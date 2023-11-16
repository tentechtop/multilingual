package com.alphay.boot.official.service;

import com.alphay.boot.official.dto.TagDTO;
import com.alphay.boot.official.vo.TagVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.alphay.boot.official.entity.Tag;

import java.util.List;

public interface TagService extends IService<Tag> {
    List<TagDTO> selectNewsList(TagVO tagVO);

    TagDTO selectTagById(TagVO tagVO);

    void saveTag(TagVO tagVO);

    void updateTag(TagVO tagVO);

    String deleteTagByIds(Integer[] tagIds);
}
