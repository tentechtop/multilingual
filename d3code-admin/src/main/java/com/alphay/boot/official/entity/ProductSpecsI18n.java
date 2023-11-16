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
@TableName("off_product_specs_i18n")
public class ProductSpecsI18n {

    /**
     * ID 主键
     */
    @ApiModelProperty("语言ID")
    @TableId(value = "i18n_id",type = IdType.AUTO)
    private Integer i18nId;


    @ApiModelProperty("规格ID")
    @TableField(value = "specs_id")
    private Integer specsId;


    @ApiModelProperty("语言标志")
    @TableField(value = "lang")
    private String lang;

    @TranslationField
    @ApiModelProperty("规格名称")
    @TableField(value = "specs_name")
    private String specsName;

    @ApiModelProperty("规格图片")
    @TableField(value = "img_url")
    private String imgUrl;
}
