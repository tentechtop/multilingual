package com.alphay.boot.official.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.alphay.boot.official.dao.ProductSeriesDao;
import com.alphay.boot.official.entity.ProductSeries;
import com.alphay.boot.official.service.ProductSeriesService;
import org.springframework.stereotype.Service;

@Service
public class ProductSeriesServiceImpl extends ServiceImpl<ProductSeriesDao, ProductSeries> implements ProductSeriesService {
}
