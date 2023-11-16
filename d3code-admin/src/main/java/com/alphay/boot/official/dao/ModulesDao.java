package com.alphay.boot.official.dao;

import com.alphay.boot.official.dto.ModulesExtDTO;
import com.alphay.boot.official.dto.ModulesInfoDTO;
import com.alphay.boot.official.vo.ModulesInfoVO;
import com.alphay.boot.official.vo.ModulesRequestVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.alphay.boot.official.dto.ModulesDTO;
import com.alphay.boot.official.entity.Modules;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModulesDao extends BaseMapper<Modules> {

    /**
     * 根据分类和语言查询
     * @param lang
     * @param categoryId
     * @return
     */
    List<ModulesDTO> getModulesByCategory(@Param("lang")String lang,@Param("categoryId") Integer categoryId);

    /**
     * 根据分类和语言查询Modules
     */
    List<Modules> getModulesList(@Param("lang")String lang,@Param("categoryId") Integer categoryId);



    @Select("SELECT MAX(modules_id) FROM off_modules")
    Integer getMaxId();

    List<ModulesDTO> selectModulesByLangAndId(@Param("lang") String lang, @Param("pageId")Integer pageId);

    ModulesInfoDTO getModulesByLang(ModulesInfoVO modulesInfoVO);

    List<ModulesExtDTO> selectModulesExtDTO(ModulesRequestVO modulesRequestVO);

    ModulesExtDTO selectModulesExtDTOOne(ModulesRequestVO modulesRequestVO);
}
