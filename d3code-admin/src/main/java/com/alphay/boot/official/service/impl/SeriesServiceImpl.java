package com.alphay.boot.official.service.impl;

import com.alphay.boot.common.task.GlobalThreadPool;
import com.alphay.boot.common.utils.SecurityUtils;
import com.alphay.boot.official.dao.LanguageDao;
import com.alphay.boot.official.dao.SeriesDao;
import com.alphay.boot.official.dao.SeriesI18nDao;
import com.alphay.boot.official.dto.LanguageDTO;
import com.alphay.boot.official.dto.SeriesDTO;
import com.alphay.boot.official.entity.Series;
import com.alphay.boot.official.entity.SeriesI18n;
import com.alphay.boot.official.service.SeriesI18nService;
import com.alphay.boot.official.service.SeriesService;
import com.alphay.boot.official.utile.BeanCopyUtils;
import com.alphay.boot.official.utile.TranslationUtils;
import com.alphay.boot.official.vo.SeriesVO;
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
public class SeriesServiceImpl extends ServiceImpl<SeriesDao, Series> implements SeriesService {

    @Autowired
    private SeriesDao seriesDao;

    @Autowired
    private SeriesI18nDao seriesI18nDao;

    @Autowired
    private SeriesI18nService seriesI18nService;

    @Autowired
    private LanguageDao languageDao;

    @Override
    public List<SeriesDTO> selectList(SeriesVO seriesVO) {
        List<SeriesDTO> seriesDTOList = seriesDao.selectSeriesList(seriesVO);
        return seriesDTOList;
    }

    @Override
    public SeriesDTO selectById(SeriesVO seriesVO) {
        System.out.println("系列查询的数据是"+seriesVO);
        SeriesDTO seriesDTO =   seriesDao.selectSeriesById(seriesVO);
        return seriesDTO;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveSeries(SeriesVO seriesVO) {
        System.out.println("当前添加的系列"+seriesVO);
        String currentLang = seriesVO.getLang();
        Integer maxId = seriesDao.getMaxId();
        if (maxId==null){
            maxId=0;
        }
        Integer seriesId = maxId+1;
        Series series = BeanCopyUtils.copyObject(seriesVO, Series.class);
        series.setSeriesId(seriesId);
        series.setCreateByUid(Math.toIntExact(SecurityUtils.getUserId()));
        series.setCreateTime(LocalDateTime.now());

        SeriesI18n seriesI18n = BeanCopyUtils.copyObject(seriesVO, SeriesI18n.class);
        seriesI18n.setSeriesId(seriesId);
        ArrayList<SeriesI18n> seriesI18ns = new ArrayList<>();
        seriesI18ns.add(seriesI18n);

        List<LanguageDTO> languageDTOS = languageDao.LanguageListExcludeCurrent(currentLang);
        int size = languageDTOS.size();
        if (size>0){
            GlobalThreadPool taskManager = GlobalThreadPool.getInstance();
            CountDownLatch latch = new CountDownLatch(size); // 初始值是线程数量减一
            for (int i = 0; i < size; i++) {
                String lang = languageDTOS.get(i).getLang();
                taskManager.addTask(()->{
                    SeriesI18n seriesI18n1 = TranslationUtils.translationObject(seriesI18n, SeriesI18n.class, "auto", lang);
                    seriesI18n1.setLang(lang);
                    seriesI18ns.add(seriesI18n1);
                    latch.countDown(); // 确保无论如何都减少计数
                });
            }
            try {
                latch.await(); // 等待所有任务完成
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        this.saveOrUpdate(series);
        seriesI18nService.saveOrUpdateBatch(seriesI18ns);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateSeries(SeriesVO seriesVO) {
        //同步更新 langI18n
        String currentLang  = seriesVO.getLang();
        Integer seriesId = seriesVO.getSeriesId();
        Series series = BeanCopyUtils.copyObject(seriesVO, Series.class);
        series.setUpdateByUid(Math.toIntExact(SecurityUtils.getUserId()));
        series.setUpdateTime(LocalDateTime.now());
        SeriesI18n seriesI18n = seriesI18nDao.selectOne(new LambdaQueryWrapper<SeriesI18n>()
                .eq(SeriesI18n::getSeriesId, seriesId)
                .eq(SeriesI18n::getLang, currentLang));
        seriesI18n.setSeriesName(seriesVO.getSeriesName());
        seriesI18n.setSeriesDesc(seriesVO.getSeriesDesc());
        seriesI18n.setImgUrl(seriesVO.getImgUrl());
        this.saveOrUpdate(series);
        seriesI18nService.saveOrUpdate(seriesI18n);

    }

    @Override
    public String deleteSeriesByIds(Integer[] seriesIds) {
        //删除本体 和I18n
        try{
            List<Integer> list = Arrays.asList(seriesIds);
            seriesDao.deleteBatchIds(list);
            seriesI18nDao.delete(new LambdaQueryWrapper<SeriesI18n>().in(SeriesI18n::getSeriesId,list));
            return null;
        }catch (Exception e){
            return "修改失败,请检查数据一致性";
        }
    }
}
