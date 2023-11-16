package com.alphay.boot.official.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.alphay.boot.official.dao.ProductFileDao;
import com.alphay.boot.official.entity.ProductFile;
import com.alphay.boot.official.service.ProductFileService;
import org.springframework.stereotype.Service;

@Service
public class ProductFileServiceImpl extends ServiceImpl<ProductFileDao, ProductFile> implements ProductFileService {
}
