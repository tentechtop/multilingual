package com.alphay.boot.official.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.alphay.boot.official.dao.FileI18nDao;
import com.alphay.boot.official.entity.FileI18n;
import com.alphay.boot.official.service.FileI18nService;
import org.springframework.stereotype.Service;

@Service
public class FileI18nServiceImpl extends ServiceImpl<FileI18nDao, FileI18n> implements FileI18nService {
}
