package com.alphay.boot.official.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.alphay.boot.official.dao.FaqTagDao;
import com.alphay.boot.official.entity.FaqTag;
import com.alphay.boot.official.service.FaqTagService;
import org.springframework.stereotype.Service;

@Service
public class FaqTagServiceImpl extends ServiceImpl<FaqTagDao, FaqTag> implements FaqTagService {
}
