package com.alphay.boot.official.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ModulesInfoDTO {

    /**
     * 模块id
     */
    @ApiModelProperty(name = "modulesId", value = "模块id", dataType = "Integer")
    private Integer modulesId;


    @ApiModelProperty(name = "modulesId", value = "页面id", dataType = "Integer")
    private Integer pageId;

    /**
     * 语言标志
     */
    @ApiModelProperty(name = "lang", value = "语言标志", dataType = "String")
    private String lang;

    /**
     * 排序字段
     */
    @ApiModelProperty(name = "sort", value = "排序字段",  dataType = "Integer")
    private Integer sort;

    /**
     * 地址位置
     */
    @ApiModelProperty(name = "位置(页面地址+组件位置)", value = "页面地址", dataType = "String")
    private String position;

    /**
     * 模块标题
     */
    @ApiModelProperty(name = "title", value = "模块标题", dataType = "String")
    private String title;

    /**
     * 副标题
     */
    @ApiModelProperty(name = "subTitle", value = "副标题", dataType = "String")
    private String subTitle;

}
