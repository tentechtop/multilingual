package com.alphay.boot.official.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.alphay.boot.official.dao.ProductTagDao;
import com.alphay.boot.official.entity.ProductTag;
import com.alphay.boot.official.service.ProductTagService;
import org.springframework.stereotype.Service;

@Service
public class ProductTagServiceImpl extends ServiceImpl<ProductTagDao, ProductTag> implements ProductTagService {
}
