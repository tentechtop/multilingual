package com.alphay.boot.official.entity;

import com.alphay.boot.official.annotation.TranslationField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("off_series_i18n")
public class SeriesI18n {

    /**
     * ID 主键
     */
    @ApiModelProperty(name = "产品国际化ID",required = true)
    @TableId(value = "i18n_id",type = IdType.AUTO)
    private Integer i18nId;

    /**
     * ID 主键
     */
    @ApiModelProperty(name = "系列ID",required = true)
    @TableField(value = "series_id")
    private Integer seriesId;

    @ApiModelProperty(name = "语言标志",required = true)
    @TableField(value = "lang")
    private String lang;

    @TranslationField
    @ApiModelProperty(name = "系列名称",notes = "国际化字段",required = true)
    @TableField("series_name")
    private String seriesName ;

    @TranslationField
    @ApiModelProperty(name = "系列描述",notes = "",required = false)
    @TableField("series_desc")
    private String seriesDesc ;

    @ApiModelProperty(name = "系列图片",notes = "",required = false)
    @TableField("img_url")
    private String imgUrl ;

}
