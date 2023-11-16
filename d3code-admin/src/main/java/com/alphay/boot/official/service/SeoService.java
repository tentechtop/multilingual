package com.alphay.boot.official.service;

import com.alphay.boot.official.entity.Seo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SeoService extends IService<Seo> {

    List<Seo> selectNewsList(Seo seo);

    Seo selectSeoById(Integer seoId);

    void saveOrUpdateSeo(Seo seo);

    void updateSeo(Seo seo);

    void deleteSeoByIds(Integer[] seoIds);
}
