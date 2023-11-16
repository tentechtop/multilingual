package com.alphay.boot.official.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.alphay.boot.official.dao.ProductI18nDao;
import com.alphay.boot.official.entity.ProductI18n;
import com.alphay.boot.official.service.ProductI18nService;
import org.springframework.stereotype.Service;

@Service
public class ProductI18nServiceImpl extends ServiceImpl<ProductI18nDao, ProductI18n> implements ProductI18nService {
}
