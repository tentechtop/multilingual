package com.alphay.boot.official.controller;

import com.alphay.boot.common.annotation.Log;
import com.alphay.boot.common.core.controller.BaseController;
import com.alphay.boot.common.core.domain.AjaxResult;
import com.alphay.boot.common.core.page.TableDataInfo;
import com.alphay.boot.common.enums.BusinessType;
import com.alphay.boot.official.dto.CategoryDTO;
import com.alphay.boot.official.service.CategoryService;
import com.alphay.boot.official.vo.CategoryVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "分类模块")
@RestController
@CrossOrigin
@RequestMapping("/official/category")
public class CategoryController extends BaseController {

    @Autowired
    private CategoryService categoryService;

    //查看分类
    @PreAuthorize("@ss.hasPermi('official:category:list')")
    @GetMapping("/list")
    public TableDataInfo list(CategoryVO categoryVO) {
        startPage();
        List<CategoryDTO> categoryDTOList = categoryService.selectCategoryList(categoryVO);
        return getDataTable(categoryDTOList);
    }

    /**
     * 根据Id获取
     */
    @PreAuthorize("@ss.hasPermi('official:category:query')")
    @GetMapping(value = "/detail")
    public AjaxResult getInfo(CategoryVO categoryVO) {
       CategoryDTO categoryDTO  =  categoryService.selectCategoryById(categoryVO);
        return success(categoryDTO);
    }

    /**
     * 添加一个分类 lang  pageId
     */
    @PreAuthorize("@ss.hasPermi('official:category:add')")
    @Log(title = "添加一个分类", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult saveCategory(@Validated @RequestBody CategoryVO categoryVO) {
        categoryService.saveCategory(categoryVO);
        return AjaxResult.success();
    }

    /**
     * 修改分类
     */
    @ApiOperation(value = "修改分类")
    @PreAuthorize("@ss.hasPermi('official:category:update')")
    @Log(title = "修改分类", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult updateCategory(@RequestBody CategoryVO categoryVO){
        categoryService.updateCategory(categoryVO);
        return success();
    }

    /**
     * 删除分类
     */
    @PreAuthorize("@ss.hasPermi('official:category:remove')")
    @Log(title = "删除分类", businessType = BusinessType.DELETE)
    @DeleteMapping("/{categoryIds}")
    public AjaxResult remove(@PathVariable Integer[] categoryIds) {
        String s = categoryService.deleteCategoryByIds(categoryIds);
        if (s!=null){
            return  error(s);
        }else {
            return AjaxResult.success();
        }
    }

}
