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
@TableName("off_category_i18n")
public class CategoryI18n {

    /**
     * ID 主键
     */
    @ApiModelProperty(name = "分类国际化ID",required = true)
    @TableId(value = "i18n_id",type = IdType.AUTO)
    private Integer i18nId;

    @ApiModelProperty(name = "分类ID",required = true)
    @TableField(value = "category_id")
    private Integer categoryId;

    @ApiModelProperty(name = "语言标志",required = true)
    @TableField(value = "lang")
    private String lang;

    @TranslationField
    @ApiModelProperty(name = "分类名称",notes = "国际化字段",required = true)
    @TableField("category_name")
    private String categoryName ;

    @ApiModelProperty(name = "分类图片",notes = "",required = false)
    @TableField("img_url")
    private String imgUrl ;

    @TranslationField
    @ApiModelProperty(name = "分类描述",notes = "",required = false)
    @TableField("category_desc")
    private String categoryDesc ;

}
