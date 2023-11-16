package com.alphay.boot.official.service;

import com.alphay.boot.official.dto.ProductDTO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.alphay.boot.official.entity.Product;
import com.alphay.boot.official.vo.ProductVO;

import java.util.List;

public interface ProductService extends IService<Product> {


    List<ProductDTO> getProductList(ProductVO productVO);

    ProductDTO selectProductDetail(ProductVO productVO);

    void saveProductDetail(ProductVO productVO);

    void updateProductDetail(ProductVO productVO);

    void deleteProductDetail(Integer[] productIds);
}
