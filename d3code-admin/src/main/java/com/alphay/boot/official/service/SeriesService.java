package com.alphay.boot.official.service;

import com.alphay.boot.official.dto.SeriesDTO;
import com.alphay.boot.official.vo.SeriesVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.alphay.boot.official.entity.Series;

import java.util.List;

public interface SeriesService extends IService<Series> {
    List<SeriesDTO> selectList(SeriesVO seriesVO);

    SeriesDTO selectById(SeriesVO seriesVO);

    void saveSeries(SeriesVO seriesVO);

    void updateSeries(SeriesVO seriesVO);


    String deleteSeriesByIds(Integer[] seriesIds);
}
