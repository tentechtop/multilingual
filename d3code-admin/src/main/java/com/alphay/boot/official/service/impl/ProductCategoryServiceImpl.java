package com.alphay.boot.official.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.alphay.boot.official.dao.ProductCategoryDao;
import com.alphay.boot.official.entity.ProductCategory;
import com.alphay.boot.official.service.ProductCategoryService;
import org.springframework.stereotype.Service;

@Service
public class ProductCategoryServiceImpl extends ServiceImpl<ProductCategoryDao, ProductCategory> implements ProductCategoryService {
}
