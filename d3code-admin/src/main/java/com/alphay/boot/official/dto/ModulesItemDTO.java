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
public class ModulesItemDTO {

    /**
     * ID 主键
     */
    @ApiModelProperty(name = "模块元素ID")
    private Integer itemId;

    @ApiModelProperty(name = "模块ID")
    private Integer modulesId;

    @ApiModelProperty(name = "语言标志")
    private String lang;

    @ApiModelProperty(name = "排序")
    private Integer sort;

    @ApiModelProperty(name = "图片url路径")
    private String imgUrl;

    @ApiModelProperty(name = "视频图片预览")
    private String poster;

    @ApiModelProperty(name = "视频url路径")
    private String videoUrl;

    @ApiModelProperty(name = "描述1")
    private String title1;

    @ApiModelProperty(name = "描述2")
    private String title2;

    @ApiModelProperty(name = "描述1")
    private String desc1;

    @ApiModelProperty(name = "描述2")
    private String desc2;

    @ApiModelProperty(name = "目标地址1")
    private String url1;

    @ApiModelProperty(name = "路由标题1")
    private String url1Title;

    @ApiModelProperty(name = "目标地址2")
    private String url2;

    @ApiModelProperty(name = "路由标题2")
    private String url2Title;


}
