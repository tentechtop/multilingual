package com.alphay.boot.official.entity;

import com.alphay.boot.official.annotation.TranslationField;
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
@TableName("off_course_video")
public class CourseVideo {

    /**
     * ID 主键
     */
    @ApiModelProperty(name = "视频ID",required = true)
    @TableId(value = "video_id",type = IdType.AUTO)
    private Integer videoId;

    @ApiModelProperty(name = "课程ID",required = true)
    @TableField(value = "course_id")
    private Integer courseId;

    @ApiModelProperty(name = "语言标志",required = true)
    @TableField(value = "lang")
    private String lang;

    @ApiModelProperty(name = "排序字段升序",required = true)
    @TableField(value = "sort")
    private Integer sort;

    @TranslationField
    @ApiModelProperty(name = "视频标题",required = true)
    @TableField(value = "title")
    private String title;

    @ApiModelProperty(name = "视频描述",required = true)
    @TableField(value = "description")
    private String description;

    @ApiModelProperty(name = "视频封面",required = true)
    @TableField(value = "poster")
    private String poster;

    @ApiModelProperty(name = "视频URL",required = true)
    @TableField(value = "video_url")
    private String videoUrl;

    @ApiModelProperty(name = "播放次数",required = true)
    @TableField(value = "view_count")
    private int viewCount;

    @ApiModelProperty(name = "点赞数量",required = true)
    @TableField(value = "like_count")
    private int likeCount;

    @ApiModelProperty(name = "是否可用",notes = "0不可用/1可用",required = true)
    @TableField("is_enable")
    private Integer isEnable ;

    @ApiModelProperty(name = "是否删除",notes = "0不删除/1删除",required = true)
    @TableField("is_delete")
    private Integer isDelete ;

    @ApiModelProperty(name = "创建时间",notes = "")
    private LocalDateTime createTime ;

    @ApiModelProperty(name = "更新时间",notes = "")
    private LocalDateTime updateTime ;
}
