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

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("off_course_i18n")
public class CourseI18n {

    /**
     * ID 主键
     */
    @ApiModelProperty(name = "国际化ID",required = true)
    @TableId(value = "i18n_id",type = IdType.AUTO)
    private Integer i18nId;

    @ApiModelProperty(name = "课程id",required = true)
    @TableField(value = "course_id")
    private Integer courseId;

    @ApiModelProperty(name = "语言标志")
    @TableField(value = "lang")
    private String lang;

    @TranslationField
    @ApiModelProperty(name = "课程名称")
    @TableField(value = "course_name")
    private String courseName;

    @TranslationField
    @ApiModelProperty(name = "课程描述")
    @TableField(value = "course_desc")
    private String courseDesc;

    @ApiModelProperty(name = "课程封面")
    @TableField(value = "course_cover")
    private String courseCover;

}
