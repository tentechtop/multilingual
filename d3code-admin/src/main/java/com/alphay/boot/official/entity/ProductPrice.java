package com.alphay.boot.official.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("off_product_price")
public class ProductPrice {

    /**
     * ID 主键
     */
    @ApiModelProperty("语言ID")
    @TableId(value = "price_id",type = IdType.AUTO)
    private Integer priceId;

    @ApiModelProperty("产品ID")
    @TableField(value = "product_id")
    private Integer productId;

    @ApiModelProperty("语言标志")
    @TableField(value = "lang")
    private String lang;

    @ApiModelProperty("产品价格")
    @TableField(value = "price")
    private Double price;

    @ApiModelProperty("结算货币")
    @TableField(value = "currency")
    private String currency;

    @ApiModelProperty("结算货币符号")
    @TableField(value = "symbol")
    private String symbol;

    @ApiModelProperty(name = "创建时间",notes = "")
    private LocalDateTime createTime ;

    @ApiModelProperty(name = "更新时间",notes = "")
    private LocalDateTime updateTime ;


}
