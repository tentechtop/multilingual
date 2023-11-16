package com.alphay.boot.official.dao;

import com.alphay.boot.official.dto.TagDTO;
import com.alphay.boot.official.entity.Tag;
import com.alphay.boot.official.vo.TagVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagDao extends BaseMapper<Tag> {



    @Select("select  max(tag_id) from off_tag")
    Integer getMaxId();

    List<TagDTO> selectNewsList(TagVO tagVO);

    TagDTO selectTagById(TagVO tagVO);
}
