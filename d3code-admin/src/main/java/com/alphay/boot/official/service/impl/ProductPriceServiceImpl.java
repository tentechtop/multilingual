package com.alphay.boot.official.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.alphay.boot.official.dao.ProductPriceDao;
import com.alphay.boot.official.entity.ProductPrice;
import com.alphay.boot.official.service.ProductPriceService;
import org.springframework.stereotype.Service;

@Service
public class ProductPriceServiceImpl extends ServiceImpl<ProductPriceDao, ProductPrice> implements ProductPriceService {
}
