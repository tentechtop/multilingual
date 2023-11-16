package com.alphay.boot.official.service.impl;

import com.alphay.boot.common.task.GlobalThreadPool;
import com.alphay.boot.official.dao.*;
import com.alphay.boot.official.dto.CourseCategoryDTO;
import com.alphay.boot.official.dto.CourseDTO;
import com.alphay.boot.official.dto.CourseVideoDTO;
import com.alphay.boot.official.dto.LanguageDTO;
import com.alphay.boot.official.entity.*;
import com.alphay.boot.official.service.*;
import com.alphay.boot.official.utile.BeanCopyUtils;
import com.alphay.boot.official.utile.TranslationUtils;
import com.alphay.boot.official.vo.CourseVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@Service
public class CourseServiceImpl extends ServiceImpl<CourseDao, Course> implements CourseService {


    @Autowired
    private CourseDao courseDao;

    @Autowired
    private LanguageDao languageDao;

    @Autowired
    private CourseI18nDao courseI18nDao;

    @Autowired
    private CourseI18nService courseI18nService;

    @Autowired
    private CourseCategoryDao courseCategoryDao;

    @Autowired
    private CourseCategoryService courseCategoryService;

    @Autowired
    private CourseVideoDao courseVideoDao;

    @Autowired
    private CourseVideoService courseVideoService;





    @Override
    public List<CourseDTO> selectCourseList(CourseVO courseVO) {
        List<CourseDTO>  courseList=courseDao.selectCourseList(courseVO);
        return courseList;
    }


