package com.alphay.boot.official.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ModulesDTO {


    @ApiModelProperty(name = "模块ID")
    private Integer modulesId;

    @ApiModelProperty(name = "语言标志")
    private String lang;

    @ApiModelProperty(name = "排序")
    private Integer sort;

    @ApiModelProperty(name = "位置(页面地址+组件位置)")
    private String position;

    @ApiModelProperty(name = "标题")
    private String title;

    @ApiModelProperty(name = "副标题")
    private String subTitle;

    @ApiModelProperty(name = "模块元素")
    private List<ModulesItemDTO> modulesItemDtoList;
}
