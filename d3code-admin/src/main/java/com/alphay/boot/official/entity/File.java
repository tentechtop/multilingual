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
@TableName("off_file")
public class File {

    /**
     * ID 主键
     */
    @ApiModelProperty(name = "文件ID",required = true)
    @TableId(value = "file_id",type = IdType.AUTO)
    private Integer fileId;

    @ApiModelProperty("排序字段升序ASC")
    @TableField("sort")
    private Integer sort ;

    @ApiModelProperty(name = "文件格式",required = true)
    @TableField(value = "file_format")
    private String fileFormat;

    @ApiModelProperty(name = "文件名称",required = true)
    @TableField(value = "file_name")
    private String fileName;

    @ApiModelProperty("文件url")
    @TableField(value = "file_url")
    private String fileUrl;

    @ApiModelProperty("文件描述")
    @TableField(value = "file_desc")
    private String fileDesc;

    @ApiModelProperty("文件内容")
    @TableField(value = "file_content")
    private String fileContent;

    @ApiModelProperty(name = "是否可用",notes = "0不可用/1可用")
    @TableField("is_enable")
    private Boolean isEnable ;

    @ApiModelProperty(name = "是否删除",notes = "0不删除/1删除")
    @TableField("is_delete")
    private Boolean isDelete ;

    @ApiModelProperty(name = "创建人ID",notes = "")
    @TableField("create_by_uid")
    private Integer createByUid ;

    @ApiModelProperty(name = "创建时间",notes = "")
    private LocalDateTime createTime ;

    @ApiModelProperty(name = "更新人",notes = "")
    @TableField("update_by_uid")
    private Integer updateByUid ;

    @ApiModelProperty(name = "更新时间",notes = "")
    private LocalDateTime updateTime ;

}
