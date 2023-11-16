package com.alphay.boot.official.entity;

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
@TableName("off_product_tag")
public class ProductTag {

    /**
     * ID 主键
     */
    @ApiModelProperty("产品标签ID")
    @TableId(value = "product_tag_id",type = IdType.AUTO)
    private Integer productTagId;

    /**
     * ID 主键
     */
    @ApiModelProperty("产品ID")
    @TableField(value = "product_id")
    private Integer productId;

    /**
     * ID 主键
     */
    @ApiModelProperty("标签ID")
    @TableField(value = "tag_id")
    private Integer tagId;
}
