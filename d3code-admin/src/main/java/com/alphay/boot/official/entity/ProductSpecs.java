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


//产品规格
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("off_product_specs")
public class ProductSpecs {

    /**
     * ID 主键
     */
    @ApiModelProperty("规格ID")
    @TableId(value = "specs_id",type = IdType.AUTO)
    private Integer specsId;

    @ApiModelProperty("产品ID")
    @TableField(value = "product_id")
    private Integer productId;

    @ApiModelProperty("排序字段")
    @TableField(value = "sort")
    private Integer sort;

    @TranslationField
    @ApiModelProperty("规格名称")
    @TableField(value = "specs_name")
    private String specsName;

    @ApiModelProperty("规格图片")
    @TableField(value = "img_url")
    private String imgUrl;

    @ApiModelProperty(name = "是否可用",notes = "0不可用/1可用")
    @TableField("is_enable")
    private Boolean isEnable ;

    @ApiModelProperty(name = "是否删除",notes = "0不删除/1删除")
    @TableField("is_delete")
    private Boolean isDelete ;

}
