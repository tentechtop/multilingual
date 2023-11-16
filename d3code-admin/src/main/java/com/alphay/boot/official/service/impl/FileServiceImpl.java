package com.alphay.boot.official.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import com.alphay.boot.common.task.GlobalThreadPool;
import com.alphay.boot.common.utils.SecurityUtils;
import com.alphay.boot.official.dao.*;
import com.alphay.boot.official.dto.FileCategoryDTO;
import com.alphay.boot.official.dto.FileDTO;
import com.alphay.boot.official.dto.LanguageDTO;
import com.alphay.boot.official.dto.ProductFileDTO;
import com.alphay.boot.official.entity.*;
import com.alphay.boot.official.service.*;
import com.alphay.boot.official.utile.BeanCopyUtils;
import com.alphay.boot.official.utile.TranslationUtils;
import com.alphay.boot.official.vo.CategoryVO;
import com.alphay.boot.official.vo.FileVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;


@Service
public class FileServiceImpl extends ServiceImpl<FileDao, File> implements FileService {

    @Resource
    private FileDao fileDao;

    @Autowired
    private LanguageService languageService;

    @Autowired
    private FileCategoryService fileCategoryService;

    @Autowired
    private FileCategoryDao fileCategoryDao;

    @Autowired
    private FileI18nService fileI18nService;

    @Autowired
    private FileI18nDao fileI18nDao;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductFileDao productFileDao;

    @Autowired
    private ProductFileService productFileService;


    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductService productService;


    @Autowired
    private ElasticsearchClient client;

