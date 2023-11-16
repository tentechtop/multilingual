package com.alphay.boot.official.service.impl;

import com.alphay.boot.common.task.GlobalThreadPool;
import com.alphay.boot.official.dao.*;
import com.alphay.boot.official.dto.FaqCategoryDTO;
import com.alphay.boot.official.dto.FaqDTO;
import com.alphay.boot.official.dto.FaqTagDTO;
import com.alphay.boot.official.dto.LanguageDTO;
import com.alphay.boot.official.entity.Faq;
import com.alphay.boot.official.entity.FaqCategory;
import com.alphay.boot.official.entity.FaqI18n;
import com.alphay.boot.official.entity.FaqTag;
import com.alphay.boot.official.service.FaqCategoryService;
import com.alphay.boot.official.service.FaqI18nService;
import com.alphay.boot.official.service.FaqService;
import com.alphay.boot.official.service.FaqTagService;
import com.alphay.boot.official.utile.BeanCopyUtils;
import com.alphay.boot.official.utile.TranslationUtils;
import com.alphay.boot.official.vo.FaqVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@Service
public class FaqServiceImpl extends ServiceImpl<FaqDao, Faq> implements FaqService {


    @Autowired
    private FaqDao faqDao;

    @Autowired
    private LanguageDao languageDao;

    @Autowired
    private FaqI18nDao faqI18nDao;

    @Autowired
    private FaqI18nService faqI18nService;

    @Autowired
    private FaqCategoryDao faqCategoryDao;

    @Autowired
    private FaqCategoryService faqCategoryService;

    @Autowired
    private FaqTagDao faqTagDao;

    @Autowired
    private FaqTagService faqTagService;

    @Override
    public List<FaqDTO> selectFaqList(FaqVO faqVO) {
        List<FaqDTO> faqDTOS = faqDao.selectFileList(faqVO);
        return faqDTOS;
    }

