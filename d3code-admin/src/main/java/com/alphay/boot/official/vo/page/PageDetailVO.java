package com.alphay.boot.official.vo.page;

import com.alphay.boot.official.dto.ModulesDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageDetailVO {


    private Integer pageId;

    private String lang;

    private String pageName;

    private String pageUrl;

    private String pageDesc;

    private Boolean isDelete ;

    //模块列表
    private List<ModulesDTO> modulesDtoList;


}
