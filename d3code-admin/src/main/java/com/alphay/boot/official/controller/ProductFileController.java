package com.alphay.boot.official.controller;

import com.alphay.boot.common.annotation.Log;
import com.alphay.boot.common.core.controller.BaseController;
import com.alphay.boot.common.core.domain.AjaxResult;
import com.alphay.boot.common.core.page.TableDataInfo;
import com.alphay.boot.common.enums.BusinessType;
import com.alphay.boot.official.dto.ProductFileDTO;
import com.alphay.boot.official.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "问答模块")
@RestController
@CrossOrigin
@RequestMapping("/official/product/file")
public class ProductFileController extends BaseController {

    @Autowired
    private FileService fileService;

    //根据productId lang获取productFileList
    @PreAuthorize("@ss.hasPermi('official:productFile:list')")
    @PostMapping("/list")
    public TableDataInfo getProductFileList(@RequestBody ProductFileDTO productFileDTO) {
        startPage();
        List<ProductFileDTO> productFileList=fileService.getProductFileList(productFileDTO);
        return getDataTable(productFileList);
    }

    /**
     * 根据fileId获取文件详细信息
     */
    @PreAuthorize("@ss.hasPermi('official:productFile:query')")
    @PostMapping(value = "/detail")
    public AjaxResult getProductFileDetail(@RequestBody  ProductFileDTO productFileDTO) {
        ProductFileDTO productFileDTO1 =  fileService.getProductFileDetail(productFileDTO);
        return success(productFileDTO1);
    }

    /**
     * 新增产品文件
     */
    @PreAuthorize("@ss.hasPermi('official:productFile:insert')")
    @Log(title = "新增", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult saveProductFileDetail(@RequestBody ProductFileDTO productFileDTO){
        fileService.saveProductFileDetail(productFileDTO);
        return AjaxResult.success();
    }

    /**
     * 修改产品文件
     */
    @PreAuthorize("@ss.hasPermi('official:productFile:update')")
    @ApiOperation(value = "修改产品文件")
    @Log(title = "修改产品文件", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult updateProductFileDetail(@RequestBody ProductFileDTO productFileDTO){
        fileService.updateProductFileDetail(productFileDTO);
        return success();
    }

    /**
     * 删除文件
     */
    @PreAuthorize("@ss.hasPermi('official:file:remove')")
    @Log(title = "删除文件", businessType = BusinessType.DELETE)
    @DeleteMapping("/{fileIds}")
    public AjaxResult removeProductFileDetail(@PathVariable Integer[] fileIds) {
        String s = fileService.removeProductFileDetail(fileIds);
        if (s!=null){
            return  error(s);
        }else {
            return AjaxResult.success();
        }
    }

}
