package com.alphay.boot.official.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("off_modules")
public class Modules {

    /**
     * ID 主键
     */
    @ApiModelProperty(name = "模块ID",required = true)
    @TableId(value = "modules_id",type = IdType.AUTO)
    private Integer modulesId;

    @ApiModelProperty(name = "页面ID")
    @TableField(value = "page_id")
    private Integer pageId;

    @ApiModelProperty(name = "排序")
    @TableField(value = "sort")
    private Integer sort;

    @ApiModelProperty(name = "位置(组件)",required = true)
    @TableField(value = "position")
    private String position;

    @ApiModelProperty(name = "标题",required = true)
    @TableField(value = "title")
    private String title;

    @ApiModelProperty(name = "副标题",required = true)
    @TableField(value = "sub_title")
    private String subTitle;

    @ApiModelProperty(name = "创建时间",notes = "")
    private LocalDateTime createTime ;

    @ApiModelProperty(name = "更新时间",notes = "")
    private LocalDateTime updateTime ;

}
