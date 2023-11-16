package com.alphay.boot.official.service.impl;

import com.alphay.boot.common.task.GlobalThreadPool;
import com.alphay.boot.official.dao.SeoDao;
import com.alphay.boot.official.dto.LanguageDTO;
import com.alphay.boot.official.entity.Seo;
import com.alphay.boot.official.service.LanguageService;
import com.alphay.boot.official.service.SeoService;
import com.alphay.boot.official.utile.TranslationUtils1;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@Service
public class SeoServiceImpl extends ServiceImpl<SeoDao, Seo> implements SeoService {

    @Autowired
    private SeoDao seoDao;

    @Autowired
    private LanguageService languageService;


    /**
     * 根据pageid和lang查询list
     * @param seo
     * @return
     */
    @Override
    public List<Seo> selectNewsList(Seo seo) {
        List<Seo> seoList = seoDao.selectNewsList(seo);
        return seoList;
    }


    @Override
    public Seo selectSeoById(Integer seoId) {
        Seo seo = seoDao.selectById(seoId);
        return seo;
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveOrUpdateSeo(Seo seo) {
        String currentLang = seo.getLang();
        System.out.println("当前语言"+currentLang);
        Integer pageId = seo.getPageId();
        ArrayList<Seo> seoArrayList = new ArrayList<>();
        seoArrayList.add(seo);//当前版本
        List<LanguageDTO> languageDTOS = languageService.LanguageListExcludeCurrent(currentLang);
        int size = languageDTOS.size();
        if (size>0){
            System.out.println("其他语言数量"+size);
            GlobalThreadPool taskManager = GlobalThreadPool.getInstance();
            CountDownLatch latch = new CountDownLatch(size); // 初始值是线程数量减一
            for (int i = 0; i < size; i++) {
                String lang = languageDTOS.get(i).getLang();
                System.out.println("当前语言"+lang);
                taskManager.addTask(()->{
                    Seo seo1 = TranslationUtils1.translationObject(seo, Seo.class, "auto", lang);
                    seo1.setLang(lang);
                    seoArrayList.add(seo1);
                    latch.countDown(); // 确保无论如何都减少计数
                });
            }
            try {
                latch.await(); // 等待所有任务完成
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        this.saveBatch(seoArrayList);
    }

    @Override
    public void updateSeo(Seo seo) {
        this.saveOrUpdate(seo);
    }

    @Override
    public void deleteSeoByIds(Integer[] seoIds) {
        int i = seoDao.deleteBatchIds(Arrays.asList(seoIds));
    }


}
