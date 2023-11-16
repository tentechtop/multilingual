package com.alphay.boot.official.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "新闻")
public class NewsVO {

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


}
