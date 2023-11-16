package com.alphay.boot.official.controller;

import com.alphay.boot.common.annotation.Log;
import com.alphay.boot.common.core.controller.BaseController;
import com.alphay.boot.common.core.domain.AjaxResult;
import com.alphay.boot.common.core.page.TableDataInfo;
import com.alphay.boot.common.enums.BusinessType;
import com.alphay.boot.official.dto.SpecsParamsDTO;
import com.alphay.boot.official.service.SpecsParamsService;
import com.alphay.boot.official.vo.SpecsParamsVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "产品参数")
@RestController
@CrossOrigin
@RequestMapping("/official/product/specs/params")
public class SpecsParamsController extends BaseController {


    @Autowired
    private SpecsParamsService specsParamsService;


    @ApiOperation(value = "查询产品参数List")
    @PreAuthorize("@ss.hasPermi('official:params:list')")
    @PostMapping("/list")
    public TableDataInfo getProductSpecsList(@RequestBody SpecsParamsVO specsParamsVO){
        startPage();
        List<SpecsParamsDTO>  specsParamsDTOList = specsParamsService.getSpecsParamsList(specsParamsVO);
        return getDataTable(specsParamsDTOList);
    }

    /**
     * 根据paramsId获取参数详细信息
     */
    @PreAuthorize("@ss.hasPermi('official:params:query')")
    @PostMapping(value = "/detail")
    public AjaxResult getSpecsParamsDetail(@RequestBody SpecsParamsVO specsParamsVO) {
        SpecsParamsDTO specsParamsDTO = specsParamsService.getSpecsParamsDetail(specsParamsVO);
        return success(specsParamsDTO);
    }

    /**
     * 为产品添加一个规格和参数
     */
    @PreAuthorize("@ss.hasPermi('official:params:insert')")
    @Log(title = "为产品新增一个参数", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult saveSpecsParamsDetail(@RequestBody SpecsParamsVO specsParamsVO){
        specsParamsService.saveSpecsParamsDetail(specsParamsVO);
        return AjaxResult.success();
    }

    /**
     * 修改一个产品规格
     */
    @ApiOperation(value = "修改产品规格")
    @PreAuthorize("@ss.hasPermi('official:params:update')")
    @Log(title = "修改产品规格", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult updateSpecsParamsDetail(@RequestBody SpecsParamsVO specsParamsVO){
        specsParamsService.updateSpecsParamsDetail(specsParamsVO);
        return success();
    }


    /**
     * 删除产品规格连带删除参数和其他语言版本
     */
    @PreAuthorize("@ss.hasPermi('official:specs:remove')")
    @Log(title = "删除产品参数", businessType = BusinessType.DELETE)
    @DeleteMapping("/{paramsIds}")
    public AjaxResult removeSpecsParamsDetail(@PathVariable Integer[] paramsIds) {
        specsParamsService.removeSpecsParamsDetail(paramsIds);
        return success();
    }



}
