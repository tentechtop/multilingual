package com.alphay.boot.official.service.impl;

import com.alphay.boot.common.utils.SecurityUtils;
import com.alphay.boot.official.dao.LanguageDao;
import com.alphay.boot.official.dto.LanguageDTO;
import com.alphay.boot.official.entity.Language;
import com.alphay.boot.official.service.LanguageService;
import com.alphay.boot.official.utile.BeanCopyUtils;
import com.alphay.boot.official.vo.LanguageVO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;



@Service
public class LanguageServiceImpl extends ServiceImpl<LanguageDao, Language> implements LanguageService {

    @Autowired
    private LanguageDao languageDao;


/*
http://localhost:9090/admin/language/page/list
    {
            "current": 1,
            "size": 10,
            "keywords": null,
            "categoryId": null,
            "tagId": null,
            "loginType": null,
            "type": null,
            "status": null,
            "startTime": null,
            "endTime": null,
            "isEnable": 1,
            "isDelete": 0,
            "isReview": null
    }
*/


    /**
     * 添加或者修改语言
     * @param languageVO
     */
    @Override
    public void saveOrUpdateLanguage(LanguageVO languageVO) {
        Language language = BeanCopyUtils.copyObject(languageVO, Language.class);
        if (languageVO.getLanguageId()==null){
            language.setCreateByUid(Math.toIntExact(SecurityUtils.getUserId()));
            language.setCreateTime(LocalDateTime.now());
        }else {
            language.setUpdateByUid(Math.toIntExact(SecurityUtils.getUserId()));
            language.setUpdateTime(LocalDateTime.now());
        }
        this.saveOrUpdate(language);
    }


    /**
     * 删除语言
     * @param languageIds
     */
    @Override
    public void deleteLanguageByLanguageId(Integer[] languageIds) {
        //删除语言只是让用户选择不到语言 不是真的删除语言
        List<Language> languages = languageDao.selectBatchIds(Arrays.asList(languageIds));
        languages.forEach(lg->{
            lg.setIsDelete(true); //删除为真
            lg.setIsEnable(false);//可用为假
        });
        this.saveOrUpdateBatch(languages);
    }

    @Override
    public List<LanguageDTO> selectLanguageList(LanguageVO languageVO) {
        System.out.println("dao参数"+languageVO);
        return languageDao.LanguageListByPage(languageVO);
    }

    /**
     * 查询所有语言列表
     * @return
     */
    @Override
    public List<LanguageDTO> LanguageList() {
        List<LanguageDTO> languageDTOS = languageDao.LanguageList();
        return languageDTOS;
    }



    /**
     * 启用语言
     * @param languageId
     */
    @Override
    public void useLanguageById(Integer languageId) {
        Language language = this.getById(languageId);
        language.setIsEnable(true);
        language.setUpdateByUid(Math.toIntExact(SecurityUtils.getUserId()));
        language.setUpdateTime(LocalDateTime.now());
        this.saveOrUpdate(language);
    }

    /**
     * 禁用语言
     * @param languageId
     */
    @Override
    public void disableLanguage(Integer languageId) {
        Language language = this.getById(languageId);
        language.setIsEnable(false);
        language.setUpdateByUid(Math.toIntExact(SecurityUtils.getUserId()));
        language.setUpdateTime(LocalDateTime.now());
        this.saveOrUpdate(language);
    }

    @Override
    public LanguageDTO selectLanguageById(Integer languageId) {
        Language language = languageDao.selectById(languageId);
        LanguageDTO languageDTO = BeanCopyUtils.copyObject(language, LanguageDTO.class);
        return languageDTO;
    }

    @Override
    public List<LanguageDTO> LanguageListExcludeCurrent(String lang) {

        List<LanguageDTO> languageDTOS = languageDao.LanguageListExcludeCurrent(lang);
        return languageDTOS;
    }


}
