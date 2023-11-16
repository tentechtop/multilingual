package com.alphay.boot.official.dao;

import com.alphay.boot.official.dto.FaqDTO;
import com.alphay.boot.official.entity.Faq;
import com.alphay.boot.official.vo.FaqVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FaqDao extends BaseMapper<Faq> {
    List<FaqDTO> selectFileList(FaqVO faqVO);

    FaqDTO selectFaqById(FaqVO faqVO);


    @Select("select  max(faq_id) from off_faq")
    Integer getMaxId();
}
