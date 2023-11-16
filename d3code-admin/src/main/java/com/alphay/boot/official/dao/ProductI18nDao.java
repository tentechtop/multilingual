package com.alphay.boot.official.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.alphay.boot.official.entity.ProductI18n;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductI18nDao extends BaseMapper<ProductI18n> {

   ProductI18n getI18nByLangAndPId(@Param("lang") String lang, @Param("productId") Integer productId);
}
