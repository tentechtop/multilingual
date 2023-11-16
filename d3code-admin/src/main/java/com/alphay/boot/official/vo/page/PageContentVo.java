package com.alphay.boot.official.vo.page;

import com.alphay.boot.official.dto.ModulesDTO;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "添加一个页面")
public class PageContentVo {

    /**
     * pageId = categoryId
     */
    private Integer pageId;

    private String pageName;

    //模块列表
    private List<ModulesDTO> moduleDtoList;
}
