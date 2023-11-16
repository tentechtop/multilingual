package com.alphay.boot.official.service;

import com.alphay.boot.official.dto.ModulesDTO;
import com.alphay.boot.official.dto.ModulesExtDTO;
import com.alphay.boot.official.dto.ModulesInfoDTO;
import com.alphay.boot.official.entity.Modules;
import com.alphay.boot.official.vo.ModulesInfoVO;
import com.alphay.boot.official.vo.ModulesRequestVO;
import com.alphay.boot.official.vo.ModulesVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface ModuleService extends IService<Modules> {

    /**
     * 添加或修改模块
     */
    void saveOrUpdateModules(ModulesVO modulesVo);


    /**
     * 根据分类和语言查询ModuleList
     */
    List<ModulesDTO> getModuleDTOByCategory(String lang,Integer categoryId);

    List<ModulesDTO> selectModulesByLangAndId(String lang, Integer pageId);

    ModulesInfoDTO getModulesInfo(ModulesInfoVO modulesInfoVO);

    void saveModules(ModulesInfoVO modulesInfoVO);


    void updateModules(ModulesVO modulesVo);

    String deleteModulesByIds(Integer[] modulesIds);

    List<ModulesExtDTO> selectModulesList(ModulesRequestVO modulesRequestVO);

    ModulesExtDTO selectModulesExtDTO(ModulesRequestVO modulesRequestVO);
}
