package com.alphay.boot.official.service;

import com.alphay.boot.official.dto.CourseVideoDTO;
import com.alphay.boot.official.entity.CourseVideo;
import com.alphay.boot.official.vo.CourseVideoVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface CourseVideoService extends IService<CourseVideo> {
    List<CourseVideoDTO> getCourseVideoList(CourseVideoVO courseVideoVO);

    CourseVideoDTO getCourseVideoDetail(CourseVideoVO courseVideoVO);

    void saveCourseVideoDetail(CourseVideoVO courseVideoVO);

    void updateCourseVideoDetail(CourseVideoVO courseVideoVO);

    void removeCourseVideoDetail(Integer[] videoIds);
}
