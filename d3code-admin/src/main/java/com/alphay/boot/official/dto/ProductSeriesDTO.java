package com.alphay.boot.official.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductSeriesDTO {

    /**
     * ID 主键
     */
    @ApiModelProperty("产品ID")
    @TableId(value = "series_id",type = IdType.AUTO)
    private Integer seriesId;

    @ApiModelProperty(name = "语言标志")
    @TableField("lang")
    private String lang ;

    @ApiModelProperty(name = "系列名称",notes = "国际化字段")
    @TableField("series_name")
    private String seriesName ;

    @ApiModelProperty(name = "系列图片",notes = "")
    @TableField("img_url")
    private String imgUrl ;

    @ApiModelProperty(name = "系列描述",notes = "")
    @TableField("series_desc")
    private String seriesDesc ;

}
