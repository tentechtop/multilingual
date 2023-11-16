package com.alphay.boot.official.service.impl;


import com.alphay.boot.common.task.GlobalThreadPool;
import com.alphay.boot.common.utils.SecurityUtils;
import com.alphay.boot.official.dao.LanguageDao;
import com.alphay.boot.official.dao.ModulesDao;
import com.alphay.boot.official.dao.PageDao;
import com.alphay.boot.official.dto.LanguageDTO;
import com.alphay.boot.official.dto.ModulesDTO;
import com.alphay.boot.official.dto.ModulesItemDTO;
import com.alphay.boot.official.dto.page.PageDTO;
import com.alphay.boot.official.entity.*;
import com.alphay.boot.official.service.*;
import com.alphay.boot.official.utile.BeanCopyUtils;
import com.alphay.boot.official.utile.TranslationUtils1;
import com.alphay.boot.official.vo.page.PageContentVo;
import com.alphay.boot.official.vo.page.PageDetailVO;
import com.alphay.boot.official.vo.page.PageVO;
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
public class PageServiceImpl extends ServiceImpl<PageDao,Page> implements PageService {

    @Autowired
    private PageDao pageDao;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private LanguageDao languageDao;

    @Autowired
    private ModulesI18nService modulesI18nService;

    @Autowired
    private ModulesDao modulesDao;

    @Autowired
    private ModuleService moduleService;

    @Autowired
    private ModuleItemService moduleItemService;

    @Autowired
    private LanguageService languageService;

    @Override
    public List<Page> getPageList(PageVO pageVO) {
        List<Page> pageDTOList = pageDao.selectPageList(pageVO);
        return pageDTOList;
    }

