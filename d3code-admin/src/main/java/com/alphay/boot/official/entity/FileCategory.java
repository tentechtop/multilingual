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
@TableName("off_file_category")
public class FileCategory {

    /**
     * ID 主键
     */
    @ApiModelProperty(name = "文件ID",required = true)
    @TableId(value = "file_category_id",type = IdType.AUTO)
    private Integer fileCategoryId;


    @ApiModelProperty(name = "文件id",required = true)
    @TableField(value = "file_id")
    private Integer fileId;


    @ApiModelProperty(name = "分类id",required = true)
    @TableField(value = "category_id")
    private Integer categoryId;
}
