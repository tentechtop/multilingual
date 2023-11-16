package com.alphay.boot.official.dao;

import com.alphay.boot.official.entity.Seo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeoDao extends BaseMapper<Seo> {
    List<Seo> selectNewsList(Seo seo);


    @Select("select  max(seo_id) from off_seo)")
    Integer getMaxId();
}
