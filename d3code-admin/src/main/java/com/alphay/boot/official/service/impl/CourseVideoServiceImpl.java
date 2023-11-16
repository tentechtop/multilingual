package com.alphay.boot.official.service.impl;

import com.alphay.boot.common.task.GlobalThreadPool;
import com.alphay.boot.official.dao.CourseVideoDao;
import com.alphay.boot.official.dao.LanguageDao;
import com.alphay.boot.official.dto.CourseVideoDTO;
import com.alphay.boot.official.dto.LanguageDTO;
import com.alphay.boot.official.entity.CourseVideo;
import com.alphay.boot.official.service.CourseVideoService;
import com.alphay.boot.official.utile.BeanCopyUtils;
import com.alphay.boot.official.utile.TranslationUtils;
import com.alphay.boot.official.vo.CourseVideoVO;
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
public class CourseVideoServiceImpl extends ServiceImpl<CourseVideoDao, CourseVideo> implements CourseVideoService {


   @Autowired
   private CourseVideoDao courseVideoDao;

    @Autowired
    private LanguageDao languageDao;

    @Override
    public List<CourseVideoDTO> getCourseVideoList(CourseVideoVO courseVideoVO) {
        List<CourseVideoDTO> courseVideoDTOList = courseVideoDao.getCourseVideoList(courseVideoVO);
        return courseVideoDTOList;
    }

    @Override
    public CourseVideoDTO getCourseVideoDetail(CourseVideoVO courseVideoVO) {
        CourseVideoDTO courseVideoDTO = courseVideoDao.getCourseVideoDetail(courseVideoVO);
        return courseVideoDTO;
    }



    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveCourseVideoDetail(CourseVideoVO courseVideoVO) {
        Integer maxId  = courseVideoDao.getMaxId();
        if (maxId==null){
            maxId=0;
        }
        //保存中文版modules
        Integer videoId = maxId+1;
        String currentLang = courseVideoVO.getLang();
        CourseVideo courseVideo = BeanCopyUtils.copyObject(courseVideoVO, CourseVideo.class);
        courseVideo.setIsEnable(1);
        courseVideo.setIsDelete(0);
        courseVideo.setCreateTime(LocalDateTime.now());
        ArrayList<CourseVideo> courseVideoArrayList = new ArrayList<>();
        courseVideoArrayList.add(courseVideo);

        List<LanguageDTO> languageDTOS = languageDao.LanguageListExcludeCurrent(currentLang);
        int size = languageDTOS.size();
        if (size>0){
            GlobalThreadPool taskManager = GlobalThreadPool.getInstance();
            CountDownLatch latch = new CountDownLatch(size); // 初始值是线程数量减一
            for (int i = 0; i < size; i++) {
                String lang = languageDTOS.get(i).getLang();
                taskManager.addTask(()->{
                    CourseVideo courseVideo1 = TranslationUtils.
                            translationObject(courseVideo, CourseVideo.class, currentLang, lang);
                    courseVideo1.setLang(lang);
                    System.out.println("翻译后的I18n"+courseVideo1);
                    courseVideoArrayList.add(courseVideo1);
                    latch.countDown(); // 确保无论如何都减少计数
                });
            }
            try {
                latch.await(); // 等待所有任务完成
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        this.saveOrUpdateBatch(courseVideoArrayList);
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateCourseVideoDetail(CourseVideoVO courseVideoVO) {
        CourseVideo courseVideo = BeanCopyUtils.copyObject(courseVideoVO, CourseVideo.class);
        courseVideo.setUpdateTime(LocalDateTime.now());
        this.saveOrUpdate(courseVideo);
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void removeCourseVideoDetail(Integer[] videoIds) {
        List<Integer> list = Arrays.asList(videoIds);
        courseVideoDao.deleteBatchIds(list);
    }
}
