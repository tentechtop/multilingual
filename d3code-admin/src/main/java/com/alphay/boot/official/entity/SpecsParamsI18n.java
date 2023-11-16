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
@TableName("off_specs_params_i18n")
public class SpecsParamsI18n {

    /**
     * ID 主键
     */
    @ApiModelProperty("语言ID")
    @TableId(value = "i18n_id",type = IdType.AUTO)
    private Integer i18nId;

    @ApiModelProperty("参数ID")
    @TableField(value = "params_id")
    private Integer paramsId;

    @ApiModelProperty("语言标志")
    @TableField(value = "lang")
    private String lang;

    @TranslationField
    @ApiModelProperty("参数标题")
    @TableField(value = "params_title")
    private String paramsTitle;

    @ApiModelProperty("int类型参数")
    @TableField(value = "number_value")
    private Integer numberValue;

    @TranslationField
    @ApiModelProperty("字符串类型参数")
    @TableField(value = "string_value")
    private String stringValue;
}
