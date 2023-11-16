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
@TableName("off_seo")
public class Seo {

    /**
     * ID 主键
     */
    @ApiModelProperty(name = "搜索优化ID",required = true)
    @TableId(value = "seo_id",type = IdType.AUTO)
    private Integer seoId;

    @ApiModelProperty(name = "页面名称",required = true)
    @TableField(value = "page_id")
    private Integer pageId;

    @ApiModelProperty(name = "语言标志")
    @TableField(value = "lang")
    private String lang;

    @TranslationField
    @ApiModelProperty(name = "标题")
    @TableField(value = "title")
    private String title;

    @TranslationField
    @ApiModelProperty(name = "关键词")
    @TableField(value = "keywords")
    private String keywords;

    @TranslationField
    @ApiModelProperty(name = "描述")
    @TableField(value = "description")
    private String description;

}
