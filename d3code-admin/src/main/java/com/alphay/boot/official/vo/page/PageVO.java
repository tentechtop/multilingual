package com.alphay.boot.official.vo.page;

import io.swagger.annotations.ApiModel;
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
@ApiModel(description = "页面元素请求")
public class PageVO {

    /**
     * 语言标志
     */
    @ApiModelProperty(name = "lang", value = "语言标志", required = true, dataType = "String")
    private String lang;

    private Integer pageId;

    private String pageName;

    private String pageUrl;

    private String pageDesc;

    private Boolean isDelete ;

    private LocalDateTime createTime ;

    private LocalDateTime updateTime ;

}
