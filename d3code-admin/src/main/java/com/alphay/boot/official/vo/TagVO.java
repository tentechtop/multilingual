package com.alphay.boot.official.vo;


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
@ApiModel(description = "标签")
public class TagVO {

    /**
     * ID 主键
     */
    @ApiModelProperty("标签ID")
    private Integer tagId;

    @ApiModelProperty(name = "语言标志",notes = "语言标志")
    private String lang ;

    @ApiModelProperty(name = "标签名称",notes = "")
    private String tagName ;

    @ApiModelProperty(name = "标签类型",notes = "")
    private Integer tagType ;

    @ApiModelProperty(name = "是否可用",notes = "0不可用/1可用")
    private Boolean isEnable ;

    @ApiModelProperty(name = "是否删除",notes = "0不删除/1删除")
    private Boolean isDelete ;

    @ApiModelProperty(name = "创建人ID",notes = "")
    private Integer createByUid ;

    @ApiModelProperty(name = "创建时间",notes = "")
    private LocalDateTime createTime ;

    @ApiModelProperty(name = "更新人",notes = "")
    private Integer updateByUid ;

    @ApiModelProperty(name = "更新时间",notes = "")
    private LocalDateTime updateTime ;

}
