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
@TableName("off_tag_i18n")
public class TagI18n {

    /**
     * ID 主键
     */
    @ApiModelProperty(name = "标签国际化ID",required = true)
    @TableId(value = "i18n_id",type = IdType.AUTO)
    private Integer i18nId;

    /**
     * ID 主键
     */
    @ApiModelProperty(name = "系列ID",required = true)
    @TableField(value = "tag_id")
    private Integer tagId;


    @ApiModelProperty(name = "语言标志",required = true)
    @TableField(value = "lang")
    private String lang;



    @TranslationField
    @ApiModelProperty(name = "标签名称",notes = "国际化字段",required = true)
    @TableField("tag_name")
    private String tagName ;

}
