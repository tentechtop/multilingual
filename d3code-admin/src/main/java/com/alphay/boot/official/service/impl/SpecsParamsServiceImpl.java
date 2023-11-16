package com.alphay.boot.official.service.impl;

import com.alphay.boot.common.task.GlobalThreadPool;
import com.alphay.boot.official.dao.ProductDao;
import com.alphay.boot.official.dao.ProductSpecsDao;
import com.alphay.boot.official.dao.SpecsParamsDao;
import com.alphay.boot.official.dao.SpecsParamsI18nDao;
import com.alphay.boot.official.dto.LanguageDTO;
import com.alphay.boot.official.dto.SpecsParamsDTO;
import com.alphay.boot.official.entity.Product;
import com.alphay.boot.official.entity.ProductSpecs;
import com.alphay.boot.official.entity.SpecsParams;
import com.alphay.boot.official.entity.SpecsParamsI18n;
import com.alphay.boot.official.service.*;
import com.alphay.boot.official.utile.BeanCopyUtils;
import com.alphay.boot.official.utile.TranslationUtils;
import com.alphay.boot.official.vo.SpecsParamsVO;
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
public class SpecsParamsServiceImpl extends ServiceImpl<SpecsParamsDao, SpecsParams> implements SpecsParamsService {


    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductSpecsDao productSpecsDao;

    @Autowired
    private ProductSpecsService productSpecsService;

    @Autowired
    private SpecsParamsDao specsParamsDao;

    @Autowired
    private LanguageService languageService;

    @Autowired
    private SpecsParamsI18nDao specsParamsI18nDao;

    @Autowired
    private SpecsParamsI18nService specsParamsI18nService;

    @Override
    public List<SpecsParamsDTO> getSpecsParamsList(SpecsParamsVO specsParamsVO) {
        List<SpecsParamsDTO> specsParamsDTOList =  specsParamsDao.getSpecsParamsList(specsParamsVO);
        return specsParamsDTOList;
    }

    @Override
    public SpecsParamsDTO getSpecsParamsDetail(SpecsParamsVO specsParamsVO) {
        SpecsParamsDTO specsParamsDTO = specsParamsDao.getSpecsParamsDetail(specsParamsVO);
        return specsParamsDTO;
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveSpecsParamsDetail(SpecsParamsVO specsParamsVO) {
        try{
            String currentLang = specsParamsVO.getLang();
            Integer maxId = specsParamsDao.getMaxId();
            if (maxId==null){
                maxId=0;
            }
            Integer paramsId = maxId+1;
            SpecsParams specsParams = BeanCopyUtils.copyObject(specsParamsVO, SpecsParams.class);
            specsParams.setParamsId(paramsId);
            ArrayList<SpecsParamsI18n> specsParamsI18nArrayList = new ArrayList<>();
            SpecsParamsI18n specsParamsI18n = BeanCopyUtils.copyObject(specsParamsVO, SpecsParamsI18n.class);
            specsParamsI18n.setParamsId(paramsId);
            specsParamsI18nArrayList.add(specsParamsI18n);

            List<LanguageDTO> languageDTOS = languageService.LanguageListExcludeCurrent(currentLang);
            int size = languageDTOS.size();
            if (size>0){
                GlobalThreadPool taskManager = GlobalThreadPool.getInstance();
                CountDownLatch latch = new CountDownLatch(size); // 初始值是线程数量减一
                for (int i = 0; i < size; i++) {
                    String lang = languageDTOS.get(i).getLang();
                    taskManager.addTask(()->{
                        SpecsParamsI18n specsParamsI18n1 = TranslationUtils.translationObject(specsParamsI18n, SpecsParamsI18n.class, currentLang, lang);
                        specsParamsI18n1.setLang(lang);
                        specsParamsI18nArrayList.add(specsParamsI18n1);
                        latch.countDown(); // 确保无论如何都减少计数
                    });
                }
                try {
                    latch.await(); // 等待所有任务完成
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

            this.saveOrUpdate(specsParams);
            System.out.println("保存这个参数"+specsParams);
            specsParamsI18nService.saveOrUpdateBatch(specsParamsI18nArrayList);
            System.out.println("保存这个参数的翻译"+specsParamsI18nArrayList);

            Integer specsId = specsParamsVO.getSpecsId();
            ProductSpecs productSpecs = productSpecsDao.selectById(specsId);
            Integer productId = productSpecs.getProductId();
            Product product = productDao.selectById(productId);
            product.setUpdateTime(LocalDateTime.now());
            productService.saveOrUpdate(product);

        }catch (Exception e){
            log.error("e"+e);
        }
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateSpecsParamsDetail(SpecsParamsVO specsParamsVO) {
        try {
            String currentLang = specsParamsVO.getLang();
            Integer paramsId = specsParamsVO.getParamsId();
            SpecsParamsI18n specsParamsI18n = specsParamsI18nDao.selectOne(new LambdaQueryWrapper<SpecsParamsI18n>().eq(SpecsParamsI18n::getParamsId, paramsId)
                    .eq(SpecsParamsI18n::getLang, currentLang));
            specsParamsI18n.setParamsTitle(specsParamsVO.getParamsTitle());
            specsParamsI18n.setNumberValue(specsParamsVO.getNumberValue());
            specsParamsI18n.setStringValue(specsParamsVO.getStringValue());
            specsParamsI18nService.saveOrUpdate(specsParamsI18n);
            if (currentLang.equals("zh")){
                SpecsParams specsParams = BeanCopyUtils.copyObject(specsParamsVO, SpecsParams.class);
                this.saveOrUpdate(specsParams);
            }

            Integer specsId = specsParamsVO.getSpecsId();
            ProductSpecs productSpecs = productSpecsDao.selectById(specsId);
            Integer productId = productSpecs.getProductId();
            Product product = productDao.selectById(productId);
            product.setUpdateTime(LocalDateTime.now());
            productService.saveOrUpdate(product);
        }catch (Exception e){
            log.error("e"+e);
        }

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void removeSpecsParamsDetail(Integer[] paramsIds) {
        try{
            List<Integer> paramsIdList = Arrays.asList(paramsIds);
            specsParamsDao.delete(new LambdaQueryWrapper<SpecsParams>().in(SpecsParams::getParamsId, paramsIdList));
            specsParamsI18nDao.delete(new LambdaQueryWrapper<SpecsParamsI18n>().in(SpecsParamsI18n::getParamsId,paramsIdList));
        }catch (Exception e){
            log.error("e"+e);
        }
    }
}
