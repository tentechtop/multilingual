package com.alphay.boot.official.service.impl;

import com.alphay.boot.common.task.GlobalThreadPool;
import com.alphay.boot.official.dao.*;
import com.alphay.boot.official.dto.LanguageDTO;
import com.alphay.boot.official.dto.ProductSpecsDTO;
import com.alphay.boot.official.dto.SpecsParamsDTO;
import com.alphay.boot.official.entity.*;
import com.alphay.boot.official.service.*;
import com.alphay.boot.official.utile.BeanCopyUtils;
import com.alphay.boot.official.utile.TranslationUtils;
import com.alphay.boot.official.vo.ProductSpecsVO;
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
public class ProductSpecsServiceImpl extends ServiceImpl<ProductSpecsDao, ProductSpecs> implements ProductSpecsService {


    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductSpecsDao productSpecsDao;

    @Autowired
    private ProductSpecsI18nDao productSpecsI18nDao;

    @Autowired
    private ProductSpecsI18nService productSpecsI18nService;

    @Autowired
    private SpecsParamsDao specsParamsDao;

    @Autowired
    private SpecsParamsI18nDao specsParamsI18nDao;

    @Autowired
    private SpecsParamsService specsParamsService;

    @Autowired
    private SpecsParamsI18nService specsParamsI18nService;

    @Autowired
    private LanguageService languageService;

    @Override
    public List<ProductSpecsDTO> getProductSpecsList(ProductSpecsVO productSpecsVO) {
        List<ProductSpecsDTO> productSpecsList =  productSpecsDao.getProductSpecsList(productSpecsVO);
        return productSpecsList;
    }

