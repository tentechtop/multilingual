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
@TableName("off_news")
public class News {

    /**
     * ID 主键
     */
    @ApiModelProperty(name = "文件ID",required = true)
    @TableId(value = "news_id",type = IdType.AUTO)
    private Integer newsId;

    @ApiModelProperty(name = "语言标志",required = true)
    @TableField(value = "lang")
    private String lang;

    @ApiModelProperty(name = "新闻封面图片url",required = true)
    @TableField(value = "cover_img_url")
    private String coverImgUrl;

    @TranslationField
    @ApiModelProperty(name = "新闻标题",required = true)
    @TableField(value = "news_title")
    private String newsTitle;

    @TranslationField
    @ApiModelProperty(name = "新闻内容",required = true)
    @TableField(value = "html_text")
    private String htmlText;

    @TranslationField
    @ApiModelProperty(name = "新闻内容seo",required = true)
    @TableField(value = "text")
    private String text;

    @ApiModelProperty(name = "排序字段 ASC升序",required = true)
    @TableField(value = "sort")
    private Integer sort;

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
