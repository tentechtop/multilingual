package com.alphay.boot.official.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.alphay.boot.official.annotation.TranslationField;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 产品实体类  Kwun-B33H 高压电机等等
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("off_product")
public class Product {

    /**
     * ID 主键
     */
    @ApiModelProperty("产品ID")
    @TableId(value = "product_id",type = IdType.AUTO)
    private Integer productId;

    /**
     *产品型号 Kwun-B33H
     */
    @ApiModelProperty("产品型号")
    @TableField("product_model")
    private String productModel;

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

    @ApiModelProperty("排序字段")
    @TableField("sort")
    private Integer sort;

    /** 产品预览图 */
    @ApiModelProperty(name = "产品预览图",notes = "")
    @TableField("img_url")
    private String imgUrl;

    @ApiModelProperty(name = "是否可用",notes = "0不可用/1可用")
    @TableField("is_enable")
    private Boolean isEnable ;

    @ApiModelProperty(name = "是否删除",notes = "0不删除/1删除")
    @TableField("is_delete")
    private Boolean isDelete ;

    @ApiModelProperty(name = "乐观锁",notes = "")
    @TableField("revision")
    private Integer revision ;

    @ApiModelProperty(name = "创建人ID",notes = "")
    @TableField("create_by_uid")
    private Integer createByUid ;

    @ApiModelProperty(name = "创建时间",notes = "")
    private LocalDateTime createTime ;

    @ApiModelProperty(name = "更新人",notes = "")
    @TableField("update_by_uid")
    private Integer updateByUid ;

    @ApiModelProperty(name = "更新时间",notes = "")
    private LocalDateTime updateTime ;

}
