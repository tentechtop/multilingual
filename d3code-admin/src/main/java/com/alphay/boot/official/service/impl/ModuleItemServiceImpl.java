package com.alphay.boot.official.service.impl;

import com.alphay.boot.common.task.GlobalThreadPool;
import com.alphay.boot.official.dao.LanguageDao;
import com.alphay.boot.official.dto.LanguageDTO;
import com.alphay.boot.official.dto.ModulesItemDTO;
import com.alphay.boot.official.utile.BeanCopyUtils;
import com.alphay.boot.official.utile.TranslationUtils;
import com.alphay.boot.official.vo.ModulesRequestVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.alphay.boot.official.dao.ModuleItemDao;
import com.alphay.boot.official.entity.ModulesItem;
import com.alphay.boot.official.service.ModuleItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@Service
public class ModuleItemServiceImpl extends ServiceImpl<ModuleItemDao, ModulesItem> implements ModuleItemService {


    @Autowired
    private ModuleItemDao modulesItemDao;

    @Autowired
    private LanguageDao languageDao;

    @Override
    public List<ModulesItemDTO> selectModulesItemList(ModulesRequestVO modulesRequestVO) {

        LambdaQueryWrapper<ModulesItem> myEq = new LambdaQueryWrapper<ModulesItem>()
                .eq(ModulesItem::getModulesId, modulesRequestVO.getModulesId());
        if (modulesRequestVO.getLang()!=null){
            myEq.eq(ModulesItem::getLang, modulesRequestVO.getLang());
        }
        List<ModulesItem> modulesItems = modulesItemDao.selectList(myEq);
        List<ModulesItemDTO> modulesItemDTOS = BeanCopyUtils.copyList(modulesItems, ModulesItemDTO.class);
        return modulesItemDTOS;
    }

    @Override
    public ModulesItemDTO selectModulesItemById(Integer itemId) {
        ModulesItem modulesItem = modulesItemDao.selectById(itemId);
        ModulesItemDTO modulesItemDTO = BeanCopyUtils.copyObject(modulesItem, ModulesItemDTO.class);
        return modulesItemDTO;
    }

    @Override
    public void saveModulesItem(ModulesItemDTO modulesItemDTO) {
        String currentLang = modulesItemDTO.getLang();
        ModulesItem modulesItem = BeanCopyUtils.copyObject(modulesItemDTO, ModulesItem.class);
        ArrayList<ModulesItem> modulesItemArrayList = new ArrayList<>();
        modulesItemArrayList.add(modulesItem);
        List<LanguageDTO> languageDTOS = languageDao.LanguageListExcludeCurrent(currentLang);
        int size = languageDTOS.size();
        if (size>0){
            GlobalThreadPool taskManager = GlobalThreadPool.getInstance();
            CountDownLatch latch = new CountDownLatch(size); // 初始值是线程数量减一
            for (int i = 0; i < size; i++) {
                String lang = languageDTOS.get(i).getLang();
                taskManager.addTask(()->{
                    ModulesItem modulesItem1 = TranslationUtils.translationObject(modulesItem, ModulesItem.class, "auto", lang);
                    modulesItem1.setLang(lang);
                    modulesItem1.setItemId(null);
                    modulesItemArrayList.add(modulesItem1);
                    latch.countDown(); // 确保无论如何都减少计数
                });
            }
            try {
                latch.await(); // 等待所有任务完成
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        this.saveOrUpdateBatch(modulesItemArrayList);
    }

    @Override
    public void updateModulesItem(ModulesItemDTO modulesItemDTO) {
        ModulesItem modulesItem = BeanCopyUtils.copyObject(modulesItemDTO, ModulesItem.class);
        this.saveOrUpdate(modulesItem);
    }

    @Override
    public void deleteItemByIds(Integer[] itemIds) {
        System.out.println("删除的数据"+itemIds);
        List<Integer> list = Arrays.asList(itemIds);
        List<ModulesItem> modulesItems = modulesItemDao.selectBatchIds(list);
        Integer modulesId;
        int size = modulesItems.size();
        if (size>0){
            modulesId=modulesItems.get(0).getModulesId();
            System.out.println("模块的Id是"+modulesId);
            ArrayList<Integer> sortList = new ArrayList<>();
            ArrayList<Integer> idList = new ArrayList<>();
            for (int i = 0; i < modulesItems.size(); i++) {
                ModulesItem modulesItem = modulesItems.get(i);
                Integer sort = modulesItem.getSort();
                sortList.add(sort);
            }
            System.out.println("删除的位置是"+sortList);
            List<ModulesItem> modulesItems1 = modulesItemDao.selectList(new LambdaQueryWrapper<ModulesItem>()
                    .eq(ModulesItem::getModulesId, modulesId)
                    .in(ModulesItem::getSort, sortList));
            modulesItems1.forEach(modulesItem1 -> {
                idList.add(modulesItem1.getItemId());
                System.out.println("要删除的数据是"+idList);
            });
            modulesItemDao.deleteBatchIds(idList);
        }





    }
}
