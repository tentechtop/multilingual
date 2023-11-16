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
@TableName("off_product_i18n")
public class ProductI18n {

    /**
     * ID 主键
     */
    @ApiModelProperty("产品国际化ID")
    @TableId(value = "i18n_id",type = IdType.AUTO)
    private Integer i18nId;

    /**
     * ID 主键
     */
    @ApiModelProperty("产品ID")
    @TableField(value = "product_id")
    private Integer productId;

    @ApiModelProperty("语言标志")
    @TableField(value = "lang")
    private String lang;

    /**
     *产品名称 Kwun-B33H
     */
    @TranslationField
    @ApiModelProperty("产品名称")
    @TableField("product_name")
    private String productName;

    /**
     *产品昵称 全能王
     */
    @TranslationField
    @ApiModelProperty("产品昵称")
    @TableField("product_nickname")
    private String productNickname;

    /**
     *产品描述
     */
    @TranslationField
    @ApiModelProperty("产品描述")
    @TableField("product_desc")
    private String productDesc;

    /** 产品预览图 */
    @ApiModelProperty(name = "产品预览图",notes = "")
    @TableField("img_url")
    private String imgUrl;

}
