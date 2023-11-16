package com.alphay.boot.official.controller;


import com.alphay.boot.common.annotation.Log;
import com.alphay.boot.common.core.controller.BaseController;
import com.alphay.boot.common.core.domain.AjaxResult;
import com.alphay.boot.common.core.page.TableDataInfo;
import com.alphay.boot.common.enums.BusinessType;
import com.alphay.boot.official.dto.ProductSpecsDTO;
import com.alphay.boot.official.service.ProductSpecsService;
import com.alphay.boot.official.vo.ProductSpecsVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "产品规格模块")
@RestController
@CrossOrigin
@RequestMapping("/official/product/specs")
public class ProductSpecsController extends BaseController {

    @Autowired
    private ProductSpecsService productSpecsService;


    @ApiOperation(value = "查询产品参数List")
    @PreAuthorize("@ss.hasPermi('official:specs:list')")
    @PostMapping("/list")
    public TableDataInfo getProductSpecsList(@RequestBody ProductSpecsVO productSpecsVO){
        startPage();
        List<ProductSpecsDTO> productSpecsList = productSpecsService.getProductSpecsList(productSpecsVO);
        return getDataTable(productSpecsList);
    }

    /**
     * 根据specsId获取标签详细信息
     */
    @PreAuthorize("@ss.hasPermi('official:specs:query')")
    @PostMapping(value = "/detail")
    public AjaxResult getProductSpecsDetail(@RequestBody ProductSpecsVO productSpecsVO) {
        ProductSpecsDTO productSpecsDTO = productSpecsService.getProductSpecsDetail(productSpecsVO);
        return success(productSpecsDTO);
    }


    /**
     * 为产品添加一个规格和参数
     */
    @PreAuthorize("@ss.hasPermi('official:specs:insert')")
    @Log(title = "为产品新增一个参数", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult saveProductSpecsDetail(@RequestBody ProductSpecsVO productSpecsVO){
        productSpecsService.saveProductSpecsDetail(productSpecsVO);
        return AjaxResult.success();
    }

    /**
     * 修改一个产品规格
     */
    @ApiOperation(value = "修改产品规格")
    @PreAuthorize("@ss.hasPermi('official:specs:update')")
    @Log(title = "修改产品规格", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult updateProductSpecsDetail(@RequestBody ProductSpecsVO productSpecsVO){
        productSpecsService.updateProductSpecsDetail(productSpecsVO);
        return success();
    }


    /**
     * 删除产品规格连带删除参数和其他语言版本
     */
    @PreAuthorize("@ss.hasPermi('official:specs:remove')")
    @Log(title = "删除产品参数", businessType = BusinessType.DELETE)
    @DeleteMapping("/{specsIds}")
    public AjaxResult removeProductSpecsDetail(@PathVariable Integer[] specsIds) {
        productSpecsService.removeProductSpecsDetail(specsIds);
        return success();
    }





}
