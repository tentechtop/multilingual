package com.alphay.boot.official.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.alphay.boot.official.annotation.TranslationField;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("off_file_i18n")
public class FileI18n {

    @ApiModelProperty(name = "国际化ID",required = true)
    @TableId(value = "i18n_id",type = IdType.AUTO)
    private Integer i18nId;

    @ApiModelProperty(name = "文件ID",required = true)
    @TableField(value = "file_id")
    private Integer fileId;

    @ApiModelProperty(name = "语言标志",required = true)
    @TableField(value = "lang")
    private String lang;

    @TranslationField
    @ApiModelProperty(name = "文件名称",required = true)
    @TableField(value = "file_name")
    private String fileName;

    @ApiModelProperty("文件url")
    @TableField(value = "file_url")
    private String fileUrl;

    @TranslationField
    @ApiModelProperty("文件描述")
    @TableField(value = "file_desc")
    private String fileDesc;

    @TranslationField
    @ApiModelProperty("文件内容")
    @TableField(value = "file_content")
    private String fileContent;


}
