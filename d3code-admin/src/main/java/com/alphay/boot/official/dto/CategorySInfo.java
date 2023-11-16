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
public class CategorySInfo {

    /**
     * 用于给产品或者问答分类时提供选择时使用 默认输出中文 可以根据语言
     */
    @ApiModelProperty(name = "产品ID",required = true)
    private Integer categoryId;

    @ApiModelProperty(name = "分类名称",notes = "国际化字段",required = true)
    private String categoryName ;

}
