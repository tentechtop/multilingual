package com.alphay.boot.official.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.alphay.boot.official.annotation.TranslationField;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("off_modules_i18n")
public class ModulesI18n {


    /**
     * ID 主键
     */
    @ApiModelProperty(name = "模块ID",required = true)
    @TableId(value = "i18n_id",type = IdType.AUTO)
    private Integer i18nId;

    @ApiModelProperty(name = "模块ID")
    @TableField(value = "modules_id")
    private Integer modulesId;

    @ApiModelProperty(name = "语言标志",required = true)
    @TableField(value = "lang")
    private String lang;

    @TranslationField
    @ApiModelProperty(name = "标题",required = true)
    @TableField(value = "title")
    private String title;

    @TranslationField
    @ApiModelProperty(name = "副标题",required = true)
    @TableField(value = "sub_title")
    private String subTitle;
}
