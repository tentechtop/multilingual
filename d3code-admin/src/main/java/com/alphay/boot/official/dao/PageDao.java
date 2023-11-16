package com.alphay.boot.official.dao;

import com.alphay.boot.official.dto.page.PageDTO;
import com.alphay.boot.official.entity.Page;
import com.alphay.boot.official.vo.page.PageVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PageDao extends BaseMapper<Page> {

    List<Page> selectPageList(PageVO pageVO);

    PageDTO selectPageByLangAndId(String lang, Integer pageId);


    @Select("SELECT MAX(page_id) FROM off_page")
    Integer getMaxId();
}
