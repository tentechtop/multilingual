package com.alphay.boot.official.service;

import com.alphay.boot.official.dto.SpecsParamsDTO;
import com.alphay.boot.official.vo.SpecsParamsVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.alphay.boot.official.entity.SpecsParams;

import java.util.List;

public interface SpecsParamsService extends IService<SpecsParams> {
    List<SpecsParamsDTO> getSpecsParamsList(SpecsParamsVO specsParamsVO);

    SpecsParamsDTO getSpecsParamsDetail(SpecsParamsVO specsParamsVO);

    void saveSpecsParamsDetail(SpecsParamsVO specsParamsVO);

    void updateSpecsParamsDetail(SpecsParamsVO specsParamsVO);

    void removeSpecsParamsDetail(Integer[] paramsIds);
}
