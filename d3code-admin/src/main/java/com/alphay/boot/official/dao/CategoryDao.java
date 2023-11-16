package com.alphay.boot.official.dao;

import com.alphay.boot.official.dto.CategoryDTO;
import com.alphay.boot.official.entity.Category;
import com.alphay.boot.official.vo.CategoryVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryDao extends BaseMapper<Category> {
    List<CategoryDTO> selectCategoryList(CategoryVO categoryVO);

    CategoryDTO selectCategoryById(CategoryVO categoryVO);

    @Select("select  max(category_id) from off_category")
    Integer getMaxId();
}
