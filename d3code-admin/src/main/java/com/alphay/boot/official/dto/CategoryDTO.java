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
public class CategoryDTO {

    /**
     * ID 主键
     */
    @ApiModelProperty(name = "产品ID",required = true)
    private Integer categoryId;

    @ApiModelProperty(name = "语言标志",notes = "国际化字段",required = true)
    private String lang ;

    @ApiModelProperty(name = "分类名称",notes = "国际化字段",required = true)
    private String categoryName ;

    @ApiModelProperty(name = "分类类型",notes = "分类类型",required = true)
    private Integer categoryType ;

    @ApiModelProperty(name = "分类图片",notes = "",required = false)
    private String imgUrl ;

    @ApiModelProperty(name = "分类描述",notes = "",required = false)
    private String categoryDesc ;

    @ApiModelProperty(name = "是否可用",notes = "0不可用/1可用",required = true)
    private Boolean isEnable ;

    @ApiModelProperty(name = "是否删除",notes = "0不删除/1删除",required = true)
    private Boolean isDelete ;

    @ApiModelProperty(name = "创建时间",notes = "",required = false)
    private LocalDateTime createTime ;

    @ApiModelProperty(name = "更新时间",notes = "",required = false)
    private LocalDateTime updateTime ;
}
