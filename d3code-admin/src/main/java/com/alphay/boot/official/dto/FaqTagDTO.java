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
public class FaqTagDTO {

    @ApiModelProperty("标签ID")
    private Integer tagId;

    @ApiModelProperty(name = "语言标志",notes = "语言标志")
    private String lang ;

    @ApiModelProperty(name = "标签名称",notes = "")
    private String tagName ;

    @ApiModelProperty(name = "标签类型",notes = "")
    private Integer tagType ;


}
