package com.alphay.boot.official.dao;

import com.alphay.boot.official.dto.ProductSpecsDTO;
import com.alphay.boot.official.vo.ProductSpecsVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.alphay.boot.official.entity.ProductSpecs;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductSpecsDao extends BaseMapper<ProductSpecs> {
    List<ProductSpecsDTO> getProductSpecsList(ProductSpecsVO productSpecsVO);

    ProductSpecsDTO getProductSpecsDetail(ProductSpecsVO productSpecsVO);

    @Select("select MAX(specs_id) from off_product_specs")
    Integer getMaxId();
}
