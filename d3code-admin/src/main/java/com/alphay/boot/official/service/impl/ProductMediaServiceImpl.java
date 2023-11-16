package com.alphay.boot.official.service.impl;

import com.alphay.boot.common.task.GlobalThreadPool;
import com.alphay.boot.official.dao.ProductDao;
import com.alphay.boot.official.dao.ProductMediaDao;
import com.alphay.boot.official.dao.ProductMediaI18nDao;
import com.alphay.boot.official.dto.LanguageDTO;
import com.alphay.boot.official.dto.ProductMediaDTO;
import com.alphay.boot.official.entity.Product;
import com.alphay.boot.official.entity.ProductMedia;
import com.alphay.boot.official.entity.ProductMediaI18n;
import com.alphay.boot.official.service.LanguageService;
import com.alphay.boot.official.service.ProductMediaI18nService;
import com.alphay.boot.official.service.ProductMediaService;
import com.alphay.boot.official.service.ProductService;
import com.alphay.boot.official.utile.BeanCopyUtils;
import com.alphay.boot.official.utile.TranslationUtils;
import com.alphay.boot.official.vo.ProductMediaVO;
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
public class ProductMediaServiceImpl extends ServiceImpl<ProductMediaDao, ProductMedia> implements ProductMediaService {


    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductService productService;


    @Autowired
    private ProductMediaDao productMediaDao;


    @Autowired
    private LanguageService languageService;


    @Autowired
    private ProductMediaI18nDao productMediaI18nDao;

    @Autowired
    private ProductMediaI18nService productMediaI18nService;



    @Override
    public List<ProductMediaDTO> getProductMediaList(ProductMediaVO productMediaVO) {
        List<ProductMediaDTO> productMediaDTOList =  productMediaDao.getProductMediaList(productMediaVO);
        return productMediaDTOList;
    }

    @Override
    public ProductMediaDTO getProductMediaDetail(ProductMediaVO productMediaVO) {
        ProductMediaDTO  productMediaDetail = productMediaDao.getProductMediaDetail(productMediaVO);
        return productMediaDetail;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveProductMediaDetail(ProductMediaVO productMediaVO) {
        String currentLang = productMediaVO.getLang();
        Integer maxId = productMediaDao.getMaxId();
        if (maxId==null){
            maxId=0;
        }
        Integer mediaId = maxId+1;

        //保存中文版
        ProductMedia productMedia = BeanCopyUtils.copyObject(productMediaVO, ProductMedia.class);
        productMedia.setMediaId(mediaId);
        productMedia.setCreateTime(LocalDateTime.now());

        //保存当前语言版本的I18n
        ArrayList<ProductMediaI18n> productMediaI18nArrayList = new ArrayList<>();
        ProductMediaI18n productMediaI18n = BeanCopyUtils.copyObject(productMediaVO, ProductMediaI18n.class);
        productMediaI18n.setMediaId(mediaId);
        productMediaI18nArrayList.add(productMediaI18n);

        //获取其他版本的I18n
        List<LanguageDTO> languageDTOS = languageService.LanguageListExcludeCurrent(currentLang);
        int size = languageDTOS.size();

        if (size>0){
            GlobalThreadPool taskManager = GlobalThreadPool.getInstance();
            CountDownLatch latch = new CountDownLatch(size); // 初始值是线程数量减一
            for (int i = 0; i < size; i++) {
                String lang = languageDTOS.get(i).getLang();
                taskManager.addTask(()->{
                    ProductMediaI18n productMediaI18n1 = TranslationUtils.translationObject(productMediaI18n,
                            ProductMediaI18n.class, "auto", lang);
                    productMediaI18n1.setLang(lang);
                    productMediaI18nArrayList.add(productMediaI18n1);
                    latch.countDown(); // 确保无论如何都减少计数
                });
            }
            try {
                latch.await(); // 等待所有任务完成
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("productMedia是什么"+productMedia);
        //保存文件
        this.saveOrUpdate(productMedia);
        productMediaI18nArrayList.forEach(productMediaI18n1 -> {
            System.out.println("productMediaI18n1第是什么："+productMediaI18n1);
        });
        productMediaI18nService.saveOrUpdateBatch(productMediaI18nArrayList);
        Integer productId = productMediaVO.getProductId();
        Product product = productDao.selectById(productId);
        product.setUpdateTime(LocalDateTime.now());
        productService.saveOrUpdate(product);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateProductMediaDetail(ProductMediaVO productMediaVO) {
        try{
            String currentLang = productMediaVO.getLang();
            Integer mediaId = productMediaVO.getMediaId();
            //更新本体 和当前i18n  删除文件分类中间表，重新添加

            //根据mediaId 和 lang找到MediaI18n
            ProductMediaI18n productMediaI18n = productMediaI18nDao.selectOne(new LambdaQueryWrapper<ProductMediaI18n>()
                    .eq(ProductMediaI18n::getMediaId, mediaId).eq(ProductMediaI18n::getLang, currentLang));
            productMediaI18n.setImgUrl(productMediaVO.getImgUrl());
            productMediaI18n.setVideoUrl(productMediaI18n.getVideoUrl());
            productMediaI18n.setAlt(productMediaVO.getAlt());
            productMediaI18nService.saveOrUpdate(productMediaI18n);
            //更新本体
            if (currentLang.equals("zh")){
                ProductMedia productMedia = BeanCopyUtils.copyObject(productMediaVO, ProductMedia.class);
                productMedia.setUpdateTime(LocalDateTime.now());
                this.saveOrUpdate(productMedia);
            }
            Integer productId = productMediaVO.getProductId();
            Product product = productDao.selectById(productId);
            product.setUpdateTime(LocalDateTime.now());
            productService.saveOrUpdate(product);
        }catch (Exception e){
            log.error("e"+e);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String removeProductMediaDetail(Integer[] mediaIds) {
        try{
            //mediaIds
            List<Integer> mediaIdList = Arrays.asList(mediaIds);
            //删除本体
            productMediaDao.deleteBatchIds(mediaIdList);
            //删除I18n
            productMediaI18nDao.delete(new LambdaQueryWrapper<ProductMediaI18n>().in(ProductMediaI18n::getMediaId,mediaIdList));
            return null;
        }catch (Exception e){
            return "修改失败,请检查数据一致性";
        }
    }
}
