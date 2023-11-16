package com.alphay.boot.official.dto;

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
public class NewsDTO {

    /**
     * ID 主键
     */
    @ApiModelProperty(name = "新闻ID",required = true)
    private Integer newsId;

    @ApiModelProperty(name = "语言标志",required = true)
    private String lang;

    @ApiModelProperty(name = "新闻封面图片url",required = true)
    private String coverImgUrl;

    @ApiModelProperty(name = "新闻标题",required = true)
    private String newsTitle;

    @ApiModelProperty(name = "新闻内容",required = true)
    private String htmlText;

    @ApiModelProperty(name = "新闻内容seo",required = true)
    private String text;

    @ApiModelProperty(name = "排序字段 ASC升序",required = true)
    private Integer sort;

    @ApiModelProperty(name = "是否可用",notes = "0不可用/1可用")
    private Boolean isEnable ;

    @ApiModelProperty(name = "创建时间",notes = "")
    private LocalDateTime createTime ;

    @ApiModelProperty(name = "更新时间",notes = "")
    private LocalDateTime updateTime ;
}
