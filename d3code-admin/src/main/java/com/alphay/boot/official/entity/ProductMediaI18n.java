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
@TableName("off_product_media_i18n")
public class ProductMediaI18n {



    @ApiModelProperty("语言ID")
    @TableId(value = "i18n_id",type = IdType.AUTO)
    private Integer i18nId;

    @ApiModelProperty("媒体ID")
    @TableField(value = "media_id")
    private Integer mediaId;

    @ApiModelProperty("资源类型 image / video 两种")
    @TableField(value = "lang")
    private String lang;

    @ApiModelProperty("图片url地址")
    @TableField(value = "img_url")
    private String imgUrl;

    @ApiModelProperty("视频url地址")
    @TableField(value = "video_url")
    private String videoUrl;

    @TranslationField
    @ApiModelProperty("资源描述")
    @TableField(value = "alt")
    private String alt;
}
