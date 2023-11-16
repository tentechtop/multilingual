package com.alphay.boot.official.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseCategoryDTO {

    @ApiModelProperty(name = "产品ID",required = true)
    private Integer categoryId;

    @ApiModelProperty(name = "分类名称",notes = "国际化字段",required = true)
    private String categoryName ;

    @ApiModelProperty(name = "分类类型",notes = "分类类型",required = true)
    private Integer categoryType ;

}
