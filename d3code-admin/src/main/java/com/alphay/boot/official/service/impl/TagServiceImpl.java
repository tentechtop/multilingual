package com.alphay.boot.official.service.impl;

import com.alphay.boot.common.task.GlobalThreadPool;
import com.alphay.boot.common.utils.SecurityUtils;
import com.alphay.boot.official.dao.*;
import com.alphay.boot.official.dto.LanguageDTO;
import com.alphay.boot.official.dto.TagDTO;
import com.alphay.boot.official.entity.FaqTag;
import com.alphay.boot.official.entity.ProductTag;
import com.alphay.boot.official.entity.Tag;
import com.alphay.boot.official.entity.TagI18n;
import com.alphay.boot.official.service.LanguageService;
import com.alphay.boot.official.service.TagI18nService;
import com.alphay.boot.official.service.TagService;
import com.alphay.boot.official.utile.BeanCopyUtils;
import com.alphay.boot.official.utile.TranslationUtils;
import com.alphay.boot.official.vo.TagVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl extends ServiceImpl<TagDao, Tag> implements TagService {


    @Autowired
    private TagDao tagDao;

    @Autowired
    private FaqTagDao faqTagDao;

    @Autowired
    private ProductTagDao productTagDao;

    @Autowired
    private TagI18nService tagI18nService;

    @Autowired
    private TagI18nDao tagI18nDao;

    @Autowired
    private LanguageService languageService;

    @Autowired
    private LanguageDao languageDao;

    @Override
    public List<TagDTO> selectNewsList(TagVO tagVO) {
        List<TagDTO> tagDTOList = tagDao.selectNewsList(tagVO);
        return tagDTOList;
    }

    @Override
    public TagDTO selectTagById(TagVO tagVO) {
        TagDTO tagDTO =   tagDao.selectTagById(tagVO);
        return tagDTO;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveTag(TagVO tagVO) {
        String currentLang = tagVO.getLang();
        Integer maxId = tagDao.getMaxId();
        if (maxId==null){
            maxId=0;
        }
        Integer tagId = maxId+1;
        Tag tag = BeanCopyUtils.copyObject(tagVO, Tag.class);
        tag.setTagId(tagId);
        tag.setCreateByUid(Math.toIntExact(SecurityUtils.getUserId()));
        tag.setCreateTime(LocalDateTime.now());

        TagI18n tagI18n = BeanCopyUtils.copyObject(tagVO, TagI18n.class);
        tagI18n.setTagId(tagId);
        ArrayList<TagI18n> tagI18ns = new ArrayList<>();
        tagI18ns.add(tagI18n);

        List<LanguageDTO> languageDTOS = languageDao.LanguageListExcludeCurrent(currentLang);
        int size = languageDTOS.size();
        if (size>0){
            GlobalThreadPool taskManager = GlobalThreadPool.getInstance();
            CountDownLatch latch = new CountDownLatch(size); // 初始值是线程数量减一
            for (int i = 0; i < size; i++) {
                String lang = languageDTOS.get(i).getLang();
                taskManager.addTask(()->{
                    TagI18n tagI18n1 = TranslationUtils.translationObject(tagI18n, TagI18n.class, "auto", lang);
                    tagI18n1.setLang(lang);
                    tagI18ns.add(tagI18n1);
                    latch.countDown(); // 确保无论如何都减少计数
                });
            }
            try {
                latch.await(); // 等待所有任务完成
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        this.saveOrUpdate(tag);
        tagI18nService.saveOrUpdateBatch(tagI18ns);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateTag(TagVO tagVO) {
        //还要同步更新 langI18n
        String currentLang  = tagVO.getLang();
        Integer tagId = tagVO.getTagId();
        Tag tag = BeanCopyUtils.copyObject(tagVO, Tag.class);
        tag.setUpdateByUid(Math.toIntExact(SecurityUtils.getUserId()));
        tag.setUpdateTime(LocalDateTime.now());

        TagI18n tagI18n = tagI18nDao.selectOne(new LambdaQueryWrapper<TagI18n>()
                .eq(TagI18n::getTagId, tagId)
                .eq(TagI18n::getLang, currentLang));
        tagI18n.setTagName(tagVO.getTagName());
        this.saveOrUpdate(tag);
        tagI18nService.saveOrUpdate(tagI18n);
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public String deleteTagByIds(Integer[] tagIds) {
        //查看关系表 faq_tag  product_tag
        try{
            List<Tag> tags = tagDao.selectList(new LambdaQueryWrapper<Tag>().in(Tag::getTagId, tagIds));
            Map<Integer, List<Integer>> tagIdMap = tags.stream()
                    .collect(Collectors.groupingBy(Tag::getTagType, // 使用tag.getTagType()作为键
                            Collectors.mapping(Tag::getTagId, Collectors.toList())));
            if (tagIdMap.get(1)!=null){
                System.out.println("现在删除的是产品标签");
                productTagDao.delete(new LambdaQueryWrapper<ProductTag>().in(ProductTag::getTagId,tagIdMap.get(1)));
            }
            if (tagIdMap.get(2)!=null){
                System.out.println("现在删除的是问答标签");
                faqTagDao.delete(new LambdaQueryWrapper<FaqTag>().in(FaqTag::getTagId,tagIdMap.get(2)));
            }
            tagDao.deleteBatchIds(Arrays.asList(tagIds));
            tagI18nDao.delete(new LambdaQueryWrapper<TagI18n>().in(TagI18n::getTagId, Arrays.asList(tagIds)));
            return null;
        }catch (Exception e){
            return "修改失败,请检查数据一致性";
        }
    }
}
