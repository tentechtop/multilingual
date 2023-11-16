package com.alphay.boot.official.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.alphay.boot.official.dao.TagI18nDao;
import com.alphay.boot.official.entity.TagI18n;
import com.alphay.boot.official.service.TagI18nService;
import org.springframework.stereotype.Service;

@Service
public class TagI18nServiceImpl extends ServiceImpl<TagI18nDao, TagI18n> implements TagI18nService {
}
