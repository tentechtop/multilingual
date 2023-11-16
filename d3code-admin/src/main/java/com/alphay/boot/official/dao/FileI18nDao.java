package com.alphay.boot.official.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.alphay.boot.official.entity.FileI18n;
import org.apache.ibatis.annotations.Param;

public interface FileI18nDao extends BaseMapper<FileI18n> {
    FileI18n getFileI18nByFileIdAndLang(@Param("fileId") Integer fileId, @Param("lang") String lang);
}