    private ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveOrUpdateFile(FileVO fileVO) {
        try{
            Integer maxId = fileDao.getMaxId();
            if (maxId==null){
                maxId=0;
            }
            Integer fileId = maxId+1;
            File file = BeanCopyUtils.copyObject(fileVO, File.class);
            file.setFileId(fileId);
            file.setCreateByUid(Math.toIntExact(SecurityUtils.getUserId()));
            file.setCreateTime(LocalDateTime.now());
            file.setUpdateTime(LocalDateTime.now());
            String currentLang = fileVO.getLang();
            //保存中文版I18n
            ArrayList<FileI18n> FileI18nList = new ArrayList<>();
            FileI18n fileI18n = new FileI18n();
            fileI18n.setFileId(fileId);
            fileI18n.setLang(currentLang);

            fileI18n.setFileUrl(file.getFileUrl());
            fileI18n.setFileDesc(file.getFileDesc());
            fileI18n.setFileContent(file.getFileContent());
            fileI18n.setFileName(file.getFileName());
            FileI18nList.add(fileI18n);

            //保存文件分类
            List<CategoryVO> categoryVOList = fileVO.getCategoryVOList();
            if (categoryVOList!=null && categoryVOList.size()>0){
                ArrayList<FileCategory> fileCategoryList = new ArrayList<>();
                categoryVOList.forEach(categoryVO -> {
                    FileCategory fileCategory = new FileCategory();
                    fileCategory.setCategoryId(categoryVO.getCategoryId());
                    fileCategory.setFileId(fileId);
                    fileCategoryList.add(fileCategory);
                });
                fileCategoryService.saveBatch(fileCategoryList);
            }

            //获取其他版本的I18n
            List<LanguageDTO> languageDTOS = languageService.LanguageListExcludeCurrent(currentLang);
            int size = languageDTOS.size();
            if (size>0){
                GlobalThreadPool taskManager = GlobalThreadPool.getInstance();
                CountDownLatch latch = new CountDownLatch(size); // 初始值是线程数量减一
                for (int i = 0; i < size; i++) {
                    String lang = languageDTOS.get(i).getLang();
                    taskManager.addTask(()->{
                        FileI18n fileI18n1 = TranslationUtils.translationObject(fileI18n, FileI18n.class, "auto", lang);
                        fileI18n1.setLang(lang);
                        FileI18nList.add(fileI18n1);
                        latch.countDown(); // 确保无论如何都减少计数
                    });
                }
                try {
                    latch.await(); // 等待所有任务完成
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            //保存文件
            this.save(file);
            //保存文件分类
            fileI18nService.saveOrUpdateBatch(FileI18nList);
            // 延时执行查询文件列表的操作
        }catch (Exception e){
            log.error("e"+e);
        }
    }


    public static String generateCustomId() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    /**
     * 根据语言和分类找文件
     * @param fileVO
     * @return
     */
    @Override
    public List<FileDTO> selectFileList(FileVO fileVO) {
        List<FileDTO> fileDTOS  =  fileDao.selectFileList(fileVO);
        return fileDTOS;
    }

    @Override
    public FileDTO selectFileById(FileVO fileVO) {
        FileDTO fileDTO =   fileDao.selectFileById(fileVO);
        return fileDTO;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateFile(FileVO fileVO) {
        //更新本体 和当前i18n  删除文件分类中间表，重新添加
        String currentLang = fileVO.getLang();
        Integer fileId = fileVO.getFileId();

        //根据fileId和lang找到FileI18n
        FileI18n fileI18n = fileI18nDao.selectOne(new LambdaQueryWrapper<FileI18n>().eq(FileI18n::getFileId, fileId).eq(FileI18n::getLang, currentLang));
        fileI18n.setFileName(fileVO.getFileName());
        fileI18n.setFileUrl(fileVO.getFileUrl());
        fileI18n.setFileDesc(fileVO.getFileDesc());
        fileI18n.setFileContent(fileVO.getFileContent());


        File file = fileDao.selectById(fileId);
        if (currentLang.equals("zh")){
            file = BeanCopyUtils.copyObject(fileVO, File.class);
        }
        file.setUpdateTime(LocalDateTime.now());


        //将有这个fileId的中间表都删除
        fileCategoryDao.delete(new LambdaQueryWrapper<FileCategory>()
                .eq(FileCategory::getFileId,fileId));
        //重新添加中间表
        List<CategoryVO> categoryVOList = fileVO.getCategoryVOList();
        if (categoryVOList.size()>0){
            ArrayList<FileCategory> fileCategories = new ArrayList<>();
            categoryVOList.forEach(categoryVO -> {
                FileCategory fileCategory = new FileCategory();
                fileCategory.setFileId(fileId);
                fileCategory.setCategoryId(categoryVO.getCategoryId());
                fileCategories.add(fileCategory);
            });
            fileCategoryService.saveOrUpdateBatch(fileCategories);
        }

        this.saveOrUpdate(file);
        fileI18nService.saveOrUpdate(fileI18n);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String deleteFileByIds(Integer[] fileIds) {
        try{
            //根据fileId删除本体和I18n 和分类中间表
            List<Integer> fileIdList = Arrays.asList(fileIds);
            fileDao.deleteBatchIds(fileIdList);
            fileI18nDao.delete(new LambdaQueryWrapper<FileI18n>().in(FileI18n::getFileId,fileIdList));
            //删除分类中间表
            fileCategoryDao.delete(new LambdaQueryWrapper<FileCategory>().in(FileCategory::getFileId,fileIdList));
            //删除productFile
            productFileDao.delete(new LambdaQueryWrapper<ProductFile>().in(ProductFile::getFileId,fileIdList));
            return null;
        }catch (Exception e){
            return "修改失败,请检查数据一致性";
        }

    }

    @Override
    public List<ProductFileDTO> getProductFileList(ProductFileDTO productFileDTO) {
        List<ProductFileDTO> productFileDTOList = fileDao.getProductFileList(productFileDTO);
        return productFileDTOList;
    }

    @Override
    public ProductFileDTO getProductFileDetail(ProductFileDTO productFileDTO) {

        System.out.println("fileId"+productFileDTO.getFileId()+"lang"+productFileDTO.getLang());
        ProductFileDTO productFileDTO1 =  fileDao.getProductFileDetail(productFileDTO);
        return productFileDTO1;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveProductFileDetail(ProductFileDTO productFileDTO) {
        String currentLang = productFileDTO.getLang();
        Integer maxId = fileDao.getMaxId();
        if (maxId==null){
            maxId=0;
        }
        Integer fileId = maxId+1;

        File file = BeanCopyUtils.copyObject(productFileDTO, File.class);
        file.setFileId(fileId);
        file.setCreateByUid(Math.toIntExact(SecurityUtils.getUserId()));
        file.setCreateTime(LocalDateTime.now());
        file.setUpdateTime(LocalDateTime.now());
        //保存中文版I18n
        ArrayList<FileI18n> FileI18nList = new ArrayList<>();
        FileI18n fileI18n = new FileI18n();
        fileI18n.setFileId(fileId);
        fileI18n.setLang(currentLang);
        fileI18n.setFileUrl(file.getFileUrl());
        fileI18n.setFileDesc(file.getFileDesc());
        fileI18n.setFileContent(file.getFileContent());
        fileI18n.setFileName(file.getFileName());
        FileI18nList.add(fileI18n);

        //保存文件分类
        List<FileCategoryDTO> categoryDTOList = productFileDTO.getCategoryDTOList();
        if (categoryDTOList!=null && categoryDTOList.size()>0){
            ArrayList<FileCategory> fileCategoryList = new ArrayList<>();
            categoryDTOList.forEach(category -> {
                FileCategory fileCategory = new FileCategory();
                fileCategory.setCategoryId(category.getCategoryId());
                fileCategory.setFileId(fileId);
                fileCategoryList.add(fileCategory);
            });
            fileCategoryService.saveBatch(fileCategoryList);
        }
        //获取其他版本的I18n
        List<LanguageDTO> languageDTOS = languageService.LanguageListExcludeCurrent(currentLang);
        int size = languageDTOS.size();
        if (size>0){
            GlobalThreadPool taskManager = GlobalThreadPool.getInstance();
            CountDownLatch latch = new CountDownLatch(size); // 初始值是线程数量减一
            for (int i = 0; i < size; i++) {
                String lang = languageDTOS.get(i).getLang();
                taskManager.addTask(()->{
                    FileI18n fileI18n1 = TranslationUtils.translationObject(fileI18n, FileI18n.class, "auto", lang);
                    fileI18n1.setLang(lang);
                    FileI18nList.add(fileI18n1);
                    latch.countDown(); // 确保无论如何都减少计数
                });
            }
            try {
                latch.await(); // 等待所有任务完成
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        //保存文件
        this.save(file);
        //保存文件分类
        fileI18nService.saveOrUpdateBatch(FileI18nList);
        Integer productId = productFileDTO.getProductId();
        //保存productFile中建表
        productFileDao.delete(new LambdaQueryWrapper<ProductFile>().eq(ProductFile::getProductId,productId));
        ProductFile productFile = new ProductFile();
        productFile.setProductId(productId);
        productFile.setFileId(fileId);
        productFileService.saveOrUpdate(productFile);

        Product product = productDao.selectById(productId);
        product.setUpdateTime(LocalDateTime.now());
        productService.saveOrUpdate(product);


    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateProductFileDetail(ProductFileDTO productFileDTO) {
        try{
            //更新本体 和当前i18n  删除文件分类中间表，重新添加
            File file = BeanCopyUtils.copyObject(productFileDTO, File.class);
            String currentLang = productFileDTO.getLang();
            Integer fileId = productFileDTO.getFileId();
            //根据fileId和lang找到FileI18n
            FileI18n fileI18n = fileI18nDao.selectOne(new LambdaQueryWrapper<FileI18n>()
                    .eq(FileI18n::getFileId, fileId).eq(FileI18n::getLang, currentLang));
            fileI18n.setFileName(productFileDTO.getFileName());
            fileI18n.setFileUrl(file.getFileUrl());
            fileI18n.setFileDesc(file.getFileDesc());
            fileI18n.setFileContent(file.getFileContent());
            //将有这个fileId的中间表都删除
            fileCategoryDao.delete(new LambdaQueryWrapper<FileCategory>()
                    .eq(FileCategory::getFileId,fileId));
            //保存文件分类
            List<FileCategoryDTO> categoryDTOList = productFileDTO.getCategoryDTOList();
            if (categoryDTOList!=null && categoryDTOList.size()>0){
                ArrayList<FileCategory> fileCategoryList = new ArrayList<>();
                categoryDTOList.forEach(category -> {
                    FileCategory fileCategory = new FileCategory();
                    fileCategory.setCategoryId(category.getCategoryId());
                    fileCategory.setFileId(fileId);
                    fileCategoryList.add(fileCategory);
                });
                fileCategoryService.saveBatch(fileCategoryList);
            }
            this.saveOrUpdate(file);
            fileI18nService.saveOrUpdate(fileI18n);
            Integer productId = productFileDTO.getProductId();
            Product product = productDao.selectById(productId);
            product.setUpdateTime(LocalDateTime.now());
            productService.saveOrUpdate(product);
        }catch (Exception e){
            log.error("修改失败"+e);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String removeProductFileDetail(Integer[] fileIds) {
        try{
            //根据fileId删除本体和I18n 和分类中间表
            List<Integer> fileIdList = Arrays.asList(fileIds);
            fileDao.deleteBatchIds(fileIdList);
            fileI18nDao.delete(new LambdaQueryWrapper<FileI18n>().in(FileI18n::getFileId,fileIdList));
            //删除分类中间表
            fileCategoryDao.delete(new LambdaQueryWrapper<FileCategory>().in(FileCategory::getFileId,fileIdList));
            //删除productFile
            productFileDao.delete(new LambdaQueryWrapper<ProductFile>().in(ProductFile::getFileId,fileIdList));
            return null;
        }catch (Exception e){
            return "修改失败,请检查数据一致性";
        }
    }
}
