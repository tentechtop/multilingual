package com.alphay.boot.official.dao;

import com.alphay.boot.official.dto.ProductMediaDTO;
import com.alphay.boot.official.vo.ProductMediaVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.alphay.boot.official.entity.ProductMedia;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductMediaDao extends BaseMapper<ProductMedia> {
    List<ProductMediaDTO> getProductMediaList(ProductMediaVO productMediaVO);

    ProductMediaDTO getProductMediaDetail(ProductMediaVO productMediaVO);


    @Select("select max(media_id) from off_product_media")
    Integer getMaxId();
}
