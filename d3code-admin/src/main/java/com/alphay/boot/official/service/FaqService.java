package com.alphay.boot.official.service;

import com.alphay.boot.official.dto.FaqDTO;
import com.alphay.boot.official.entity.Faq;
import com.alphay.boot.official.vo.FaqVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface FaqService extends IService<Faq> {
    List<FaqDTO> selectFaqList(FaqVO faqVO);

    FaqDTO selectFaqById(FaqVO faqVO);

    void saveFaq(FaqVO faqVO);

    void updateFaq(FaqVO faqVO);

    void deleteFaqByIds(Integer[] faqIds);
}
