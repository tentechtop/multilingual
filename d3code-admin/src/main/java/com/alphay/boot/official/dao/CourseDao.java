package com.alphay.boot.official.dao;

import com.alphay.boot.official.dto.CourseDTO;
import com.alphay.boot.official.vo.CourseVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.alphay.boot.official.entity.Course;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseDao extends BaseMapper<Course> {
    List<CourseDTO> selectCourseList(CourseVO courseVO);

    CourseDTO selectCourseById(CourseVO courseVO);

    @Select("select max(course_id) from off_course")
    Integer getMaxId();
}
