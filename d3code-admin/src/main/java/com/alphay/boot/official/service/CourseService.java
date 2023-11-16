package com.alphay.boot.official.service;

import com.alphay.boot.official.dto.CourseDTO;
import com.alphay.boot.official.vo.CourseVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.alphay.boot.official.entity.Course;

import java.util.List;

public interface CourseService extends IService<Course> {
    List<CourseDTO> selectCourseList(CourseVO courseVO);

    CourseDTO selectCourseById(CourseVO courseVO);

    void saveCourse(CourseVO courseVO);

    void updateCourse(CourseVO courseVO);

    void deleteCourseByIds(Integer[] courseIds);
}
