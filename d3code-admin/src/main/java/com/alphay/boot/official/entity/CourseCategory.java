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

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("off_course_category")
public class CourseCategory {

    /**
     * ID 主键
     */
    @ApiModelProperty(name = "课程分类ID",required = true)
    @TableId(value = "course_category_id",type = IdType.AUTO)
    private Integer courseCategoryId;

    @ApiModelProperty(name = "课程ID",required = true)
    @TableField(value = "course_id")
    private Integer courseId;

    @ApiModelProperty(name = "分类ID",required = true)
    @TableField(value = "category_id")
    private Integer categoryId;

}
