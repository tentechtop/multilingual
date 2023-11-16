package com.alphay.boot.official.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.alphay.boot.official.dao.FaqCategoryDao;
import com.alphay.boot.official.entity.FaqCategory;
import com.alphay.boot.official.service.FaqCategoryService;
import org.springframework.stereotype.Service;

@Service
public class FaqCategoryServiceImpl extends ServiceImpl<FaqCategoryDao, FaqCategory> implements FaqCategoryService {
}
