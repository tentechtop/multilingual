package com.alphay.boot.official.dto;

import io.swagger.annotations.ApiModel;
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
@ApiModel(description = "系列")
public class SeriesDTO {

    @ApiModelProperty("产品ID")
    private Integer seriesId;

    @ApiModelProperty(name = "系列名称",notes = "国际化字段")
    private String seriesName;

    @ApiModelProperty(name = "系列图片",notes = "")
    private String imgUrl;

    @ApiModelProperty(name = "系列描述",notes = "")
    private String seriesDesc;

    @ApiModelProperty(name = "是否可用",notes = "0不可用/1可用")
    private Boolean isEnable;

    @ApiModelProperty(name = "是否删除",notes = "0不删除/1删除")
    private Boolean isDelete;

    @ApiModelProperty(name = "创建人ID",notes = "")
    private Integer createByUid;

    @ApiModelProperty(name = "创建时间",notes = "")
    private LocalDateTime createTime;

    @ApiModelProperty(name = "更新人",notes = "")
    private Integer updateByUid;

    @ApiModelProperty(name = "更新时间",notes = "")
    private LocalDateTime updateTime;
}
