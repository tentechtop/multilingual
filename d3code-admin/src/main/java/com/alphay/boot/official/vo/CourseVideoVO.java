package com.alphay.boot.official.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseVideoVO {
    /**
     * ID 主键
     */
    @ApiModelProperty(name = "视频ID",required = true)
    private Integer videoId;

    @ApiModelProperty(name = "课程ID",required = true)
    private Integer courseId;

    @ApiModelProperty(name = "语言标志",required = true)
    private String lang;

    @ApiModelProperty(name = "排序字段升序",required = true)
    private Integer sort;

    @ApiModelProperty(name = "视频标题",required = true)
    private String title;

    @ApiModelProperty(name = "视频封面",required = true)
    private String description;

    @ApiModelProperty(name = "视频封面",required = true)
    private String poster;

    @ApiModelProperty(name = "视频URL",required = true)
    private String videoUrl;

    @ApiModelProperty(name = "播放次数",required = true)
    private int viewCount;

    @ApiModelProperty(name = "点赞数量",required = true)
    private int likeCount;

}
