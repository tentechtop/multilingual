package com.alphay.boot.official.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.alphay.boot.official.dao.FileCategoryDao;
import com.alphay.boot.official.entity.FileCategory;
import com.alphay.boot.official.service.FileCategoryService;
import org.springframework.stereotype.Service;

@Service
public class FileCategoryServiceImpl extends ServiceImpl<FileCategoryDao, FileCategory> implements FileCategoryService {
}
