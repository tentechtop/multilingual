package com.alphay.boot.official.entity;

import com.baomidou.mybatisplus.annotation.*;
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
@TableName("off_page")
public class Page {

    /**
     * ID 主键
     */
    @ApiModelProperty(name = "页面ID",required = true)
    @TableId(value = "page_id",type = IdType.AUTO)
    private Integer pageId;

    @ApiModelProperty(name = "页面名称",required = true)
    @TableField(value = "page_name")
    private String pageName;

    @ApiModelProperty(name = "页面地址")
    @TableField(value = "page_url")
    private String pageUrl;

    @ApiModelProperty(name = "页面描述")
    @TableField(value = "page_desc")
    private String pageDesc;

    @ApiModelProperty(name = "是否删除",notes = "0不删除/1删除")
    @TableField("is_delete")
    private Boolean isDelete ;

    @ApiModelProperty(name = "创建时间")
    private LocalDateTime createTime ;

    @ApiModelProperty(name = "更新时间")
    private LocalDateTime updateTime ;

}
