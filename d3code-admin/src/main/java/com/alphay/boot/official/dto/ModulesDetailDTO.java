package com.alphay.boot.official.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ModulesDetailDTO {


    @ApiModelProperty(name = "模块ID",required = true)
    private Integer modulesId;

    @ApiModelProperty(name = "页面ID")
    private Integer pageId;

    @ApiModelProperty(name = "排序")
    private Integer sort;

    @ApiModelProperty(name = "位置(组件)",required = true)
    private String position;

    @ApiModelProperty(name = "标题",required = true)
    private String title;

    @ApiModelProperty(name = "副标题",required = true)
    private String subTitle;

    @ApiModelProperty(name = "创建时间",notes = "")
    private LocalDateTime createTime ;

    @ApiModelProperty(name = "更新时间",notes = "")
    private LocalDateTime updateTime ;

}
