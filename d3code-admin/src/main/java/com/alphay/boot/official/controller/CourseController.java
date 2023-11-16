package com.alphay.boot.official.controller;


import com.alphay.boot.common.annotation.Log;
import com.alphay.boot.common.core.controller.BaseController;
import com.alphay.boot.common.core.domain.AjaxResult;
import com.alphay.boot.common.core.page.TableDataInfo;
import com.alphay.boot.common.enums.BusinessType;
import com.alphay.boot.official.dto.CourseDTO;
import com.alphay.boot.official.service.CourseService;
import com.alphay.boot.official.vo.CourseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "课程模块")
@RestController
@CrossOrigin
@RequestMapping("/official/course")
public class CourseController extends BaseController {

    @Autowired
    private CourseService courseService;

    /**
     * @param courseVO
     * @return
     */
    @PreAuthorize("@ss.hasPermi('system:course:list')")
    @PostMapping("/list")
    public TableDataInfo list(@RequestBody  CourseVO courseVO) {
        startPage();
        List<CourseDTO> list = courseService.selectCourseList(courseVO);
        return getDataTable(list);
    }

    /**
     * 根据Id和lang获取当前课程的详情
     */
    @PreAuthorize("@ss.hasPermi('system:course:query')")
    @PostMapping(value = "/detail")
    public AjaxResult getInfo(@RequestBody CourseVO courseVO) {
        CourseDTO courseDTO =  courseService.selectCourseById(courseVO);
        return success(courseDTO);
    }

    /**
     * 添加一个课程 lang
     */
    @PreAuthorize("@ss.hasPermi('official:course:add')")
    @Log(title = "添加一个课程", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult saveCategory(@Validated @RequestBody CourseVO courseVO) {
        courseService.saveCourse(courseVO);
        return AjaxResult.success();
    }

    /**
     * 修改课程
     */
    @ApiOperation(value = "修改课程")
    @PreAuthorize("@ss.hasPermi('official:course:update')")
    @Log(title = "修改课程", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult updateCategory(@RequestBody CourseVO courseVO){
        courseService.updateCourse(courseVO);
        return success();
    }

    /**
     * 删除课程
     */
    @PreAuthorize("@ss.hasPermi('official:course:remove')")
    @Log(title = "删除课程", businessType = BusinessType.DELETE)
    @DeleteMapping("/{courseIds}")
    public AjaxResult remove(@PathVariable Integer[] courseIds) {
        courseService.deleteCourseByIds(courseIds);
        return null;
    }
}
