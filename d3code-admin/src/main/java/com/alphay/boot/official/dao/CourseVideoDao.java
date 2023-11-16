package com.alphay.boot.official.dao;

import com.alphay.boot.official.dto.CourseVideoDTO;
import com.alphay.boot.official.vo.CourseVideoVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.alphay.boot.official.entity.CourseVideo;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseVideoDao extends BaseMapper<CourseVideo> {
    List<CourseVideoDTO> getCourseVideoList(CourseVideoVO courseVideoVO);

    CourseVideoDTO getCourseVideoDetail(CourseVideoVO courseVideoVO);

    @Select("select MAX(video_id) from off_course_video")
    Integer getMaxId();
}
