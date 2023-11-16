package com.alphay.boot.official.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.alphay.boot.official.dao.CategoryI18nDao;
import com.alphay.boot.official.entity.CategoryI18n;
import com.alphay.boot.official.service.CategoryI18nService;
import org.springframework.stereotype.Service;

@Service
public class CategoryI18nServiceImpl extends ServiceImpl<CategoryI18nDao, CategoryI18n> implements CategoryI18nService {
}
