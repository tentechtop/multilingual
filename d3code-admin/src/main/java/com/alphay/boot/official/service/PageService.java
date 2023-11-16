package com.alphay.boot.official.service;

import com.alphay.boot.official.dto.page.PageDTO;
import com.alphay.boot.official.entity.Page;
import com.alphay.boot.official.vo.page.PageContentVo;
import com.alphay.boot.official.vo.page.PageDetailVO;
import com.alphay.boot.official.vo.page.PageVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface PageService extends IService<Page> {

    List<Page> getPageList(PageVO pageVO);

    PageDTO getPageContent(PageVO pageVo);

    void saveOrUpdatePage(PageContentVo pageContentVo);

    PageDTO getPageAllinfo(PageVO pageVO);

    int add(PageVO pageVO);

    int edit(PageVO pageVO);

    String deletePageByIds(Integer[] pageIds);

    void savePageAllInfo(PageDetailVO pageDetailVO);

    Page getPageInfo(Integer pageId);
}
