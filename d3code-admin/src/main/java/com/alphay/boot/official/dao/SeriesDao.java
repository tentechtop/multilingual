package com.alphay.boot.official.dao;

import com.alphay.boot.official.dto.SeriesDTO;
import com.alphay.boot.official.entity.Series;
import com.alphay.boot.official.vo.SeriesVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeriesDao extends BaseMapper<Series> {


    @Select("select  MAX(series_id) from off_series")
    Integer getMaxId();

    List<SeriesDTO> selectSeriesList(SeriesVO seriesVO);

    SeriesDTO selectSeriesById(SeriesVO seriesVO);
}
