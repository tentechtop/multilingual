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
@TableName("off_tag")
public class Tag {

    /**
     * ID 主键
     */
    @ApiModelProperty("标签ID")
    @TableId(value = "tag_id",type = IdType.AUTO)
    private Integer tagId;

    @ApiModelProperty(name = "标签名称",notes = "国际化字段")
    @TableField("tag_name")
    private String tagName ;

    @ApiModelProperty(name = "标签类型",notes = "")
    @TableField("tag_type")
    private Integer tagType ;

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
