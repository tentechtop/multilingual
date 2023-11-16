package com.alphay.boot.official.dao;

import com.alphay.boot.official.dto.LanguageDTO;
import com.alphay.boot.official.entity.Language;
import com.alphay.boot.official.vo.LanguageVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LanguageDao extends BaseMapper<Language> {


    /**
     * 分页查询语言
     */
    List<LanguageDTO> LanguageListByPage(LanguageVO languageVO);


    /**
     * 查询语言列表
     * @return
     */
    List<LanguageDTO> LanguageList();


    List<LanguageDTO> LanguageListExcludeCurrent(@Param("lang") String lang);
}
