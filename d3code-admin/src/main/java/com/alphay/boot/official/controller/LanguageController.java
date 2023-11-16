package com.alphay.boot.official.controller;

import com.alphay.boot.common.annotation.Log;
import com.alphay.boot.common.core.controller.BaseController;
import com.alphay.boot.common.core.domain.AjaxResult;
import com.alphay.boot.common.core.page.TableDataInfo;
import com.alphay.boot.common.enums.BusinessType;
import com.alphay.boot.official.dto.LanguageDTO;
import com.alphay.boot.official.service.LanguageService;
import com.alphay.boot.official.vo.LanguageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;



@Api(tags = "语言模块")
@RestController
@RequestMapping("/official/language")
public class LanguageController extends BaseController {


    @Autowired
    private LanguageService languageService;


    /**
     * 获取语言列表
     */
    @PreAuthorize("@ss.hasPermi('official:language:list')")
    @ApiOperation(value = "查看语言列表")
    @GetMapping("/list")
    public TableDataInfo list(LanguageVO languageVO){
        System.out.println("语言参数"+languageVO);
        startPage();
        List<LanguageDTO> languageDTOS = languageService.selectLanguageList(languageVO);
        return getDataTable(languageDTOS);
    }


    /**
     * 查看所有语言 在选择时用
     * @return
     */
    @ApiOperation(value = "查看所有语言")
    @PreAuthorize("@ss.hasPermi('official:language:allList')")
    @GetMapping("/allList")
    public AjaxResult LanguageList(){
        List<LanguageDTO> languageDTOS = languageService.LanguageList();
        return AjaxResult.success(languageDTOS);
    }


    /**
     * 根据id获取单个信息
     */
    @PreAuthorize("@ss.hasPermi('official:language:query')")
    @GetMapping(value = "/{languageId}")
    public AjaxResult getInfo(@PathVariable Integer languageId) {
        LanguageDTO language =languageService.selectLanguageById(languageId);
        return success(language);
    }

    /**
     * 新增语言
     * @param languageVO
     * @return
     */
    @PreAuthorize("@ss.hasPermi('official:language:add')")
    @ApiOperation(value = "添加或更新语言")
    @ApiImplicitParam(name = "languageVO", value = "语言请求", required = true, dataType = "LanguageVO")
    @PostMapping
    public AjaxResult add(@Valid @RequestBody LanguageVO languageVO){
        languageService.saveOrUpdateLanguage(languageVO);
        return AjaxResult.success();
    }

    /**
     * 更新语言
     * @param languageVO
     * @return
     */
    @PreAuthorize("@ss.hasPermi('official:language:edit')")
    @ApiOperation(value = "更新语言")
    @ApiImplicitParam(name = "languageVO", value = "语言请求", required = true, dataType = "LanguageVO")
    @PutMapping
    public AjaxResult edit(@Valid @RequestBody LanguageVO languageVO){
        languageService.saveOrUpdateLanguage(languageVO);
        return AjaxResult.success();
    }


    /**
     * 根据id删除语言
     * @param languageIds
     * @return
     */
    @PreAuthorize("@ss.hasPermi('official:language:remove')")
    @ApiOperation(value = "根据id删除语言")
    @Log(title = "语言", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "languageId", value = "语言Id", required = true, dataType = "Integer")
    @DeleteMapping("/{languageIds}")
    public AjaxResult deleteLanguage(@PathVariable("languageIds") Integer[] languageIds){
        languageService.deleteLanguageByLanguageId(languageIds);
        return AjaxResult.success();
    }

    /**
     * 禁用语言
     */
    @PreAuthorize("@ss.hasPermi('official:language:remove')")
    @ApiOperation(value = "根据id禁用语言")
    @ApiImplicitParam(name = "languageId", value = "语言Id", required = true, dataType = "Integer")
    @PutMapping("/disable/{languageId}")
    public AjaxResult disableLanguage(@PathVariable("languageId") Integer languageId){
        languageService.disableLanguage(languageId);
        return AjaxResult.success();
    }

    /**
     * 根据id启用用语言
     */
    @PreAuthorize("@ss.hasPermi('official:language:edit')")
    @ApiOperation(value = "根据id启用用语言")
    @ApiImplicitParam(name = "languageId", value = "语言Id", required = true, dataType = "Integer")
    @PutMapping("/use/{languageId}")
    public AjaxResult useLanguage(@PathVariable("languageId") Integer languageId){
        languageService.useLanguageById(languageId);
        return AjaxResult.success();
    }



}
