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
public class SpecsParamsVO {

    /**
     * ID 主键
     */
    @ApiModelProperty("参数ID")
    private Integer paramsId;

    @ApiModelProperty("规格ID")
    private Integer specsId;

    @ApiModelProperty("语言标志")
    private String lang;

    @ApiModelProperty("排序字段")
    private Integer sort;

    @ApiModelProperty("参数标题")
    private String paramsTitle;

    @ApiModelProperty("参数值类型")
    private String valueType;

    @ApiModelProperty("int类型参数")
    private Integer numberValue;

    @ApiModelProperty("字符串类型参数")
    private String stringValue;
}
