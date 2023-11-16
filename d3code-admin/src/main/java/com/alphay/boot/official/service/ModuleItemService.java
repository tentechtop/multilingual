package com.alphay.boot.official.service;

import com.alphay.boot.official.dto.ModulesItemDTO;
import com.alphay.boot.official.vo.ModulesRequestVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.alphay.boot.official.entity.ModulesItem;

import java.util.List;

public interface ModuleItemService extends IService<ModulesItem> {

    List<ModulesItemDTO> selectModulesItemList(ModulesRequestVO modulesRequestVO);

    ModulesItemDTO selectModulesItemById(Integer itemId);

    void saveModulesItem(ModulesItemDTO modulesItemDTO);

    void updateModulesItem(ModulesItemDTO modulesItemDTO);

    void deleteItemByIds(Integer[] itemIds);
}
