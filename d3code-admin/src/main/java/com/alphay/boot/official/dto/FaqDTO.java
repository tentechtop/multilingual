package com.alphay.boot.official.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FaqDTO {


    /**
     * ID 主键
     */
    @ApiModelProperty(name = "常见问题与回答ID",required = true)
    private Integer faqId;

    @ApiModelProperty(name = "语言标志")
    private String lang ;

    @ApiModelProperty(name = "分类")
    private List<FaqCategoryDTO>  categoryDTOList;

    @ApiModelProperty(name = "标签")
    private List<FaqTagDTO>  tagDTOList;

    @ApiModelProperty(name = "排序字段")
    private Integer sort ;

    @ApiModelProperty(name = "问题概述")
    private String faqTitle ;

    @ApiModelProperty(name = "问题详细")
    private String faqQuestion ;

    @ApiModelProperty(name = "问题解答")
    private String faqAnswers ;

    @ApiModelProperty(name = "点赞数量")
    private Integer likeNumber ;

    @ApiModelProperty(name = "是否删除",notes = "0不删除/1删除")
    private Boolean isDelete ;

    @ApiModelProperty(name = "创建时间")
    private LocalDateTime createTime ;

    @ApiModelProperty(name = "更新时间")
    private LocalDateTime updateTime ;
}
