package com.alphay.boot.official.service.impl;

import com.alphay.boot.common.task.GlobalThreadPool;
import com.alphay.boot.common.utils.SecurityUtils;
import com.alphay.boot.official.dao.NewsDao;
import com.alphay.boot.official.dto.LanguageDTO;
import com.alphay.boot.official.dto.NewsDTO;
import com.alphay.boot.official.entity.News;
import com.alphay.boot.official.service.LanguageService;
import com.alphay.boot.official.service.NewsService;
import com.alphay.boot.official.utile.BeanCopyUtils;
import com.alphay.boot.official.utile.TranslationUtils1;
import com.alphay.boot.official.vo.NewsVO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@Service
public class NewsServiceImpl extends ServiceImpl<NewsDao, News> implements NewsService {


    @Autowired
    private NewsDao newsDao;


    @Autowired
    private LanguageService languageService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveOrUpdateNews(NewsVO newsVO) {
        System.out.println("查询的新闻参数"+newsVO);
        if (newsVO.getNewsId()==null){
            String htmlText = newsVO.getHtmlText();
            if (newsVO.getText()==null && htmlText!=null){
                //解析html富文本 将内容填入到Text字段
                Document doc = Jsoup.parse(htmlText);
                // 提取所有的<p>标签和<a>标签内的文本内容
                Elements elements = doc.select("p, a");
                newsVO.setText(elements.text());
            }
            String currentLang = newsVO.getLang();
            System.out.println("正在执行");
            Integer maxId = newsDao.getMaxId();
            System.out.println("正在执行"+maxId);
            System.out.println("max"+maxId);
            if (maxId==null){
                maxId=0;
            }
            Integer newsId = maxId+1;
            //新增操作
            ArrayList<News> newsList = new ArrayList<>();
            News news = BeanCopyUtils.copyObject(newsVO, News.class);
            news.setNewsId(newsId);
            news.setCreateByUid(Math.toIntExact(SecurityUtils.getUserId()));
            news.setCreateTime(LocalDateTime.now());
            newsList.add(news);
            newsId++;
            //获取其他版本的news
            List<LanguageDTO> languageDTOS = languageService.LanguageListExcludeCurrent(currentLang);
            System.out.println("LanguageList是"+languageDTOS);
            int size = languageDTOS.size();
            if (size>0){
                GlobalThreadPool taskManager = GlobalThreadPool.getInstance();
                CountDownLatch latch = new CountDownLatch(size); // 初始值是线程数量减一
                for (int i = 0; i < size; i++) {
                    String lang = languageDTOS.get(i).getLang();
                    Integer finalNewsId = newsId;
                    taskManager.addTask(()->{
                        News news1 = TranslationUtils1.translationObject(news, News.class, "auto", lang);
                        news1.setNewsId(finalNewsId);
                        news1.setLang(lang);
                        newsList.add(news1);
                        latch.countDown(); // 确保无论如何都减少计数
                    });
                    newsId++;
                }
                try {
                    latch.await(); // 等待所有任务完成
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            this.saveBatch(newsList);
        }else {
            News news = BeanCopyUtils.copyObject(newsVO, News.class);
            news.setUpdateByUid(Math.toIntExact(SecurityUtils.getUserId()));
            news.setUpdateTime(LocalDateTime.now());
            this.saveOrUpdate(news);
        }
    }

    @Override
    public List<NewsDTO> selectNewsList(NewsVO newsVo) {
        List<NewsDTO> newsDTOList = newsDao.selectNewsList(newsVo);
        return newsDTOList;
    }

    @Override
    public NewsDTO selectNewsById(Integer newsId) {
        News news = newsDao.selectById(newsId);
        NewsDTO newsDTO = BeanCopyUtils.copyObject(news, NewsDTO.class);
        return newsDTO;
    }

    @Override
    public int deleteNewsByIds(Integer[] newsIds) {
        int i = newsDao.deleteBatchIds(Arrays.asList(newsIds));
        return i;
    }


}
