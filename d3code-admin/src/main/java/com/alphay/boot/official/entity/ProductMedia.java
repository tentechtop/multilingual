package com.alphay.boot.official.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

//产品图片资源或视频资源 产品的图片
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("off_product_media")
public class ProductMedia {

    /**
     * ID 主键
     */
    @ApiModelProperty("媒体ID")
    @TableId(value = "media_id",type = IdType.AUTO)
    private Integer mediaId;

    @ApiModelProperty("产品ID")
    @TableField(value = "product_id")
    private Integer productId;

    @ApiModelProperty("资源类型 image / video 两种")
    @TableField(value = "type")
    private String type;

    @ApiModelProperty("排序字段")
    @TableField(value = "sort")
    private Integer sort;

    @ApiModelProperty("图片url地址")
    @TableField(value = "img_url")
    private String imgUrl;

    @ApiModelProperty("视频url地址")
    @TableField(value = "video_url")
    private String videoUrl;

    @ApiModelProperty("资源描述")
    @TableField(value = "alt")
    private String alt;

    @ApiModelProperty(name = "是否可用",notes = "0不可用/1可用")
    @TableField("is_enable")
    private Boolean isEnable ;

    @ApiModelProperty(name = "是否删除",notes = "0不删除/1删除")
    @TableField("is_delete")
    private Boolean isDelete ;

    @ApiModelProperty(name = "创建时间",notes = "")
    private LocalDateTime createTime ;

    @ApiModelProperty(name = "更新时间",notes = "")
    private LocalDateTime updateTime ;
}
