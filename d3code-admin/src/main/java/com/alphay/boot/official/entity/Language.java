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
@TableName("off_language")
public class Language {

    /**
     * ID 主键
     */
    @ApiModelProperty("语言ID")
    @TableId(value = "language_id",type = IdType.AUTO)
    private Integer languageId;

    @ApiModelProperty("语言标志")
    @TableField(value = "lang")
    private String lang;

    @ApiModelProperty("语言英文名称")
    @TableField(value = "en_name")
    private String enName;

    @ApiModelProperty("语言方言")
    @TableField(value = "dialect")
    private String dialect;

    @ApiModelProperty("语言旗帜")
    @TableField(value = "flag_img_url")
    private String flagImgUrl;

    @ApiModelProperty(name = "排序",notes = "")
    @TableField("sort")
    private Integer sort ;

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
