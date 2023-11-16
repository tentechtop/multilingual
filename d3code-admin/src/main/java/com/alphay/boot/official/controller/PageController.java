package com.alphay.boot.official.controller;

import com.alphay.boot.common.annotation.Log;
import com.alphay.boot.common.core.controller.BaseController;
import com.alphay.boot.common.core.domain.AjaxResult;
import com.alphay.boot.common.core.page.TableDataInfo;
import com.alphay.boot.common.enums.BusinessType;
import com.alphay.boot.official.dto.page.PageDTO;
import com.alphay.boot.official.entity.Page;
import com.alphay.boot.official.service.LanguageService;
import com.alphay.boot.official.service.ModuleService;
import com.alphay.boot.official.service.PageService;
import com.alphay.boot.official.vo.page.PageDetailVO;
import com.alphay.boot.official.vo.page.PageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "页面内容请求")
@RestController
@CrossOrigin
@RequestMapping("/official/page")
public class PageController extends BaseController {


    @Autowired
    private PageService pageService;

    @Autowired
    private ModuleService moduleService;

    @Autowired
    private LanguageService languageService;

    /**
     * 获取页面
     * @param pageVO
     * @return
     */
    @PreAuthorize("@ss.hasPermi('official:page:list')")
    @ApiOperation(value = "获取所有页面的内容")
    @GetMapping("/list")
    public TableDataInfo getPageList(PageVO pageVO){
        startPage();
        List<Page> list = pageService.getPageList(pageVO);
        return getDataTable(list);
    }


    @PreAuthorize("@ss.hasPermi('system:notice:query')")
    @GetMapping(value = "/{pageId}")
    public AjaxResult getPageInfo(@PathVariable Integer pageId){
       Page page = pageService.getPageInfo(pageId);
        return success(page);
    }

    /**
     * 根据页面Id和语言获取 整个页面详细
     */
    @PreAuthorize("@ss.hasPermi('official:page:query')")
    @GetMapping(value = "/detail")
    public AjaxResult getPageAllInfo(PageVO pageVO) {
        PageDTO pageDTO = pageService.getPageAllinfo(pageVO);
        return success(pageDTO);
    }

    /**
     * 新增一个页面 普通的
     */
    @PreAuthorize("@ss.hasPermi('official:page:add')")
    @Log(title = "新增一个页面", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody PageVO pageVO) {
       int i =pageService.add(pageVO);
        return toAjax(i);
    }


    /**
     * 保存整个页面内容 页面的模块和子模块
     */
    @PreAuthorize("@ss.hasPermi('official:page:savePageAllInfo')")
    @Log(title = "新增完整页面", businessType = BusinessType.INSERT)
    @PostMapping("/savePageAllInfo")
    public AjaxResult savePageAllInfo(@RequestBody PageDetailVO pageDetailVO){
        pageService.savePageAllInfo(pageDetailVO);
        return AjaxResult.success();
    }


    /**
     * 修改页面内容
     */
    @PreAuthorize("@ss.hasPermi('official:page:edit')")
    @Log(title = "修改页面内容", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody PageVO pageVO) {
        int i = pageService.edit(pageVO);
        return toAjax(i);
    }


    /**
     * 删除页面
     */
    @PreAuthorize("@ss.hasPermi('official:page:remove')")
    @Log(title = "删除页面", businessType = BusinessType.DELETE)
    @DeleteMapping("/{pageIds}")
    public AjaxResult remove(@PathVariable Integer[] pageIds) {
        String s = pageService.deletePageByIds(pageIds);
        if (s!=null){
            return AjaxResult.warn(s);
        }else {
            return AjaxResult.success();
        }
    }



    /*根据 语言  获取整个网站的页面内容  List<PageDTO> */


}