    @Override
    public PageDTO getPageContent(PageVO pageVo) {
        PageDTO pageDTO = new PageDTO();
        pageDTO.setPageName(pageVo.getPageName());
        List<ModulesDTO> moduleDTOByCategory = moduleService.getModuleDTOByCategory(pageVo.getLang(), pageVo.getPageId());
        pageDTO.setModuleDtoList(moduleDTOByCategory);
        pageDTO.setPageId(pageVo.getPageId());
        return pageDTO;
    }



    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveOrUpdatePage(PageContentVo pageContentVo) {
        try{
            Category category = new Category();
            category.setCategoryId(pageContentVo.getPageId());
            category.setCategoryName(pageContentVo.getPageName());
            if (pageContentVo.getPageId()==null){
                category.setCreateByUid(Math.toIntExact(SecurityUtils.getUserId()));
                category.setCreateTime(LocalDateTime.now());
            }else {
                category.setUpdateByUid(Math.toIntExact(SecurityUtils.getUserId()));
                category.setUpdateTime(LocalDateTime.now());
            }
            categoryService.saveOrUpdate(category);
            List<ModulesDTO> moduleDtoList = pageContentVo.getModuleDtoList();
            List<Modules> modules = BeanCopyUtils.copyList(moduleDtoList, Modules.class);
            modules.forEach(modules1 -> {
               if (modules1.getModulesId()==null){
                   modules1.setCreateTime(LocalDateTime.now());
               }else {
                   modules1.setUpdateTime(LocalDateTime.now());
               }
            });
            moduleService.saveOrUpdateBatch(modules);
            moduleDtoList.forEach(modulesDTO -> {
                List<ModulesItemDTO> moduleItemDtoList = modulesDTO.getModulesItemDtoList();
                List<ModulesItem> modulesItems = BeanCopyUtils.copyList(moduleItemDtoList, ModulesItem.class);
                modulesItems.forEach(modulesItem -> {
                    if (modulesItem.getModulesId()==null){
                        modulesItem.setCreateTime(LocalDateTime.now());
                    }else {
                        modulesItem.setUpdateTime(LocalDateTime.now());
                    }
                });
                moduleItemService.saveOrUpdateBatch(modulesItems);
            });
            //更新redis缓存中的页面数据
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public PageDTO getPageAllinfo(PageVO pageVO) {
        Integer pageId = pageVO.getPageId();
        Page page = pageDao.selectById(pageId);
        String lang = pageVO.getLang();
        PageDTO pageDTO = BeanCopyUtils.copyObject(page, PageDTO.class);
        List<ModulesDTO> modulesDTOS = moduleService.selectModulesByLangAndId(lang,pageId);
        pageDTO.setLang(lang);
        pageDTO.setModuleDtoList(modulesDTOS);
        return pageDTO;
    }

    @Override
    public int add(PageVO pageVO) {
        Page page = BeanCopyUtils.copyObject(pageVO, Page.class);
        page.setIsDelete(false);
        page.setCreateTime(LocalDateTime.now());
        //插入一条记录
        int insert = pageDao.insert(page);
        return insert;
    }

    @Override
    public int edit(PageVO pageVO) {
        Page page = BeanCopyUtils.copyObject(pageVO, Page.class);
        this.saveOrUpdate(page);
        return 1;
    }

    @Override
    public String deletePageByIds(Integer[] pageIds) {
        //查询当前页面有无子模块

        List<Modules> modules = modulesDao.selectList(new LambdaQueryWrapper<Modules>().in(Modules::getPageId, pageIds));
        if (modules.size()>0){
            return "当前页面存在模块元素，不允许删除";
        }else {
            boolean b = this.removeBatchByIds(Arrays.asList(pageIds));
            return null;
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void savePageAllInfo(PageDetailVO pageDetailVO) {
        String currentLang = pageDetailVO.getLang();
        Integer maxId = pageDao.getMaxId();
        if (maxId==null){
            maxId=0;
        }
        Integer pageId = maxId+1;
        Page page = BeanCopyUtils.copyObject(pageDetailVO, Page.class);
        page.setPageId(pageId);
        page.setCreateTime(LocalDateTime.now());

        List<ModulesDTO> moduleDtoList = pageDetailVO.getModulesDtoList();
        ArrayList<Modules> modulesArrayList = new ArrayList<>();
        Integer modulesMaxId = modulesDao.getMaxId();
        if (modulesMaxId==null){
            modulesMaxId=0;
        }
        Integer modulesId = modulesMaxId+1;
        ArrayList<ModulesItem> modulesItemArrayList = new ArrayList<>();
        ArrayList<ModulesI18n> modulesI18nArrayList = new ArrayList<>();
        for (int i = 0; i < moduleDtoList.size(); i++) {
            ModulesDTO modulesDTO = moduleDtoList.get(i);
            Modules modules = BeanCopyUtils.copyObject(modulesDTO, Modules.class);
            modules.setModulesId(modulesId);
            modules.setPageId(pageId);
            modules.setCreateTime(LocalDateTime.now());
            modulesArrayList.add(modules);

            ModulesI18n modulesI18n = BeanCopyUtils.copyObject(modules, ModulesI18n.class);
            modulesI18n.setModulesId(modulesId);
            modulesI18n.setLang(currentLang);
            modulesI18nArrayList.add(modulesI18n);

            List<ModulesItemDTO> moduleItemDtoList = modulesDTO.getModulesItemDtoList();
            Integer finalModulesId = modulesId;
            moduleItemDtoList.forEach(modulesItemDTO -> {
                ModulesItem modulesItem = BeanCopyUtils.copyObject(modulesItemDTO, ModulesItem.class);
                modulesItem.setModulesId(finalModulesId);
                modulesItem.setCreateTime(LocalDateTime.now());
                modulesItem.setLang(currentLang);
                modulesItemArrayList.add(modulesItem);
            });
            modulesId++;
        }
         //保存其他版本
        List<LanguageDTO> languageDTOS = languageDao.LanguageListExcludeCurrent(currentLang);
        int size = languageDTOS.size();
        ArrayList<ModulesI18n> modulesI18nArrayList1 = new ArrayList<>();
        ArrayList<ModulesItem> modulesItemArrayList2 = new ArrayList<>();
        if (size-1>0){
            GlobalThreadPool taskManager = GlobalThreadPool.getInstance();
            CountDownLatch latch = new CountDownLatch(size); // 初始值是线程数量减一
            for (int i = 0; i < size; i++) {
                // 获取当前语言
                String lang = languageDTOS.get(i).getLang();
                taskManager.addTask(()->{
                    List<ModulesI18n> modulesI18ns = TranslationUtils1.copyList(modulesI18nArrayList, ModulesI18n.class,"auto",lang);
                    modulesI18ns.forEach(modulesI18n -> {
                        modulesI18n.setLang(lang);
                        modulesI18nArrayList1.add(modulesI18n);
                    });
                    List<ModulesItem> auto = TranslationUtils1.copyList(modulesItemArrayList, ModulesItem.class, "auto", lang);
                    auto.forEach(modulesItem -> {
                        modulesItem.setLang(lang);
                        modulesItemArrayList2.add(modulesItem);
                    });
                    latch.countDown(); // 确保无论如何都减少计数
                });
            }
            modulesI18nArrayList.forEach(modulesI18n -> {
                modulesI18nArrayList1.add(modulesI18n);
            });
            modulesItemArrayList.forEach(modulesItem -> {
                modulesItemArrayList2.add(modulesItem);
            });
            try {
                latch.await(); // 等待所有任务完成
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        this.saveOrUpdate(page);
        moduleService.saveOrUpdateBatch(modulesArrayList);
        moduleItemService.saveOrUpdateBatch(modulesItemArrayList2);
        modulesI18nService.saveOrUpdateBatch(modulesI18nArrayList1);
    }

    @Override
    public Page getPageInfo(Integer pageId) {
        Page page = pageDao.selectById(pageId);
        return page;
    }
}
