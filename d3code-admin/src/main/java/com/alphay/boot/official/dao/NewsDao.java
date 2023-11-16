package com.alphay.boot.official.dao;

import com.alphay.boot.official.dto.NewsDTO;
import com.alphay.boot.official.entity.News;
import com.alphay.boot.official.vo.NewsVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsDao extends BaseMapper<News> {


    @Select("SELECT MAX(news_id) from off_news")
    Integer getMaxId();

    List<NewsDTO> selectNewsList(NewsVO newsVo);
}
