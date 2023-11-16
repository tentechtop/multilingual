package com.alphay.boot.official.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


/**
 * 查询条件
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "查询条件")
public class ConditionVO {

    /**
     * 页码
     */
    @ApiModelProperty(name = "current", value = "页码", dataType = "Integer")
    private Integer current;

    /**
     * 条数
     */
    @ApiModelProperty(name = "size", value = "条数", dataType = "Integer")
    private Integer size;

    /**
     * 搜索内容
     */
    @ApiModelProperty(name = "keywords", value = "搜索内容", dataType = "String")
    private String keywords;

    /**
     * 分类id
     */
    @ApiModelProperty(name = "categoryId", value = "分类id", dataType = "Integer")
    private Integer categoryId;

    /**
     * 标签id
     */
    @ApiModelProperty(name = "tagId", value = "标签id", dataType = "Integer")
    private Integer tagId;

    /**
     * 登录类型
     */
    @ApiModelProperty(name = "type", value = "登录类型", dataType = "Integer")
    private Integer loginType;

    /**
     * 类型
     */
    @ApiModelProperty(name = "type", value = "类型", dataType = "Integer")
    private Integer type;

    /**
     * 状态
     */
    @ApiModelProperty(name = "status", value = "状态", dataType = "Boolean")
    private Boolean status;

    /**
     * 开始时间
     */
    @ApiModelProperty(name = "startTime", value = "开始时间", dataType = "LocalDateTime")
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    @ApiModelProperty(name = "endTime", value = "结束时间", dataType = "LocalDateTime")
    private LocalDateTime endTime;


    /**
     * 是否可用
     */
    @ApiModelProperty(name = "isEnable", value = "是否可用", dataType = "Integer")
    private Integer isEnable;

    /**
     * 是否删除
     */
    @ApiModelProperty(name = "isDelete", value = "是否删除", dataType = "Integer")
    private Integer isDelete;

    /**
     * 是否审核
     */
    @ApiModelProperty(name = "isReview", value = "是否审核", dataType = "Integer")
    private Integer isReview;

}
