package com.alphay.boot.official.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.alphay.boot.official.dao.CourseCategoryDao;
import com.alphay.boot.official.entity.CourseCategory;
import com.alphay.boot.official.service.CourseCategoryService;
import org.springframework.stereotype.Service;

@Service
public class CourseCategoryServiceImpl extends ServiceImpl<CourseCategoryDao, CourseCategory> implements CourseCategoryService {
}
