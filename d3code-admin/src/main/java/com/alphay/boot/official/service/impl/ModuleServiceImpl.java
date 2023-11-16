package com.alphay.boot.official.service.impl;

import com.alphay.boot.common.task.GlobalThreadPool;
import com.alphay.boot.official.dao.LanguageDao;
import com.alphay.boot.official.dao.ModuleItemDao;
import com.alphay.boot.official.dao.ModulesDao;
import com.alphay.boot.official.dao.ModulesI18nDao;
import com.alphay.boot.official.dto.*;
import com.alphay.boot.official.entity.Modules;
import com.alphay.boot.official.entity.ModulesI18n;
import com.alphay.boot.official.entity.ModulesItem;
import com.alphay.boot.official.service.LanguageService;
import com.alphay.boot.official.service.ModuleService;
import com.alphay.boot.official.service.ModulesI18nService;
import com.alphay.boot.official.utile.BeanCopyUtils;
import com.alphay.boot.official.utile.TranslationUtils;
import com.alphay.boot.official.vo.ModulesInfoVO;
import com.alphay.boot.official.vo.ModulesRequestVO;
import com.alphay.boot.official.vo.ModulesVO;
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
public class ModuleServiceImpl  extends  ServiceImpl<ModulesDao, Modules>  implements  ModuleService{

    @Autowired
    private ModuleItemDao moduleItemDao;

    @Autowired
    private ModulesDao modulesDao;

    @Autowired
    private ModulesI18nDao modulesI18nDao;

    @Autowired
    private ModulesI18nService modulesI18nService;

    @Autowired
    private ModuleItemServiceImpl moduleItemService;

    @Autowired
    private LanguageService languageService;

    @Autowired
    private LanguageDao languageDao;



