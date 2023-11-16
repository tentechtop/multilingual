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
@TableName("off_series")
public class Series {

    /**
     * ID 主键
     */
    @ApiModelProperty("产品ID")
    @TableId(value = "series_id",type = IdType.AUTO)
    private Integer seriesId;

    @ApiModelProperty(name = "系列名称",notes = "国际化字段")
    @TableField("series_name")
    private String seriesName ;

    @ApiModelProperty(name = "系列图片",notes = "")
    @TableField("img_url")
    private String imgUrl ;

    @ApiModelProperty(name = "系列描述",notes = "")
    @TableField("series_desc")
    private String seriesDesc ;

    @ApiModelProperty(name = "是否可用",notes = "0不可用/1可用")
    @TableField("is_enable")
    private Boolean isEnable ;

    @ApiModelProperty(name = "是否删除",notes = "0不删除/1删除")
    @TableField("is_delete")
    private Boolean isDelete ;

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
