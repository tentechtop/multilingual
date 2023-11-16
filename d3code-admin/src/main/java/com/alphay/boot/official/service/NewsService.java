package com.alphay.boot.official.service;

import com.alphay.boot.official.dto.NewsDTO;
import com.alphay.boot.official.entity.News;
import com.alphay.boot.official.vo.NewsVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface NewsService extends IService<News> {
    void saveOrUpdateNews(NewsVO newsVO);

    List<NewsDTO> selectNewsList(NewsVO newsVo);

    NewsDTO selectNewsById(Integer newsId);

    int deleteNewsByIds(Integer[] newsIds);
}
