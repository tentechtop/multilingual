package com.alphay.boot.official.dao;

import com.alphay.boot.official.dto.SpecsParamsDTO;
import com.alphay.boot.official.entity.SpecsParams;
import com.alphay.boot.official.vo.SpecsParamsVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpecsParamsDao extends BaseMapper<SpecsParams> {
    @Select("select MAX(params_id) from off_specs_params")
    Integer getMaxId();

    List<SpecsParamsDTO> getSpecsParamsList(SpecsParamsVO specsParamsVO);

    SpecsParamsDTO getSpecsParamsDetail(SpecsParamsVO specsParamsVO);
}
