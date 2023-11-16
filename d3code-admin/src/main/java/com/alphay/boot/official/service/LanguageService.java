package com.alphay.boot.official.service;

import com.alphay.boot.official.dto.LanguageDTO;
import com.alphay.boot.official.entity.Language;
import com.alphay.boot.official.vo.LanguageVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface LanguageService extends IService<Language> {

    /**
     * 添加或者修改语言
     */

    void saveOrUpdateLanguage(LanguageVO languageVO);


    /**
     * 删除语言 删除是不再列表中展示
     */
    void deleteLanguageByLanguageId(Integer[] languageId);

    /**
     * 分页语言列表
     */
    List<LanguageDTO> selectLanguageList(LanguageVO languageVO);

    /**
     * 不分页查询语言列表
     */
    List<LanguageDTO> LanguageList();



    /**
     * 启用语言
     */
    void useLanguageById(Integer languageId);


    /**
     * 禁用语言
     * @param languageId
     */
    void disableLanguage(Integer languageId);

    LanguageDTO selectLanguageById(Integer languageId);


    /**
     * 根据 当前语言 找出除当前语言的list
     */
    List<LanguageDTO> LanguageListExcludeCurrent(String lang);


}
