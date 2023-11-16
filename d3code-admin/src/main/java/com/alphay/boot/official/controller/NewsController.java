package com.alphay.boot.official.controller;

import com.alphay.boot.common.annotation.Log;
import com.alphay.boot.common.core.controller.BaseController;
import com.alphay.boot.common.core.domain.AjaxResult;
import com.alphay.boot.common.core.page.TableDataInfo;
import com.alphay.boot.common.enums.BusinessType;
import com.alphay.boot.official.dto.NewsDTO;
import com.alphay.boot.official.service.NewsService;
import com.alphay.boot.official.vo.NewsVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Api(tags = "新闻模块")
@RestController
@CrossOrigin
@RequestMapping("/official/news")
public class NewsController extends BaseController {

    @Autowired
    private NewsService newsService;

    /** 根据语言获取新闻列表 */
    @PreAuthorize("@ss.hasPermi('official:news:list')")
    @GetMapping("/list")
    public TableDataInfo list(NewsVO newsVo) {
        System.out.println("查询的参数是"+newsVo);
        startPage();
        List<NewsDTO> newsDTOList = newsService.selectNewsList(newsVo);
        System.out.println("newsDTOList数据是"+newsDTOList);
        return getDataTable(newsDTOList);
    }


    /**
     * 根据新闻Id获取新闻详细信息
     */
    @PreAuthorize("@ss.hasPermi('official:news:query')")
    @GetMapping(value = "/{newsId}")
    public AjaxResult getInfo(@PathVariable Integer newsId) {
        return success(newsService.selectNewsById(newsId));
    }

    /**
     * 新增一个新闻
     */
    @PreAuthorize("@ss.hasPermi('official:news:add')")
    @ApiOperation(value = "新增一个news以及语言版本")
    @Log(title = "新增新闻", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody NewsVO newsVO){
        newsService.saveOrUpdateNews(newsVO);
        return AjaxResult.success();
    }


    /**
     * 修改一个新闻
     */
    @PreAuthorize("@ss.hasPermi('official:news:add')")
    @ApiOperation(value = "修改一个news以及语言版本")
    @Log(title = "修改新闻", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody NewsVO newsVO){
        newsService.saveOrUpdateNews(newsVO);
        return AjaxResult.success();
    }


    /**
     * 根据Id删除新闻
     */
    @PreAuthorize("@ss.hasPermi('official:news:remove')")
    @Log(title = "删除新闻", businessType = BusinessType.DELETE)
    @DeleteMapping("/{newsIds}")
    public AjaxResult remove(@PathVariable Integer[] newsIds) {
        return toAjax(newsService.deleteNewsByIds(newsIds));
    }



}
