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

//中间
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("off_product_category")
public class ProductCategory {


    /**
     * ID 主键
     */
    @ApiModelProperty("产品ID")
    @TableId(value = "product_category_id",type = IdType.AUTO)
    private Integer productCategoryId;

    /**
     * ID 主键
     */
    @ApiModelProperty("产品ID")
    @TableField(value = "product_id")
    private Integer productId;

    /**
     * ID 主键
     */
    @ApiModelProperty("分类ID")
    @TableField(value = "category_id")
    private Integer categoryId;
}