    @Override
    public CourseDTO selectCourseById(CourseVO courseVO) {
        CourseDTO courseDTO =   courseDao.selectCourseById(courseVO);
        return courseDTO;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveCourse(CourseVO courseVO) {
        Integer maxId = courseDao.getMaxId();
        if (maxId==null){
            maxId=0;
        }
        //保存中文版modules
        Integer courseId = maxId+1;
        String currentLang = courseVO.getLang();
        Course course = BeanCopyUtils.copyObject(courseVO, Course.class);
        course.setCourseId(courseId);
        course.setIsEnable(1);
        course.setIsDelete(0);
        course.setCreateTime(LocalDateTime.now());
        ArrayList<CourseI18n> courseI18nArrayList = new ArrayList<>();
        CourseI18n courseI18n = BeanCopyUtils.copyObject(courseVO, CourseI18n.class);
        courseI18n.setCourseId(courseId);
        courseI18nArrayList.add(courseI18n);
        System.out.println("中文版I18n"+courseI18n);
        List<LanguageDTO> languageDTOS = languageDao.LanguageListExcludeCurrent(currentLang);
        int size = languageDTOS.size();
        if (size>0){
            GlobalThreadPool taskManager = GlobalThreadPool.getInstance();
            CountDownLatch latch = new CountDownLatch(size); // 初始值是线程数量减一
            for (int i = 0; i < size; i++) {
                String lang = languageDTOS.get(i).getLang();
                taskManager.addTask(()->{
                    CourseI18n courseI18n1 = TranslationUtils.translationObject(courseI18n, CourseI18n.class, "auto", lang);
                    courseI18n1.setLang(lang);
                    System.out.println("翻译后的I18n"+courseI18n1);
                    courseI18nArrayList.add(courseI18n1);
                    latch.countDown(); // 确保无论如何都减少计数
                });
            }
            try {
                latch.await(); // 等待所有任务完成
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        this.saveOrUpdate(course);
        courseI18nService.saveOrUpdateBatch(courseI18nArrayList);

        //保存分类
        List<CourseCategoryDTO> categoryDTOList = courseVO.getCategoryDTOList();

        courseCategoryDao.delete(new LambdaQueryWrapper<CourseCategory>().eq(CourseCategory::getCourseId,courseId));
        if (categoryDTOList!=null && categoryDTOList.size()>0){
            ArrayList<CourseCategory> categoryArrayList = new ArrayList<>();
            categoryDTOList.forEach(courseCategoryDTO -> {
                CourseCategory courseCategory = new CourseCategory();
                courseCategory.setCourseId(courseId);
                courseCategory.setCategoryId(courseCategoryDTO.getCategoryId());
                categoryArrayList.add(courseCategory);
            });
            courseCategoryService.saveOrUpdateBatch(categoryArrayList);
        }

        //保存视频
        List<CourseVideoDTO> courseVideoList = courseVO.getCourseVideoList();
        courseVideoDao.delete(new LambdaQueryWrapper<CourseVideo>().eq(CourseVideo::getCourseId,courseId));
        if (courseVideoList!= null && courseVideoList.size()>0){
            ArrayList<CourseVideo> courseVideoArrayList = new ArrayList<>();
            courseVideoList.forEach(courseVideoDTO -> {
                CourseVideo courseVideo = BeanCopyUtils.copyObject(courseVideoDTO, CourseVideo.class);
                courseVideo.setCourseId(courseId);
                courseVideoArrayList.add(courseVideo);
            });
            courseVideoService.saveOrUpdateBatch(courseVideoArrayList);
        }



    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateCourse(CourseVO courseVO) {
        String lang = courseVO.getLang();
        Course course = BeanCopyUtils.copyObject(courseVO, Course.class);
        course.setUpdateTime(LocalDateTime.now());
        CourseI18n courseI18n = courseI18nDao.selectOne(new LambdaQueryWrapper<CourseI18n>()
                .eq(CourseI18n::getCourseId, courseVO.getCourseId())
                .eq(CourseI18n::getLang, courseVO.getLang()));
        courseI18n.setCourseName(courseVO.getCourseName());
        courseI18n.setCourseDesc(courseVO.getCourseDesc());
        courseI18n.setCourseCover(courseI18n.getCourseCover());
        this.saveOrUpdate(course);
        courseI18nService.saveOrUpdate(courseI18n);
        //保存分类
        Integer courseId = courseVO.getCourseId();

        List<CourseCategoryDTO> categoryDTOList = courseVO.getCategoryDTOList();
        courseCategoryDao.delete(new LambdaQueryWrapper<CourseCategory>().eq(CourseCategory::getCourseId,courseId));
        if (categoryDTOList.size()>0){
            ArrayList<CourseCategory> categoryArrayList = new ArrayList<>();
            categoryDTOList.forEach(courseCategoryDTO -> {
                CourseCategory courseCategory = new CourseCategory();
                courseCategory.setCourseId(courseId);
                courseCategory.setCategoryId(courseCategoryDTO.getCategoryId());
                categoryArrayList.add(courseCategory);
            });
            courseCategoryService.saveOrUpdateBatch(categoryArrayList);
        }

        //保存视频
        List<CourseVideoDTO> courseVideoList = courseVO.getCourseVideoList();
        courseVideoDao.delete(new LambdaQueryWrapper<CourseVideo>().eq(CourseVideo::getCourseId,courseId));
        if (courseVideoList.size()>0){
            ArrayList<CourseVideo> courseVideoArrayList = new ArrayList<>();
            courseVideoList.forEach(courseVideoDTO -> {
                CourseVideo courseVideo = BeanCopyUtils.copyObject(courseVideoDTO, CourseVideo.class);
                courseVideo.setCourseId(courseId);
                courseVideoArrayList.add(courseVideo);
            });
            courseVideoService.saveOrUpdateBatch(courseVideoArrayList);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteCourseByIds(Integer[] courseIds) {
        //删除本体  删除I18n  删除分类中间表  删除课程下的视频
        try{
            List<Integer> list = Arrays.asList(courseIds);
            //删除本体
            courseDao.deleteBatchIds(list);
            //删除分类关系表
            courseCategoryDao.delete(new LambdaQueryWrapper<CourseCategory>().in(CourseCategory::getCourseId, list));
            //删除I18n
            courseI18nDao.delete(new LambdaQueryWrapper<CourseI18n>().in(CourseI18n::getCourseId,list));
            courseVideoDao.delete(new LambdaQueryWrapper<CourseVideo>().in(CourseVideo::getCourseId));
        }catch (Exception e){
            log.error("删除失败"+e);
        }
    }


}



































