package com.alphay.boot.official.dao;

import com.alphay.boot.official.dto.ProductDTO;
import com.alphay.boot.official.vo.ProductVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.alphay.boot.official.entity.Product;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDao extends BaseMapper<Product> {


    /**
     * 根据语言分页查找产品列表List<ProductDTO>
     */


    /**
     * 根据语言查找但个ProductDTO
     */


    /**
     * 查找产品表的最大ID
     */

    @Select("SELECT MAX(product_id) from off_product")
    Integer getMaxId();


    List<ProductDTO> getProductList(ProductVO productVO);

    ProductDTO getProductDetail(ProductVO productVO);
}
