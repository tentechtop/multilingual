package com.alphay.boot.official.controller;

import com.alphay.boot.common.annotation.Log;
import com.alphay.boot.common.core.controller.BaseController;
import com.alphay.boot.common.core.domain.AjaxResult;
import com.alphay.boot.common.core.page.TableDataInfo;
import com.alphay.boot.common.enums.BusinessType;
import com.alphay.boot.official.dto.ProductMediaDTO;
import com.alphay.boot.official.service.ProductMediaService;
import com.alphay.boot.official.vo.ProductMediaVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "产品媒体")
@RestController
@CrossOrigin
@RequestMapping("/official/product/media")
public class ProductMediaController extends BaseController {

    @Autowired
    private ProductMediaService productMediaService;


    //根据productId lang获取productFileList
    @PreAuthorize("@ss.hasPermi('official:productmedia:list')")
    @PostMapping("/list")
    public TableDataInfo getProductMediaList(@RequestBody ProductMediaVO productMediaVO) {
        startPage();
        List<ProductMediaDTO> productMediaDTOList = productMediaService.getProductMediaList(productMediaVO);
        return getDataTable(productMediaDTOList);
    }


    /**
     * 根据fileId获取文件详细信息
     */
    @PreAuthorize("@ss.hasPermi('official:productmedia:query')")
    @PostMapping(value = "/detail")
    public AjaxResult getProductMediaDetail(@RequestBody  ProductMediaVO productMediaVO) {
        ProductMediaDTO productFileDTO  = productMediaService.getProductMediaDetail(productMediaVO);
        return success(productFileDTO);
    }

    /**
     * 新增产品媒体
     */
    @PreAuthorize("@ss.hasPermi('official:productmedia:insert')")
    @Log(title = "新增", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult saveProductMediaDetail(@RequestBody ProductMediaVO productMediaVO){
        productMediaService.saveProductMediaDetail(productMediaVO);
        return AjaxResult.success();
    }

    /**
     * 修改产品媒体
     */
    @PreAuthorize("@ss.hasPermi('official:productmedia:update')")
    @ApiOperation(value = "修改产品媒体")
    @Log(title = "修改产品媒体", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult updateProductMediaDetail(@RequestBody ProductMediaVO productMediaVO){
        productMediaService.updateProductMediaDetail(productMediaVO);
        return success();
    }


    /**
     * 删除产品媒体
     */
    @PreAuthorize("@ss.hasPermi('official:file:remove')")
    @Log(title = "删除文件", businessType = BusinessType.DELETE)
    @DeleteMapping("/{mediaIds}")
    public AjaxResult removeProductMediaDetail(@PathVariable Integer[] mediaIds) {
        String s = productMediaService.removeProductMediaDetail(mediaIds);
        if (s!=null){
            return  error(s);
        }else {
            return AjaxResult.success();
        }
    }

}
