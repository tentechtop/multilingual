package com.alphay.boot.official.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 用于查找课程
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "课程")
public class FindCourseVO {

    @ApiModelProperty(name = "课程ID",required = true)
    private Integer courseId;

    @ApiModelProperty(name = "分类IDList",required = true)
    private List<Integer> categoryList;

    @ApiModelProperty(name = "课程名称",required = true)
    private String courseName;

    @ApiModelProperty(name = "课程名称",required = true)
    private String courseDesc;

    @ApiModelProperty(name = "课程封面",required = true)
    private String courseCover;

    @ApiModelProperty(name = "排序字段",required = true)
    private Integer sort;

    @ApiModelProperty(name = "是否可用",notes = "0不可用/1可用",required = true)
    private Boolean isEnable ;

    @ApiModelProperty(name = "是否删除",notes = "0不删除/1删除",required = true)
    private Boolean isDelete ;

}
