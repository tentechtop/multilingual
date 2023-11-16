package com.alphay.boot.official.service.impl;

import com.alphay.boot.common.task.GlobalThreadPool;
import com.alphay.boot.common.utils.SecurityUtils;
import com.alphay.boot.official.dao.*;
import com.alphay.boot.official.dto.CategoryDTO;
import com.alphay.boot.official.dto.LanguageDTO;
import com.alphay.boot.official.entity.*;
import com.alphay.boot.official.service.CategoryI18nService;
import com.alphay.boot.official.service.CategoryService;
import com.alphay.boot.official.utile.BeanCopyUtils;
import com.alphay.boot.official.utile.TranslationUtils;
import com.alphay.boot.official.vo.CategoryVO;
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
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, Category> implements CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private LanguageDao languageDao;

    @Autowired
    private CategoryI18nService categoryI18nService;

    @Autowired
    private CategoryI18nDao categoryI18nDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductCategoryDao productCategoryDao;


    @Autowired
    private FileDao fileDao;


    @Autowired
    private FileCategoryDao fileCategoryDao;

    @Autowired
    private CourseDao courseDao;


    @Autowired
    private CourseCategoryDao courseCategoryDao;


    @Override
    public List<CategoryDTO> selectCategoryList(CategoryVO categoryVO) {
        List<CategoryDTO> categoryDTOList =  categoryDao.selectCategoryList(categoryVO);
        return categoryDTOList;
    }

    @Override
    public CategoryDTO selectCategoryById(CategoryVO categoryVO) {
        CategoryDTO categoryDTO =  categoryDao.selectCategoryById(categoryVO);
        return categoryDTO;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveCategory(CategoryVO categoryVO) {
        Integer maxId = categoryDao.getMaxId();
        if (maxId==null){
            maxId=0;
        }
        //保存中文版modules
        Integer categoryId = maxId+1;
        String currentLang = categoryVO.getLang();
        Category category = BeanCopyUtils.copyObject(categoryVO, Category.class);
        category.setCategoryId(categoryId);
        category.setCreateTime(LocalDateTime.now());
        category.setCreateByUid(Math.toIntExact(SecurityUtils.getUserId()));
        ArrayList<CategoryI18n> categoryI18nArrayList = new ArrayList<>();
        CategoryI18n categoryI18n = BeanCopyUtils.copyObject(categoryVO, CategoryI18n.class);
        categoryI18n.setCategoryId(categoryId);
        categoryI18nArrayList.add(categoryI18n);
        List<LanguageDTO> languageDTOS = languageDao.LanguageListExcludeCurrent(currentLang);
        int size = languageDTOS.size();
        if (size>0){
            GlobalThreadPool taskManager = GlobalThreadPool.getInstance();
            CountDownLatch latch = new CountDownLatch(size); // 初始值是线程数量减一
            for (int i = 0; i < size; i++) {
                String lang = languageDTOS.get(i).getLang();
                taskManager.addTask(()->{
                    CategoryI18n categoryI18n1 = TranslationUtils.translationObject(categoryI18n, CategoryI18n.class, "auto", lang);
                    categoryI18n1.setLang(lang);
                    categoryI18nArrayList.add(categoryI18n1);
                    latch.countDown(); // 确保无论如何都减少计数
                });
            }
            try {
                latch.await(); // 等待所有任务完成
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        this.saveOrUpdate(category);
        categoryI18nService.saveOrUpdateBatch(categoryI18nArrayList);
    }

    @Override
    public void updateCategory(CategoryVO categoryVO) {
        String lang = categoryVO.getLang();
        Category category = BeanCopyUtils.copyObject(categoryVO, Category.class);
        category.setUpdateByUid(Math.toIntExact(SecurityUtils.getUserId()));
        category.setUpdateTime(LocalDateTime.now());
        CategoryI18n categoryI18n = categoryI18nDao.selectOne(new LambdaQueryWrapper<CategoryI18n>()
                .eq(CategoryI18n::getCategoryId, categoryVO.getCategoryId())
                .eq(CategoryI18n::getLang, categoryVO.getLang()));
        categoryI18n.setCategoryName(categoryVO.getCategoryName());
        categoryI18n.setCategoryDesc(categoryVO.getCategoryDesc());
        categoryI18n.setImgUrl(categoryVO.getImgUrl());
        this.saveOrUpdate(category);
        categoryI18nService.saveOrUpdate(categoryI18n);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String deleteCategoryByIds(Integer[] categoryIds) {
        //查看关系表 faq_tag  product_tag
        try{
            List<Category> categories = categoryDao.selectList(new LambdaQueryWrapper<Category>().in(Category::getCategoryId, categoryIds));
            Map<Integer, List<Integer>> collect = categories.stream()
                    .collect(Collectors.groupingBy(Category::getCategoryType,
                            Collectors.mapping(Category::getCategoryId, Collectors.toList())));
            if (collect.get(1)!=null){
                System.out.println("现在删除的是产品分类");
                productCategoryDao.delete(new LambdaQueryWrapper<ProductCategory>().in(ProductCategory::getCategoryId,collect.get(1)));
            }
            if (collect.get(2)!=null){
                System.out.println("现在删除的是课程分类");
                courseCategoryDao.delete(new LambdaQueryWrapper<CourseCategory>().in(CourseCategory::getCategoryId,collect.get(2)));
            }
            if (collect.get(3)!=null){
                System.out.println("现在删除的是文件分类");
                fileCategoryDao.delete(new LambdaQueryWrapper<FileCategory>().in(FileCategory::getCategoryId,collect.get(3)));
            }
            categoryDao.deleteBatchIds(Arrays.asList(categoryIds));
            categoryI18nDao.delete(new LambdaQueryWrapper<CategoryI18n>().in(CategoryI18n::getCategoryId,Arrays.asList(categoryIds)));
            return null;
        }catch (Exception e){
            return "修改失败,请检查数据一致性";
        }

    }
}
