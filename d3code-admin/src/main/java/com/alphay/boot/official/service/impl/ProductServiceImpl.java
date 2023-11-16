package com.alphay.boot.official.service.impl;

import com.alphay.boot.common.task.GlobalThreadPool;
import com.alphay.boot.common.utils.SecurityUtils;
import com.alphay.boot.official.dao.*;
import com.alphay.boot.official.dto.*;
import com.alphay.boot.official.entity.*;
import com.alphay.boot.official.service.*;
import com.alphay.boot.official.utile.BeanCopyUtils;
import com.alphay.boot.official.utile.TranslationUtils;
import com.alphay.boot.official.vo.ProductVO;
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
public class ProductServiceImpl extends ServiceImpl<ProductDao, Product> implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductI18nService productI18nService;

    @Autowired
    private ProductI18nDao productI18nDao;

    @Autowired
    private LanguageService languageService;

    @Autowired
    private ProductCategoryService productCategoryService;

    @Autowired
    private ProductCategoryDao productCategoryDao;

    @Autowired
    private ProductTagService productTagService;

    @Autowired
    private ProductTagDao productTagDao;

    @Autowired
    private ProductPriceService productPriceService;

    @Autowired
    private ProductFileDao productFileDao;

    @Autowired
    private ProductMediaDao productMediaDao;

    @Autowired
    private ProductSpecsDao productSpecsDao;

    @Autowired
    private SpecsParamsDao specsParamsDao;

    @Autowired
    private ProductPriceDao productPriceDao;

    @Autowired
    private ProductSeriesDao productSeriesDao;

    @Autowired
    private ProductSeriesService productSeriesService;

    @Autowired
    private FileDao fileDao;


    @Override
    public List<ProductDTO> getProductList(ProductVO productVO) {
        System.out.println("查询的产品是"+productVO);
        List<ProductDTO> productDTOList = productDao.getProductList(productVO);
        return productDTOList;
    }

    @Override
    public ProductDTO selectProductDetail(ProductVO productVO) {
        ProductDTO productDetail =productDao.getProductDetail(productVO);
        return productDetail;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveProductDetail(ProductVO productVO) {
        try{
            String currentLang = productVO.getLang();
            Integer maxId = productDao.getMaxId();
            if (maxId==null){
                maxId=0;
            }
            Integer productId = maxId+1;
            //新增操作
            Product product = BeanCopyUtils.copyObject(productVO, Product.class);
            product.setProductId(productId);
            product.setCreateByUid(Math.toIntExact(SecurityUtils.getUserId()));
            product.setCreateTime(LocalDateTime.now());
            product.setUpdateTime(LocalDateTime.now());
            ArrayList<ProductI18n> productI18nList = new ArrayList<>();
            //产品翻译 中文版
            ProductI18n productI18n = new ProductI18n();
            productI18n.setProductId(productId);
            productI18n.setLang("zh");
            productI18n.setProductName(product.getProductName());
            productI18n.setProductDesc(product.getProductDesc());
            productI18n.setProductNickname(product.getProductNickname());
            productI18n.setImgUrl(product.getImgUrl());
            productI18nList.add(productI18n);
            //获取其他版本的I18n
            List<LanguageDTO> languageDTOS = languageService.LanguageListExcludeCurrent(currentLang);
            int size = languageDTOS.size();
            System.out.println("当前添加的产品数据是"+productVO);
            System.out.println("当前添加的产品价格是"+productVO.getProductPriceDTO());
            if (productVO.getProductPriceDTO()!=null){
                ProductPriceDTO productPriceDTO = productVO.getProductPriceDTO();
                ArrayList<ProductPrice> productPrices = new ArrayList<>();
                //汇率 6.8
                ProductPrice productPrice = new ProductPrice();
                productPrice.setProductId(productId);
                productPrice.setLang(currentLang);
                if (productPriceDTO.getLang().equals("zh")){
                    System.out.println("人民币价格");
                    productPrice.setPrice(productPriceDTO.getPrice());
                    productPrice.setCurrency("人民币");
                    productPrice.setSymbol("￥");
                    productPrice.setCreateTime(LocalDateTime.now());
                }
                productPrices.add(productPrice);
                double transformed = transformNumber(productPriceDTO.getPrice());
                for (int i = 0; i < size; i++) {
                    String lang = languageDTOS.get(i).getLang();
                    if (!lang.equals("zh")){
                        System.out.println("美元价格");
                        ProductPrice productPrice1 = new ProductPrice();
                        productPrice1.setProductId(productId);
                        productPrice1.setLang(lang);
                        productPrice1.setPrice(transformed);
                        productPrice1.setCurrency("US dollar");
                        productPrice1.setSymbol("$");
                        productPrice1.setCreateTime(LocalDateTime.now());
                        productPrices.add(productPrice1);
                    }
                }
                productPriceService.saveOrUpdateBatch(productPrices);
            }
            if (size>0){
                GlobalThreadPool taskManager = GlobalThreadPool.getInstance();
                CountDownLatch latch = new CountDownLatch(size); // 初始值是线程数量减一
                for (int i = 0; i < size; i++) {
                    String lang = languageDTOS.get(i).getLang();
                    taskManager.addTask(()->{
                        ProductI18n productI18n1 = TranslationUtils.translationObject(productI18n, ProductI18n.class, "auto", lang);
                        productI18n1.setLang(lang);
                        productI18nList.add(productI18n1);
                        latch.countDown(); // 确保无论如何都减少计数
                    });
                }
                try {
                    latch.await(); // 等待所有任务完成
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            //保存产品
            this.save(product);
            productI18nService.saveBatch(productI18nList);
            List<ProductCategoryDTO> productCategoryDTOList = productVO.getProductCategoryDTOList();
            List<ProductTagDTO> productTagDTOList = productVO.getProductTagDTOList();
            List<ProductSeriesDTO> productSeriesDTOList = productVO.getProductSeriesDTOList();
            //删除中间表
            if (productSeriesDTOList.size()>0){
                //添加新的
                ArrayList<ProductSeries> productSeriesArrayList = new ArrayList<>();
                productSeriesDTOList.forEach(productSeriesDTO -> {
                    ProductSeries productSeries = new ProductSeries();
                    productSeries.setProductId(productId);
                    productSeries.setSeriesId(productSeriesDTO.getSeriesId());
                    productSeriesArrayList.add(productSeries);
                });
                productSeriesService.saveOrUpdateBatch(productSeriesArrayList);
            }

            if (productCategoryDTOList.size()>0){
                //新增操作不用删除中间表
                ArrayList<ProductCategory> productCategoryArrayList = new ArrayList<>();
                productCategoryDTOList.forEach(productCategoryDTO -> {
                    ProductCategory productCategory = new ProductCategory();
                    productCategory.setCategoryId(productCategoryDTO.getCategoryId());
                    productCategory.setProductId(productId);
                    productCategoryArrayList.add(productCategory);
                });
                productCategoryService.saveOrUpdateBatch(productCategoryArrayList);
            }
            if (productTagDTOList.size()>0){
                ArrayList<ProductTag> productTagArrayList = new ArrayList<>();
                productTagDTOList.forEach(productTagDTO -> {
                    ProductTag productTag = new ProductTag();
                    productTag.setTagId(productTagDTO.getTagId());
                    productTag.setProductId(productId);
                    productTagArrayList.add(productTag);
                });
                productTagService.saveOrUpdateBatch(productTagArrayList);
            }
        }catch (Exception e){
            log.error("新增错误"+e);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateProductDetail(ProductVO productVO) {
        try{
            String lang = productVO.getLang();
            Integer productId = productVO.getProductId();
            //根据语言和产品Id查找对应的I18n
            ProductI18n productI18n = productI18nDao.getI18nByLangAndPId(lang, productVO.getProductId());
            productI18n.setProductNickname(productVO.getProductNickname());
            productI18n.setProductDesc(productVO.getProductDesc());
            productI18n.setImgUrl(productVO.getImgUrl());
            productI18n.setProductName(productVO.getProductName());
            productI18nService.saveOrUpdate(productI18n);
            Product product = productDao.selectById(productId);
            if (lang.equals("zh")){
                product = BeanCopyUtils.copyObject(productVO, Product.class);
            }
            product.setUpdateByUid(Math.toIntExact(SecurityUtils.getUserId()));
            product.setUpdateTime(LocalDateTime.now());
            this.saveOrUpdate(product);


            ProductPriceDTO productPriceDTO = productVO.getProductPriceDTO();
            ProductPrice productPrice = BeanCopyUtils.copyObject(productPriceDTO, ProductPrice.class);
            productPriceService.saveOrUpdate(productPrice);
            List<ProductCategoryDTO> productCategoryDTOList = productVO.getProductCategoryDTOList();
            List<ProductTagDTO> productTagDTOList = productVO.getProductTagDTOList();
            List<ProductSeriesDTO> productSeriesDTOList = productVO.getProductSeriesDTOList();
            productCategoryDao.delete(new LambdaQueryWrapper<ProductCategory>()
                    .eq(ProductCategory::getProductId, productId));
            if (productCategoryDTOList.size()>0){
                //保存中间表信息
                ArrayList<ProductCategory> productCategoryArrayList = new ArrayList<>();
                productCategoryDTOList.forEach(productCategoryDTO -> {
                    ProductCategory productCategory = new ProductCategory();
                    productCategory.setCategoryId(productCategoryDTO.getCategoryId());
                    productCategory.setProductId(productId);
                    productCategoryArrayList.add(productCategory);
                });
                productCategoryService.saveOrUpdateBatch(productCategoryArrayList);
            }
            productTagDao.delete(new LambdaQueryWrapper<ProductTag>()
                    .eq(ProductTag::getProductId, productId));
            if (productTagDTOList.size()>0){
                //保存中间表信息
                ArrayList<ProductTag> productTagArrayList = new ArrayList<>();
                productTagDTOList.forEach(productTagDTO -> {
                    ProductTag productTag = new ProductTag();
                    productTag.setTagId(productTagDTO.getTagId());
                    productTag.setProductId(productId);
                    productTagArrayList.add(productTag);
                });
                productTagService.saveOrUpdateBatch(productTagArrayList);
            }
            productSeriesDao.delete(new LambdaQueryWrapper<ProductSeries>()
                    .eq(ProductSeries::getProductId,productId));
            if (productSeriesDTOList.size()>0){
                ArrayList<ProductSeries> productSeriesArrayList = new ArrayList<>();
                productSeriesDTOList.forEach(productSeriesDTO -> {
                    ProductSeries productSeries = new ProductSeries();
                    productSeries.setProductId(productId);
                    productSeries.setSeriesId(productSeriesDTO.getSeriesId());
                    productSeriesArrayList.add(productSeries);
                });
                productSeriesService.saveOrUpdateBatch(productSeriesArrayList);
            }
        }catch (Exception e){
            log.error("修改产品时发送错误，已经回滚"+e);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteProductDetail(Integer[] productIds) {
        try {
            List<Integer> list = Arrays.asList(productIds);
            List<Product> products = productDao.selectList(new LambdaQueryWrapper<Product>()
                    .in(Product::getProductId, list));
            //删除本体
            productDao.deleteBatchIds(list);
            //删除关系表 文件 参数 媒体 分类 标签 语言 价格
            productCategoryDao.delete(new LambdaQueryWrapper<ProductCategory>()
                    .in(ProductCategory::getProductId,list));
            productPriceDao.delete(new LambdaQueryWrapper<ProductPrice>().in(ProductPrice::getProductId,list));
            List<ProductFile> productFiles = productFileDao.selectList(new LambdaQueryWrapper<ProductFile>()
                    .in(ProductFile::getProductId, list));
            if (productFiles.size()>0){
                ArrayList<Integer> integers = new ArrayList<>();
                productFiles.forEach(productFile -> {
                    integers.add(productFile.getFileId());
                });
                productFileDao.delete(new LambdaQueryWrapper<ProductFile>().in(ProductFile::getProductId,list));
                fileDao.delete(new LambdaQueryWrapper<File>().in(File::getFileId,integers));
            }
            productMediaDao.delete(new LambdaQueryWrapper<ProductMedia>().in(ProductMedia::getProductId,list));
            productCategoryDao.delete(new LambdaQueryWrapper<ProductCategory>().in(ProductCategory::getProductId,list));
            productTagDao.delete(new LambdaQueryWrapper<ProductTag>().in(ProductTag::getProductId,list));
            productI18nDao.delete(new LambdaQueryWrapper<ProductI18n>().in(ProductI18n::getProductId,list));
            List<ProductSpecs> productSpecs = productSpecsDao.selectList(new LambdaQueryWrapper<ProductSpecs>().in(ProductSpecs::getProductId, list));
            if (productSpecs!=null && productSpecs.size()>0){
                ArrayList<Integer> specsIdArrayList = new ArrayList<>();
                productSpecs.forEach(productSpecs1 -> {
                    specsIdArrayList.add(productSpecs1.getSpecsId());
                });
                specsParamsDao.deleteById(new LambdaQueryWrapper<SpecsParams>().in(SpecsParams::getSpecsId,specsIdArrayList));
            }
            productSpecsDao.delete(new LambdaQueryWrapper<ProductSpecs>().in(ProductSpecs::getProductId, list));
        }catch (Exception e){
            log.error("删除失败"+e);
        }
    }



    public static double transformNumber(double input) {
        // Multiply the input by 6.8
        double result = input * 6.8;
        // Convert the result to a string
        String resultStr = String.valueOf(result);
        // Check if the result has a decimal point
        if (resultStr.contains(".")) {
            // Split the result into integer and fractional parts
            String[] parts = resultStr.split("\\.");
            String integerPart = parts[0];
            String fractionalPart = parts[1];
            // Replace all digits in the integer part (except the first digit) with 9
            char[] integerChars = integerPart.toCharArray();
            for (int i = 1; i < integerChars.length; i++) {
                integerChars[i] = '9';
            }
            char[] charArray = fractionalPart.toCharArray();
            for (int i = 1; i < charArray.length; i++) {
                charArray[i] = '9';
            }
            // Reconstruct the result with the modified integer part
            resultStr = new String(integerChars);
        } else {
            // If there is no decimal point, replace all digits (except the first digit) with 9
            char[] chars = resultStr.toCharArray();
            for (int i = 1; i < chars.length; i++) {
                chars[i] = '9';
            }
            resultStr = new String(chars);
        }
        // Parse the modified result back to a double
        return Double.parseDouble(resultStr);
    }

}
