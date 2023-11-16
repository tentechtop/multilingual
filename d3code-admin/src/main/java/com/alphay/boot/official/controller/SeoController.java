package com.alphay.boot.official.controller;

import com.alphay.boot.common.annotation.Log;
import com.alphay.boot.common.core.controller.BaseController;
import com.alphay.boot.common.core.domain.AjaxResult;
import com.alphay.boot.common.core.page.TableDataInfo;
import com.alphay.boot.common.enums.BusinessType;
import com.alphay.boot.official.entity.Seo;
import com.alphay.boot.official.service.SeoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "seo模块")
@RestController
@CrossOrigin
@RequestMapping("/official/seo")
public class SeoController extends BaseController {

    @Autowired
    private SeoService seoService;

    //根据页面id 获取seo LIst
    @PreAuthorize("@ss.hasPermi('official:seo:list')")
    @GetMapping("/list")
    public TableDataInfo list(Seo seo) {
        startPage();
        List<Seo> seoList = seoService.selectNewsList(seo);
        return getDataTable(seoList);
    }


    /**
     * 根据新闻Id获取Seo详细内容
     */
    @PreAuthorize("@ss.hasPermi('official:seo:query')")
    @GetMapping(value = "/{seoId}")
    public AjaxResult getInfo(@PathVariable Integer seoId) {
        Seo seo =seoService.selectSeoById(seoId);
        return success(seo);
    }

    /**
     * 新增一个Seo
     */
    @PreAuthorize("@ss.hasPermi('official:seo:add')")
    @ApiOperation(value = "新增一个页面Seo以及语言版本")
    @Log(title = "新增页面SEO", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Seo seo){
        seoService.saveOrUpdateSeo(seo);
        return AjaxResult.success();
    }



    /**
     * 修改一个Seo
     */
    @PreAuthorize("@ss.hasPermi('official:seo:edit')")
    @ApiOperation(value = "修改一个Seo")
    @Log(title = "修改SEO", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Seo seo){
        seoService.updateSeo(seo);
        return AjaxResult.success();
    }

    /**
     * 根据Id删除新闻
     */
    @PreAuthorize("@ss.hasPermi('official:news:remove')")
    @Log(title = "删除新闻", businessType = BusinessType.DELETE)
    @DeleteMapping("/{seoIds}")
    public AjaxResult remove(@PathVariable Integer[] seoIds) {
        seoService.deleteSeoByIds(seoIds);
        return AjaxResult.success();
    }






}