    @Override
    public FaqDTO selectFaqById(FaqVO faqVO) {
        System.out.println("服务中当前查询的内容"+faqVO);
        return faqDao.selectFaqById(faqVO);
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveFaq(FaqVO faqVO) {
        Integer maxId = faqDao.getMaxId();
        if (maxId==null){
            maxId=0;
        }
        Integer faqId = maxId+1;
        String currentLang = faqVO.getLang();
        Faq faq = BeanCopyUtils.copyObject(faqVO, Faq.class);
        faq.setFaqId(faqId);
        faq.setCreateTime(LocalDateTime.now());
        faq.setUpdateTime(LocalDateTime.now());
        ArrayList<FaqI18n> faqI18nArrayList = new ArrayList<>();
        FaqI18n faqI18n = BeanCopyUtils.copyObject(faqVO, FaqI18n.class);
        faqI18n.setFaqId(faqId);
        faqI18nArrayList.add(faqI18n);
        List<LanguageDTO> languageDTOS = languageDao.LanguageListExcludeCurrent(currentLang);
        int size = languageDTOS.size();
        if (size>0){
            GlobalThreadPool taskManager = GlobalThreadPool.getInstance();
            CountDownLatch latch = new CountDownLatch(size); // 初始值是线程数量减一
            for (int i = 0; i < size; i++) {
                String lang = languageDTOS.get(i).getLang();
                taskManager.addTask(()->{
                    FaqI18n faqI18n1 = TranslationUtils.translationObject(faqI18n, FaqI18n.class, "auto", lang);
                    faqI18n1.setLang(lang);
                    faqI18nArrayList.add(faqI18n1);
                    latch.countDown(); // 确保无论如何都减少计数
                });
            }
            try {
                latch.await(); // 等待所有任务完成
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        this.saveOrUpdate(faq);
        faqI18nService.saveOrUpdateBatch(faqI18nArrayList);

        //添加分类和标签 新增是不用删除中间表的
        List<FaqCategoryDTO> categoryDTOList = faqVO.getCategoryDTOList();
        List<FaqTagDTO> tagDTOList = faqVO.getTagDTOList();
        //保存中间表信息
        if (categoryDTOList!=null && categoryDTOList.size()>0){
            ArrayList<FaqCategory> faqCategoryArrayList = new ArrayList<>();
            categoryDTOList.forEach(faqCategoryDTO -> {
                FaqCategory faqCategory = new FaqCategory();
                faqCategory.setFaqId(faqId);
                faqCategory.setCategoryId(faqCategoryDTO.getCategoryId());
                faqCategoryArrayList.add(faqCategory);
            });
            faqCategoryService.saveOrUpdateBatch(faqCategoryArrayList);
        }
        if (tagDTOList!=null && tagDTOList.size()>0){
            ArrayList<FaqTag> faqTagArrayList = new ArrayList<>();
            tagDTOList.forEach(faqTagDTO -> {
                FaqTag faqTag = new FaqTag();
                faqTag.setFaqId(faqId);
                faqTag.setTagId(faqTagDTO.getTagId());
                faqTagArrayList.add(faqTag);
            });
            faqTagService.saveOrUpdateBatch(faqTagArrayList);
        }



        GlobalThreadPool taskManager = GlobalThreadPool.getInstance();
        taskManager.addTask(()->{
            //查询数据库 讲list查出来 并保存到es
            saveFaqToES(faqId);
        });
    }


    public void saveFaqToES(Integer faqId){
        //将faq的多个语言版本查出来 并保存到es





    }




    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateFaq(FaqVO faqVO) {
        //更新本体  根据faqId和lang 更新I18n
        String lang = faqVO.getLang();
        Integer faqId1 = faqVO.getFaqId();
        Faq faq = faqDao.selectById(faqId1);
        if (lang.equals("zh")){
            faq = BeanCopyUtils.copyObject(faqVO, Faq.class);
        }
        faq.setUpdateTime(LocalDateTime.now());
        this.saveOrUpdate(faq);
        FaqI18n faqI18n = faqI18nDao.selectOne(new LambdaQueryWrapper<FaqI18n>()
                .eq(FaqI18n::getFaqId, faqVO.getFaqId())
                .eq(FaqI18n::getLang, faqVO.getLang()));
        faqI18n.setFaqAnswers(faqVO.getFaqAnswers());
        faqI18n.setFaqQuestion(faqVO.getFaqQuestion());
        faqI18n.setFaqTitle(faq.getFaqTitle());
        faqI18nService.saveOrUpdate(faqI18n);
        //删除中间表 删除分类中间表  off_faq_category  off_faq_tag
        List<FaqCategory> faqCategories = faqCategoryDao.selectList(new LambdaQueryWrapper<FaqCategory>()
                .eq(FaqCategory::getFaqId, faqVO.getFaqId()));
        ArrayList<Integer> faqCategoryIds = new ArrayList<>();
        if (faqCategories.size()>0){
            faqCategories.forEach(faqCategory -> {
                if (faqCategory.getFaqCategoryId()!=null){
                    faqCategoryIds.add(faqCategory.getFaqCategoryId());
                }
            });
            faqCategoryDao.deleteBatchIds(faqCategories);
        }
        List<FaqTag> faqTags = faqTagDao.selectList(new LambdaQueryWrapper<FaqTag>()
                .eq(FaqTag::getFaqId, faqVO.getFaqId()));
        ArrayList<Integer> faqTagIds = new ArrayList<>();
        if (faqTags.size()>0){
            faqTags.forEach(faqTag -> {
                if (faqTag.getFaqTagId()!=null){
                    faqTagIds.add(faqTag.getFaqTagId());
                }
            });
            faqTagDao.deleteBatchIds(faqTagIds);
        }
        //添加新的
        Integer faqId = faqVO.getFaqId();
        List<FaqCategoryDTO> categoryDTOList = faqVO.getCategoryDTOList();
        if (categoryDTOList.size()>0){
            ArrayList<FaqCategory> faqCategories1 = new ArrayList<>();
            categoryDTOList.forEach(category -> {
                if (category!=null){
                    FaqCategory faqCategory = new FaqCategory();
                    faqCategory.setCategoryId(category.getCategoryId());
                    faqCategory.setFaqId(faqId);
                    faqCategories1.add(faqCategory);
                }
            });
            faqCategoryService.saveBatch(faqCategories1);
        }
        List<FaqTagDTO> tagDTOList = faqVO.getTagDTOList();
        if (tagDTOList.size()>0){
            ArrayList<FaqTag> faqCategories1 = new ArrayList<>();
            for (FaqTagDTO faqTagDTO : tagDTOList) {
                if (faqTagDTO != null) {
                    FaqTag faqTag = new FaqTag();
                    faqTag.setTagId(faqTagDTO.getTagId());
                    faqTag.setFaqId(faqId);
                    faqCategories1.add(faqTag);
                }
            }
            faqTagService.saveBatch(faqCategories1);
        }

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteFaqByIds(Integer[] faqIds) {
        try{
            List<Integer> list = Arrays.asList(faqIds);
            List<Faq> faqs = faqDao.selectList(new LambdaQueryWrapper<Faq>().in(Faq::getFaqId, list));
            //删除本体
            faqDao.deleteBatchIds(list);
            //删除关系表
            int delete = faqCategoryDao.delete(new LambdaQueryWrapper<FaqCategory>().in(FaqCategory::getFaqId, list));
            int delete1 = faqTagDao.delete(new LambdaQueryWrapper<FaqTag>().in(FaqTag::getFaqId, list));
            //删除I18n
            int delete2 = faqI18nDao.delete(new LambdaQueryWrapper<FaqI18n>().in(FaqI18n::getFaqId, list));
        }catch (Exception e){
            log.error("删除失败"+e);
        }
    }
}
