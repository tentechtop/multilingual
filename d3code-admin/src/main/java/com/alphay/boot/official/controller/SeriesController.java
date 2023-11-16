package com.alphay.boot.official.controller;

import com.alphay.boot.common.annotation.Log;
import com.alphay.boot.common.core.controller.BaseController;
import com.alphay.boot.common.core.domain.AjaxResult;
import com.alphay.boot.common.core.page.TableDataInfo;
import com.alphay.boot.common.enums.BusinessType;
import com.alphay.boot.official.dto.SeriesDTO;
import com.alphay.boot.official.service.SeriesService;
import com.alphay.boot.official.vo.SeriesVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "产品系列模块")
@RestController
@CrossOrigin
@RequestMapping("/official/series")
public class SeriesController extends BaseController {

    @Autowired
    private SeriesService seriesService;

    /** 根据语言获取和类型获取标签列表 */
    @PreAuthorize("@ss.hasPermi('official:series:list')")
    @GetMapping("/list")
    public TableDataInfo list(SeriesVO seriesVO) {
        startPage();
        List<SeriesDTO> seriesDTOS  = seriesService.selectList(seriesVO);
        return getDataTable(seriesDTOS);
    }

    /**
     * 根据id获取标签详细信息
     */
    @PreAuthorize("@ss.hasPermi('official:series:query')")
    @GetMapping(value = "/detail")
    public AjaxResult getInfo(SeriesVO seriesVO) {
        SeriesDTO seriesDTO =  seriesService.selectById(seriesVO);
        return success(seriesDTO);
    }

    /**
     * 系列标签
     */
    @PreAuthorize("@ss.hasPermi('official:series:add')")
    @Log(title = "新增系列", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SeriesVO seriesVO) {
        seriesService.saveSeries(seriesVO);
        return success();
    }

    /**
     * 修改系列
     */
    @PreAuthorize("@ss.hasPermi('official:series:update')")
    @ApiOperation(value = "修改标签")
    @Log(title = "修改系列", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult saveOrUpdateModules(@RequestBody SeriesVO seriesVO){
        seriesService.updateSeries(seriesVO);
        return success();
    }

    /**
     * 删除系列
     */
    @PreAuthorize("@ss.hasPermi('official:tag:remove')")
    @Log(title = "删除系列", businessType = BusinessType.DELETE)
    @DeleteMapping("/{seriesIds}")
    public AjaxResult remove(@PathVariable Integer[] seriesIds) {
        String s = seriesService.deleteSeriesByIds(seriesIds);
        if (s!=null){
            return  error(s);
        }else {
            return AjaxResult.success();
        }
    }
}
