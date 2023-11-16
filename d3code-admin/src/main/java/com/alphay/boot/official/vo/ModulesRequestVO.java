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
@ApiModel(description = "模块")
public class ModulesRequestVO {

    @ApiModelProperty(name = "modulesId", value = "模块Id", dataType = "Integer")
    private Integer modulesId;

    @ApiModelProperty(name = "pageId", value = "页面id", dataType = "Integer")
    private Integer pageId;


    @ApiModelProperty(name = "lang", value = "语言标志", dataType = "String")
    private String lang;
}
