package com.alphay.boot.official.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductPriceDTO {

    /**
     * ID 主键
     */
    @ApiModelProperty("语言ID")
    @TableId(value = "price_id",type = IdType.AUTO)
    private Integer priceId;

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





}
