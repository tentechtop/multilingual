package com.alphay.boot.official.controller;

import com.alphay.boot.common.annotation.Log;
import com.alphay.boot.common.core.controller.BaseController;
import com.alphay.boot.common.core.domain.AjaxResult;
import com.alphay.boot.common.core.page.TableDataInfo;
import com.alphay.boot.common.enums.BusinessType;
import com.alphay.boot.official.dto.ProductDTO;
import com.alphay.boot.official.service.ProductService;
import com.alphay.boot.official.vo.ProductVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "产品模块")
@RestController
@CrossOrigin
@RequestMapping("/official/product")
public class ProductController extends BaseController {

    /**
     * 操作 菜单 修改产品价格 修改产品文件 修改产品媒体 修改产品参数
     */

    @Autowired
    private ProductService productService;

    /**
     * 新增或修改产品
     */
    @ApiOperation(value = "查询产品List")
    @PreAuthorize("@ss.hasPermi('official:product:list')")
    @PostMapping("/list")
    public TableDataInfo getProductList(@RequestBody ProductVO productVO){
        startPage();
        List<ProductDTO> productDTOList = productService.getProductList(productVO);
        return getDataTable(productDTOList);
    }

    /**
     * 根据产品Id获取和lang获取单个产品详情 如果lang为空默认为zh
     */
    @PreAuthorize("@ss.hasPermi('official:product:query')")
    @GetMapping(value = "/detail")
    public AjaxResult getInfo(ProductVO productVO) {
        System.out.println("当前查询的内容"+productVO);
        ProductDTO productDTO =productService.selectProductDetail(productVO);
        System.out.println("查询到的数据是"+productDTO);
        return success(productDTO);
    }


    /**
     * 添加一个产品
     */
    @PreAuthorize("@ss.hasPermi('official:product:add')")
    @Log(title = "添加一个产品", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult saveProduct(@Validated @RequestBody ProductVO productVO) {
        productService.saveProductDetail(productVO);
        return AjaxResult.success();
    }


    /**
     * 修改一个产品
     */
    @ApiOperation(value = "修改产品")
    @PreAuthorize("@ss.hasPermi('official:product:update')")
    @Log(title = "修改产品", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult updateProductDetail(@RequestBody ProductVO productVO){
        System.out.println("修改的数据是"+productVO);
        productService.updateProductDetail(productVO);
        return success();
    }



    /**
     * 删除产品
     */
    @PreAuthorize("@ss.hasPermi('official:faq:remove')")
    @Log(title = "删除产品", businessType = BusinessType.DELETE)
    @DeleteMapping("/{productIds}")
    public AjaxResult removeProduct(@PathVariable Integer[] productIds) {
        productService.deleteProductDetail(productIds);
        return success();
    }





}
