package com.alphay.boot.official.service;

import com.alphay.boot.official.dto.ProductMediaDTO;
import com.alphay.boot.official.vo.ProductMediaVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.alphay.boot.official.entity.ProductMedia;

import java.util.List;

public interface ProductMediaService extends IService<ProductMedia> {
    List<ProductMediaDTO> getProductMediaList(ProductMediaVO productMediaVO);

    ProductMediaDTO getProductMediaDetail(ProductMediaVO productMediaVO);

    void saveProductMediaDetail(ProductMediaVO productMediaVO);

    void updateProductMediaDetail(ProductMediaVO productMediaVO);

    String removeProductMediaDetail(Integer[] mediaIds);
}