    @Override
    public ProductSpecsDTO getProductSpecsDetail(ProductSpecsVO productSpecsVO) {
        ProductSpecsDTO productSpecsDTO =  productSpecsDao.getProductSpecsDetail(productSpecsVO);
        return productSpecsDTO;
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveProductSpecsDetail(ProductSpecsVO productSpecsVO) {
        try {
            Integer productId = productSpecsVO.getProductId();
            String currentLang = productSpecsVO.getLang();
            Integer maxId = productSpecsDao.getMaxId();
            if (maxId==null){
                maxId=0;
            }
            Integer specsId = maxId+1;
            //新增操作
            ProductSpecs productSpecs = BeanCopyUtils.copyObject(productSpecsVO, ProductSpecs.class);
            productSpecs.setSpecsId(specsId);
            ArrayList<ProductSpecsI18n> productSpecsI18nList = new ArrayList<>();
            ProductSpecsI18n productSpecsI18n = BeanCopyUtils.copyObject(productSpecsVO, ProductSpecsI18n.class);
            productSpecsI18n.setSpecsId(specsId);
            productSpecsI18nList.add(productSpecsI18n);

            List<LanguageDTO> languageDTOS = languageService.LanguageListExcludeCurrent(currentLang);
            int size = languageDTOS.size();
            if (size>0){
                GlobalThreadPool taskManager = GlobalThreadPool.getInstance();
                CountDownLatch latch = new CountDownLatch(size); // 初始值是线程数量减一
                for (int i = 0; i < size; i++) {
                    String lang = languageDTOS.get(i).getLang();
                    taskManager.addTask(()->{
                        ProductSpecsI18n productSpecsI18n1 = TranslationUtils.translationObject(productSpecsI18n, ProductSpecsI18n.class, currentLang, lang);
                        productSpecsI18n1.setLang(lang);
                        productSpecsI18nList.add(productSpecsI18n1);
                        latch.countDown(); // 确保无论如何都减少计数
                    });
                }
                try {
                    latch.await(); // 等待所有任务完成
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            this.saveOrUpdate(productSpecs);
            productSpecsI18nService.saveOrUpdateBatch(productSpecsI18nList);
            Product product = productDao.selectById(productId);
            product.setUpdateTime(LocalDateTime.now());
            productService.saveOrUpdate(product);
            //保存产品规格的参数
            List<SpecsParamsDTO> paramsDTOList = productSpecsVO.getParamsDTOList();
            if (paramsDTOList!=null && paramsDTOList.size()>0){
                //参数本体
                ArrayList<SpecsParams> specsParams = new ArrayList<>();
                //参数I18n
                ArrayList<SpecsParamsI18n> specsParamsI18nArrayList = new ArrayList<>();
                //保存参数本体
                Integer paramsId = specsParamsDao.getMaxId();
                if (paramsId == null){
                    paramsId=0;
                }
                paramsId = maxId+1;
                for (int i = 0; i < paramsDTOList.size(); i++) {
                    SpecsParamsDTO specsParamsDTO = paramsDTOList.get(i);
                    SpecsParams specsParams1 = BeanCopyUtils.copyObject(specsParamsDTO, SpecsParams.class);
                    specsParams1.setParamsId(paramsId);
                    specsParams1.setSpecsId(specsId);
                    specsParams.add(specsParams1);
                    SpecsParamsI18n specsParamsI18n = BeanCopyUtils.copyObject(specsParamsDTO, SpecsParamsI18n.class);
                    specsParamsI18n.setParamsId(paramsId);
                    specsParamsI18n.setLang(currentLang);
                    specsParamsI18nArrayList.add(specsParamsI18n);
                    if (size>0){
                        GlobalThreadPool taskManager = GlobalThreadPool.getInstance();
                        CountDownLatch latch = new CountDownLatch(size); // 初始值是线程数量减一
                        for (int j = 0; j < size; j++) {
                            String lang = languageDTOS.get(j).getLang();
                            taskManager.addTask(()->{
                                SpecsParamsI18n specsParamsI18n1 = TranslationUtils.
                                        translationObject(specsParamsI18n, SpecsParamsI18n.class, currentLang, lang);
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
                }
                specsParamsService.saveOrUpdateBatch(specsParams);
                specsParamsI18nService.saveOrUpdateBatch(specsParamsI18nArrayList);
            }
        }catch (Exception e){
            log.error("e"+e);
        }
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateProductSpecsDetail(ProductSpecsVO productSpecsVO) {
        try{
            Integer productId = productSpecsVO.getProductId();
            String currentLang = productSpecsVO.getLang();
            Integer specsId = productSpecsVO.getSpecsId();
            //根据语言和产品Id查找对应的I18n
            ProductSpecsI18n productSpecsI18n = productSpecsI18nDao.selectOne(new LambdaQueryWrapper<ProductSpecsI18n>()
                    .eq(ProductSpecsI18n::getSpecsId, specsId).eq(ProductSpecsI18n::getLang, currentLang));
            productSpecsI18n.setSpecsName(productSpecsVO.getSpecsName());
            productSpecsI18n.setImgUrl(productSpecsVO.getImgUrl());
            productSpecsI18nService.saveOrUpdate(productSpecsI18n);
            if (currentLang.equals("zh")){
                //更新本体
                ProductSpecs productSpecs = BeanCopyUtils.copyObject(productSpecsVO, ProductSpecs.class);
                this.saveOrUpdate(productSpecs);
            }
            Product product = productDao.selectById(productId);
            product.setUpdateTime(LocalDateTime.now());
            productService.saveOrUpdate(product);
            //删除参数
            //根据SpecsId找到params 再根据paramsId删除I18n
            List<SpecsParams> specsParamsList = specsParamsDao.selectList(new LambdaQueryWrapper<SpecsParams>()
                    .eq(SpecsParams::getSpecsId, specsId));
            if (specsParamsList.size()>0){
                ArrayList<Integer> specsIdArrayList = new ArrayList<>();
                specsParamsList.forEach(specsParams -> {
                    specsIdArrayList.add(specsParams.getSpecsId());
                });
                specsParamsDao.deleteBatchIds(specsIdArrayList);
            }
            List<SpecsParamsDTO> paramsDTOList = productSpecsVO.getParamsDTOList();
            if (paramsDTOList!=null && paramsDTOList.size()>0){
                //参数本体
                ArrayList<SpecsParams> specsParams = new ArrayList<>();
                //参数I18n
                ArrayList<SpecsParamsI18n> specsParamsI18nArrayList = new ArrayList<>();

                Integer paramsId = specsParamsDao.getMaxId();
                if (paramsId == null){
                    paramsId=0;
                }
                paramsId = paramsId+1;
                for (int i = 0; i < paramsDTOList.size(); i++) {
                    SpecsParamsDTO specsParamsDTO = paramsDTOList.get(i);
                    SpecsParams specsParams1 = BeanCopyUtils.copyObject(specsParamsDTO, SpecsParams.class);
                    specsParams1.setParamsId(paramsId);
                    specsParams1.setSpecsId(specsId);
                    specsParams.add(specsParams1);
                    SpecsParamsI18n specsParamsI18n = BeanCopyUtils.copyObject(specsParamsDTO, SpecsParamsI18n.class);
                    specsParamsI18n.setParamsId(paramsId);
                    specsParamsI18n.setLang(currentLang);
                    specsParamsI18nArrayList.add(specsParamsI18n);
                    List<LanguageDTO> languageDTOS = languageService.LanguageListExcludeCurrent(currentLang);
                    int size = languageDTOS.size();
                    if (size>0){
                        GlobalThreadPool taskManager = GlobalThreadPool.getInstance();
                        CountDownLatch latch = new CountDownLatch(size); // 初始值是线程数量减一
                        for (int j = 0; j < size; j++) {
                            String clang = languageDTOS.get(j).getLang();
                            taskManager.addTask(()->{
                                SpecsParamsI18n specsParamsI18n1 = TranslationUtils
                                        .translationObject(specsParamsI18n, SpecsParamsI18n.class, currentLang, clang);
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
                }
                specsParamsService.saveOrUpdateBatch(specsParams);
                specsParamsI18nService.saveOrUpdateBatch(specsParamsI18nArrayList);
            }
        }catch (Exception e){
            log.error("e"+e);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void removeProductSpecsDetail(Integer[] specsIds) {
        try{
            List<Integer> specsIdList = Arrays.asList(specsIds);
            //删除规格 和 I18n
            productSpecsDao.delete(new LambdaQueryWrapper<ProductSpecs>().in(ProductSpecs::getSpecsId,specsIdList));
            productSpecsI18nDao.delete(new LambdaQueryWrapper<ProductSpecsI18n>().in(ProductSpecsI18n::getSpecsId,specsIdList));
            //根据specsIdList找到ParamsList
            List<SpecsParams> specsParamsList = specsParamsDao.selectList(new LambdaQueryWrapper<SpecsParams>()
                    .in(SpecsParams::getSpecsId, specsIdList));
            if (specsParamsList.size()>0){
                ArrayList<Integer> paramsIdList = new ArrayList<>();
                specsParamsList.forEach(specsParams -> {
                    paramsIdList.add(specsParams.getParamsId());
                });
                //根据paramsId
                specsParamsDao.delete(new LambdaQueryWrapper<SpecsParams>().in(SpecsParams::getParamsId,paramsIdList));
                specsParamsI18nDao.delete(new LambdaQueryWrapper<SpecsParamsI18n>().
                        in(SpecsParamsI18n::getParamsId,paramsIdList));
            }
        }catch (Exception e){
            log.error("e"+e);
        }

    }
}

