    /**
     * 保存或者修改模块
     * @param modulesVo
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveOrUpdateModules(ModulesVO modulesVo) {
        if (modulesVo.getModulesId()==null){
            ArrayList<Modules> ModulesList = new ArrayList<>();
            ArrayList<ModulesItem> ModulesItemList = new ArrayList<>();
            Integer maxId = modulesDao.getMaxId();
            if (maxId==null){
                maxId=0;
            }
            //保存中文版modules
            String currentLang = modulesVo.getLang();
            Integer modulesId = maxId+1;
            Modules modules = BeanCopyUtils.copyObject(modulesVo, Modules.class);
            modules.setModulesId(modulesId);
            modules.setCreateTime(LocalDateTime.now());
            //保存中文版modulesItem
            ModulesList.add(modules);
            modulesId++;
            List<ModulesItemDTO> moduleItemDtoList = modulesVo.getModuleItemDtoList();
            Boolean temp = moduleItemDtoList!=null&&moduleItemDtoList.size()>0;
            if (temp){
                for (int j = 0; j < moduleItemDtoList.size(); j++) {
                    ModulesItem modulesItem = BeanCopyUtils.copyObject(moduleItemDtoList.get(j), ModulesItem.class);
                    modulesItem.setCreateTime(LocalDateTime.now());
                    modulesItem.setModulesId(modulesId);
                    ModulesItemList.add(modulesItem);
                }
            }
            //获取其他版本的modules
            List<LanguageDTO> languageDTOS = languageService.LanguageListExcludeCurrent(currentLang);
            int size = languageDTOS.size();
            if (size>0){
                GlobalThreadPool taskManager = GlobalThreadPool.getInstance();
                CountDownLatch latch = new CountDownLatch(size); // 初始值是线程数量减一
                for (int i = 0; i < size; i++) {
                    // 获取当前语言
                    String lang = languageDTOS.get(i).getLang();
                    Integer finalModulesId1 = modulesId;
                    taskManager.addTask(()->{
                        Modules modules1 = TranslationUtils.translationObject(modules, Modules.class, "auto", lang);
                        modules1.setModulesId(finalModulesId1);
                        modules1.setCreateTime(LocalDateTime.now());
                        // 保存其他语言的modules
                        ModulesList.add(modules1);
                        if (temp) {
                            List<ModulesItem> modulesItems = BeanCopyUtils.copyList(moduleItemDtoList, ModulesItem.class);
                            List<ModulesItem> modulesItems1 = TranslationUtils.copyList(modulesItems, ModulesItem.class, "auto", lang);
                            Integer finalModulesId = finalModulesId1;
                            modulesItems1.forEach(mi -> {
                                mi.setLang(lang);
                                mi.setModulesId(finalModulesId);
                                mi.setCreateTime(LocalDateTime.now());
                                ModulesItemList.add(mi);
                            });
                        }
                        latch.countDown(); // 确保无论如何都减少计数
                    });
                    modulesId++;
                }
                try {
                    latch.await(); // 等待所有任务完成
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            System.out.println("ModulesList:" + ModulesList);
            System.out.println("ModulesItemList" + ModulesItemList);
            this.saveOrUpdateBatch(ModulesList);
            moduleItemService.saveOrUpdateBatch(ModulesItemList);
        }else {
            Modules modules = BeanCopyUtils.copyObject(modulesVo, Modules.class);
            modules.setUpdateTime(LocalDateTime.now());
            ArrayList<ModulesItem> ModulesItemList = new ArrayList<>();
            List<ModulesItemDTO> moduleItemDtoList = modulesVo.getModuleItemDtoList();
            for (int i = 0; i < moduleItemDtoList.size(); i++) {
                ModulesItem modulesItem = BeanCopyUtils.copyObject(moduleItemDtoList.get(i), ModulesItem.class);
                ModulesItemList.add(modulesItem);
            }
            this.saveOrUpdate(modules);
            moduleItemService.saveOrUpdateBatch(ModulesItemList);
        }
    }

    @Override
    public List<ModulesDTO> getModuleDTOByCategory(String lang, Integer categoryId) {
        List<Modules> modulesList = modulesDao.getModulesList(lang, categoryId);
        List<ModulesDTO> modulesDTOS = BeanCopyUtils.copyList(modulesList, ModulesDTO.class);
        modulesDTOS.forEach(modulesDTO -> {
            //根据modulesId将itemList查询出来
            List<ModulesItem> modulesItems = moduleItemDao.selectList(new LambdaQueryWrapper<ModulesItem>().eq(ModulesItem::getModulesId, modulesDTO.getModulesId()));
            List<ModulesItemDTO> modulesItemDTOS = BeanCopyUtils.copyList(modulesItems, ModulesItemDTO.class);
            modulesDTO.setModulesItemDtoList(modulesItemDTOS);
        });
        return modulesDTOS;
    }

    @Override
    public List<ModulesDTO> selectModulesByLangAndId(String lang, Integer pageId) {
        List<ModulesDTO> modulesDTOS =  modulesDao.selectModulesByLangAndId(lang,pageId);
        modulesDTOS.forEach(modulesDTO -> {
            System.out.println("dawdawd"+modulesDTO);
        });
        return modulesDTOS;
    }

    @Override
    public ModulesInfoDTO getModulesInfo(ModulesInfoVO modulesInfoVO) {
        ModulesInfoDTO  modulesInfoDTO =modulesDao.getModulesByLang(modulesInfoVO);
        return modulesInfoDTO;
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveModules(ModulesInfoVO modulesInfoVO) {
        try{
            Integer maxId = modulesDao.getMaxId();
            if (maxId==null){
                maxId=0;
            }
            //保存中文版modules
            Integer modulesId = maxId+1;
            String currentLang = modulesInfoVO.getLang();
            Modules modules = BeanCopyUtils.copyObject(modulesInfoVO, Modules.class);
            modules.setModulesId(modulesId);
            modules.setCreateTime(LocalDateTime.now());

            ArrayList<ModulesI18n> modulesI18nArrayList = new ArrayList<>();
            ModulesI18n modulesI18n = BeanCopyUtils.copyObject(modulesInfoVO, ModulesI18n.class);
            modulesI18n.setModulesId(modulesId);
            modulesI18nArrayList.add(modulesI18n);
            List<LanguageDTO> languageDTOS = languageDao.LanguageListExcludeCurrent(currentLang);
            int size = languageDTOS.size();
            if (size>0){
                GlobalThreadPool taskManager = GlobalThreadPool.getInstance();
                CountDownLatch latch = new CountDownLatch(size); // 初始值是线程数量减一
                for (int i = 0; i < size; i++) {
                    String lang = languageDTOS.get(i).getLang();
                    taskManager.addTask(()->{
                        ModulesI18n modulesI18n1 = TranslationUtils.translationObject(modulesI18n, ModulesI18n.class, "auto", lang);
                        modulesI18n1.setLang(lang);
                        modulesI18n1.setI18nId(null);
                        modulesI18nArrayList.add(modulesI18n1);
                        System.out.println("语言数据"+modulesI18nArrayList);
                        latch.countDown(); // 确保无论如何都减少计数
                    });
                }
                try {
                    latch.await(); // 等待所有任务完成
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            this.saveOrUpdate(modules);
            modulesI18nService.saveOrUpdateBatch(modulesI18nArrayList);
        }catch (Exception e){
            log.error("新增失败"+e);
        }


    }


    @Override
    public void updateModules(ModulesVO modulesVo) {
        String lang = modulesVo.getLang();

        ModulesI18n modulesI18n = modulesI18nDao.selectOne(new LambdaQueryWrapper<ModulesI18n>()
                .eq(ModulesI18n::getModulesId, modulesVo.getModulesId())
                .eq(ModulesI18n::getLang, lang));
        modulesI18n.setTitle(modulesVo.getTitle());
        modulesI18n.setSubTitle(modulesVo.getSubTitle());


        Integer modulesId = modulesVo.getModulesId();
        Modules modules1 = modulesDao.selectById(modulesId);

        Modules modules = BeanCopyUtils.copyObject(modulesVo, Modules.class);
        modules.setUpdateTime(LocalDateTime.now());

        this.saveOrUpdate(modules);
        modulesI18nService.saveOrUpdate(modulesI18n);
    }

    @Override
    public String deleteModulesByIds(Integer[] modulesIds) {

        List<ModulesItem> modulesItems = moduleItemDao.selectList(new LambdaQueryWrapper<ModulesItem>().in(ModulesItem::getModulesId, modulesIds));
        if (modulesItems.size()>0){
            return "当前模块存在子模块，不允许删除";
        }else {
            modulesDao.deleteBatchIds(Arrays.asList(modulesIds));
            return null;
        }
    }

    @Override
    public List<ModulesExtDTO> selectModulesList(ModulesRequestVO modulesRequestVO) {

        List<ModulesExtDTO>  modulesExtDTOS=   modulesDao.selectModulesExtDTO(modulesRequestVO);


        return modulesExtDTOS;
    }

    @Override
    public ModulesExtDTO selectModulesExtDTO(ModulesRequestVO modulesRequestVO) {

        ModulesExtDTO modulesExtDTO= modulesDao.selectModulesExtDTOOne(modulesRequestVO);
        return modulesExtDTO;
    }

    /**
     * 根据页面
     */





}
