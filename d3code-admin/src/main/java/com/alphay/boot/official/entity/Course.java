package com.alphay.boot.official.entity;

import com.alphay.boot.official.annotation.TranslationField;
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
@TableName("off_course")
public class Course {

    /**
     * ID 主键
     */
    @ApiModelProperty(name = "课程ID",required = true)
    @TableId(value = "course_id",type = IdType.AUTO)
    private Integer courseId;

    @TranslationField
    @ApiModelProperty(name = "课程名称",required = true)
    @TableField(value = "course_name")
    private String courseName;

    @TranslationField
    @ApiModelProperty(name = "课程描述",required = true)
    @TableField(value = "course_desc")
    private String courseDesc;

    @ApiModelProperty(name = "课程封面",required = true)
    @TableField(value = "course_cover")
    private String courseCover;

    @ApiModelProperty(name = "排序字段",required = true)
    @TableField(value = "sort")
    private Integer sort;

    @ApiModelProperty(name = "是否可用",notes = "0不可用/1可用",required = true)
    @TableField("is_enable")
    private Integer isEnable ;

    @ApiModelProperty(name = "是否删除",notes = "0不删除/1删除",required = true)
    @TableField("is_delete")
    private Integer isDelete ;

    @ApiModelProperty(name = "创建时间",notes = "")
    private LocalDateTime createTime ;

    @ApiModelProperty(name = "更新时间",notes = "")
    private LocalDateTime updateTime ;


}
