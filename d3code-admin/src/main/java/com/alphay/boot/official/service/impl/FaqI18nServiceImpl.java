package com.alphay.boot.official.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.alphay.boot.official.dao.FaqI18nDao;
import com.alphay.boot.official.entity.FaqI18n;
import com.alphay.boot.official.service.FaqI18nService;
import org.springframework.stereotype.Service;

@Service
public class FaqI18nServiceImpl extends ServiceImpl<FaqI18nDao, FaqI18n>  implements FaqI18nService {
}
