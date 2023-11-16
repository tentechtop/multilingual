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

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("off_category")
public class Category {

    /**
     * ID 主键
     */
    @ApiModelProperty(name = "产品ID",required = true)
    @TableId(value = "category_id",type = IdType.AUTO)
    private Integer categoryId;

    @ApiModelProperty(name = "分类名称",notes = "国际化字段",required = true)
    @TableField("category_name")
    private String categoryName ;

    @ApiModelProperty(name = "分类类型",notes = "分类类型",required = true)
    @TableField("category_type")
    private Integer categoryType ;

    @ApiModelProperty(name = "分类图片",notes = "",required = false)
    @TableField("img_url")
    private String imgUrl ;

    @ApiModelProperty(name = "分类描述",notes = "",required = false)
    @TableField("category_desc")
    private String categoryDesc ;

    @ApiModelProperty(name = "是否可用",notes = "0不可用/1可用",required = true)
    @TableField("is_enable")
    private Boolean isEnable ;

    @ApiModelProperty(name = "是否删除",notes = "0不删除/1删除",required = true)
    @TableField("is_delete")
    private Boolean isDelete ;

    @ApiModelProperty(name = "创建人ID",notes = "",required = false)
    @TableField("create_by_uid")
    private Integer createByUid ;

    @ApiModelProperty(name = "创建时间",notes = "",required = false)
    private LocalDateTime createTime ;

    @ApiModelProperty(name = "更新人",notes = "",required = false)
    @TableField("update_by_uid")
    private Integer updateByUid ;

    @ApiModelProperty(name = "更新时间",notes = "",required = false)
    private LocalDateTime updateTime ;


}
