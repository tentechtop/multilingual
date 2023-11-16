package com.alphay.boot.official.controller;

import com.alphay.boot.common.annotation.Log;
import com.alphay.boot.common.core.controller.BaseController;
import com.alphay.boot.common.core.domain.AjaxResult;
import com.alphay.boot.common.core.page.TableDataInfo;
import com.alphay.boot.common.enums.BusinessType;
import com.alphay.boot.official.dto.ModulesItemDTO;
import com.alphay.boot.official.service.ModuleItemService;
import com.alphay.boot.official.vo.ModulesRequestVO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "页面模块内容请求")
@RestController
@CrossOrigin
@RequestMapping("/official/modulesItem")
public class ModulesItemController extends BaseController {


    @Autowired
    private ModuleItemService moduleItemService;


    /**
     * 根据ModulesId和lang查询ModulesItemList
     */
    @PreAuthorize("@ss.hasPermi('official:modulesItem:list')")
    @GetMapping("/list")
    public TableDataInfo list(ModulesRequestVO modulesRequestVO) {
        startPage();
        List<ModulesItemDTO> modulesItemDTOS=moduleItemService.selectModulesItemList(modulesRequestVO);
        return getDataTable(modulesItemDTOS);
    }


    /**
     * 根据Id获取当前模块内容的详细
     */
    @PreAuthorize("@ss.hasPermi('official:modulesItem:query')")
    @GetMapping(value = "/{itemId}")
    public AjaxResult getInfo(@PathVariable Integer itemId) {
        ModulesItemDTO modulesItemDTO=moduleItemService.selectModulesItemById(itemId);
        return success(modulesItemDTO);
    }

    /**
     * 新增一个模块Item
     */
    @PreAuthorize("@ss.hasPermi('official:modulesItem:add')")
    @Log(title = "新增一个模块Item", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody ModulesItemDTO modulesItemDTO) {
        System.out.println("查询的参数"+modulesItemDTO);
        moduleItemService.saveModulesItem(modulesItemDTO);
        return success();
    }


    /**
     * 修改模块元素
     */
    @PreAuthorize("@ss.hasPermi('official:modulesItem:edit')")
    @Log(title = "修改模块元素", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody ModulesItemDTO modulesItemDTO) {
        System.out.println("查询的参数"+modulesItemDTO);
        moduleItemService.updateModulesItem(modulesItemDTO);
        return success();
    }


    /**
     * 删除模块元素
     */
    @PreAuthorize("@ss.hasPermi('official:modulesItem:remove')")
    @Log(title = "删除模块元素", businessType = BusinessType.DELETE)
    @DeleteMapping("/{itemIds}")
    public AjaxResult remove(@PathVariable Integer[] itemIds) {
        moduleItemService.deleteItemByIds(itemIds);
        return success();
    }

}
