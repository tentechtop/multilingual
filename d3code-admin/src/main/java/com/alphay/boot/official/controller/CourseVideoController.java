package com.alphay.boot.official.controller;


import com.alphay.boot.common.annotation.Log;
import com.alphay.boot.common.core.controller.BaseController;
import com.alphay.boot.common.core.domain.AjaxResult;
import com.alphay.boot.common.core.page.TableDataInfo;
import com.alphay.boot.common.enums.BusinessType;
import com.alphay.boot.official.dto.CourseVideoDTO;
import com.alphay.boot.official.service.CourseVideoService;
import com.alphay.boot.official.vo.CourseVideoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "课程视频模块")
@RestController
@CrossOrigin
@RequestMapping("/official/course/video")
public class CourseVideoController extends BaseController {


    @Autowired
    private CourseVideoService courseVideoService;


    /**
     * @param courseVideoVO
     * @return
     */
    @PreAuthorize("@ss.hasPermi('system:video:list')")
    @PostMapping("/list")
    public TableDataInfo getCourseVideoList(@RequestBody CourseVideoVO courseVideoVO) {
        startPage();
        List<CourseVideoDTO> courseVideoDTOList = courseVideoService.getCourseVideoList(courseVideoVO);
        return getDataTable(courseVideoDTOList);
    }

    /**
     * 根据Id和lang获取当前课程的详情
     */
    @PreAuthorize("@ss.hasPermi('system:video:query')")
    @PostMapping(value = "/detail")
    public AjaxResult getCourseVideoDetail(@RequestBody CourseVideoVO courseVideoVO) {
        CourseVideoDTO courseVideoDetail = courseVideoService.getCourseVideoDetail(courseVideoVO);
        return success(courseVideoDetail);
    }


    /**
     * 添加一个课程视频 lang
     */
    @PreAuthorize("@ss.hasPermi('official:video:add')")
    @Log(title = "添加一个课程", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult saveCourseVideoDetail(@RequestBody CourseVideoVO courseVideoVO) {
        courseVideoService.saveCourseVideoDetail(courseVideoVO);
        return AjaxResult.success();
    }

    /**
     * 修改课程视频
     */
    @ApiOperation(value = "修改课程")
    @PreAuthorize("@ss.hasPermi('official:course:update')")
    @Log(title = "修改课程", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult updateCourseVideoDetail(@RequestBody CourseVideoVO courseVideoVO){
        courseVideoService.updateCourseVideoDetail(courseVideoVO);
        return success();
    }

    /**
     * 删除问答
     */
    @PreAuthorize("@ss.hasPermi('official:course:remove')")
    @Log(title = "删除问答", businessType = BusinessType.DELETE)
    @DeleteMapping("/{videoIds}")
    public AjaxResult removeCourseVideoDetail(@PathVariable Integer[] videoIds) {
        courseVideoService.removeCourseVideoDetail(videoIds);
        return success();
    }


}
