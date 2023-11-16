package com.alphay.boot.official.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.alphay.boot.official.annotation.TranslationField;
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
@TableName("off_modules_item")
public class ModulesItem {

    /**
     * ID 主键
     */
    @ApiModelProperty(name = "模块ID",required = true)
    @TableId(value = "item_id",type = IdType.AUTO)
    private Integer itemId;

    @ApiModelProperty(name = "模块ID",required = true)
    @TableField(value = "modules_id")
    private Integer modulesId;

    @ApiModelProperty(name = "语言标志",required = true)
    @TableField(value = "lang")
    private String lang;

    @ApiModelProperty(name = "排序",required = true)
    @TableField(value = "sort")
    private Integer sort;

    @ApiModelProperty(name = "图片url路径",required = false)
    @TableField(value = "img_url")
    private String imgUrl;

    @ApiModelProperty(name = "视频预览封面",required = false)
    @TableField(value = "poster")
    private String poster;

    @ApiModelProperty(name = "视频url路径",required = false)
    @TableField(value = "video_url")
    private String videoUrl;

    @TranslationField
    @ApiModelProperty(name = "描述1",required = false)
    @TableField(value = "title1")
    private String title1;

    @TranslationField
    @ApiModelProperty(name = "描述2",required = false)
    @TableField(value = "title2")
    private String title2;

    @TranslationField
    @ApiModelProperty(name = "描述1",required = false)
    @TableField(value = "desc1")
    private String desc1;

    @TranslationField
    @ApiModelProperty(name = "描述2",required = false)
    @TableField(value = "desc2")
    private String desc2;

    @ApiModelProperty(name = "目标地址1",required = false)
    @TableField(value = "url1")
    private String url1;

    @TranslationField
    @ApiModelProperty(name = "路由标题1",required = false)
    @TableField(value = "url1_title")
    private String url1Title;

    @ApiModelProperty(name = "目标地址2",required = false)
    @TableField(value = "url2")
    private String url2;

    @TranslationField
    @ApiModelProperty(name = "路由标题2",required = false)
    @TableField(value = "url2_title")
    private String url2Title;

    @ApiModelProperty(name = "创建时间",notes = "")
    private LocalDateTime createTime ;

    @ApiModelProperty(name = "更新时间",notes = "")
    private LocalDateTime updateTime ;



}
