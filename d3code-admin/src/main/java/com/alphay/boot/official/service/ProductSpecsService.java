package com.alphay.boot.official.service;

import com.alphay.boot.official.dto.ProductSpecsDTO;
import com.alphay.boot.official.vo.ProductSpecsVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.alphay.boot.official.entity.ProductSpecs;

import java.util.List;

public interface ProductSpecsService extends IService<ProductSpecs> {
    List<ProductSpecsDTO> getProductSpecsList(ProductSpecsVO productSpecsVO);

    ProductSpecsDTO getProductSpecsDetail(ProductSpecsVO productSpecsVO);

    void saveProductSpecsDetail(ProductSpecsVO productSpecsVO);

    void updateProductSpecsDetail(ProductSpecsVO productSpecsVO);

    void removeProductSpecsDetail(Integer[] specsIds);
}
