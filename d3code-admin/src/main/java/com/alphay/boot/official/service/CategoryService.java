package com.alphay.boot.official.service;

import com.alphay.boot.official.vo.CategoryVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.alphay.boot.official.dto.CategoryDTO;
import com.alphay.boot.official.entity.Category;

import java.util.List;

public interface CategoryService extends IService<Category> {

    /**
     * 根据分类名称查询分类List 如查询 页面 分类
     */

    List<CategoryDTO> selectCategoryList(CategoryVO categoryVO);

    CategoryDTO selectCategoryById(CategoryVO categoryVO);

    void saveCategory(CategoryVO categoryVO);

    void updateCategory(CategoryVO categoryVO);

    String deleteCategoryByIds(Integer[] categoryIds);
}
