package com.alphay.boot.official.dto.page;

import com.alphay.boot.official.dto.ModulesDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 带着语言来请求 页面+语言 极个别需要 页面+位置+语言
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageDTO {

    private Integer pageId;

    private String lang;

    private String pageName;

    private String pageUrl;

    private String pageDesc;

    //模块列表
    private List<ModulesDTO> moduleDtoList;

}
