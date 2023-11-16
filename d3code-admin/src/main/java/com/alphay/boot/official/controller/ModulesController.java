package com.alphay.boot.official.controller;

import com.alphay.boot.common.annotation.Log;
import com.alphay.boot.common.core.controller.BaseController;
import com.alphay.boot.common.core.domain.AjaxResult;
import com.alphay.boot.common.core.page.TableDataInfo;
import com.alphay.boot.common.enums.BusinessType;
import com.alphay.boot.official.dto.ModulesExtDTO;
import com.alphay.boot.official.service.ModuleService;
import com.alphay.boot.official.vo.ModulesInfoVO;
import com.alphay.boot.official.vo.ModulesRequestVO;
import com.alphay.boot.official.vo.ModulesVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "页面模块内容请求")
@RestController
@CrossOrigin
@RequestMapping("/official/modules")
public class ModulesController extends BaseController {

    @Autowired
    private ModuleService moduleService;


    /**
     * 根据lang 和 pageId 获取Modules
     */
    @PreAuthorize("@ss.hasPermi('official:modules:list')")
    @GetMapping("/list")
    public TableDataInfo list(ModulesRequestVO modulesRequestVO) {
        startPage();
        List<ModulesExtDTO>  modulesList=moduleService.selectModulesList(modulesRequestVO);
        return getDataTable(modulesList);
    }


    /**
     * 根据通知公告编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('official:modules:query')")
    @GetMapping(value = "/detail")
    public AjaxResult getInfo(ModulesRequestVO modulesRequestVO) {
        ModulesExtDTO modulesExtDTO =  moduleService.selectModulesExtDTO(modulesRequestVO);
        return success(modulesExtDTO);
    }



    /**
     * 添加一个模块 lang  pageId
     */
    @PreAuthorize("@ss.hasPermi('official:modules:add')")
    @Log(title = "添加一个模块", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody ModulesInfoVO modulesInfoVO) {
        moduleService.saveModules(modulesInfoVO);
        return AjaxResult.success();
    }


    /**
     * 修改模块
     */
    @ApiOperation(value = "修改模块")
    @PutMapping
    public AjaxResult saveOrUpdateModules(@RequestBody ModulesVO modulesVo){
        System.out.println("查询单数"+modulesVo);
        moduleService.updateModules(modulesVo);
        return success();
    }


    /**
     * 删除模块
     */
    @PreAuthorize("@ss.hasPermi('system:notice:remove')")
    @Log(title = "删除模块", businessType = BusinessType.DELETE)
    @DeleteMapping("/{modulesIds}")
    public AjaxResult remove(@PathVariable Integer[] modulesIds) {
        String s = moduleService.deleteModulesByIds(modulesIds);
        if (s!=null){
            return  error(s);
        }else {
            return AjaxResult.success();
        }
    }

}
