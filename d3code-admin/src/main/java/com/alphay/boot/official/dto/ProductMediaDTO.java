package com.alphay.boot.official.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductMediaDTO {


    /**
     * ID 主键
     */
    @ApiModelProperty("媒体ID")
    private Integer mediaId;

    @ApiModelProperty("产品ID")
    private Integer productId;

    @ApiModelProperty("语言标志")
    @TableField(value = "lang")
    private String lang;

    @ApiModelProperty("资源类型 image / video 两种")
    private String type;

    @ApiModelProperty("排序字段")
    private Integer sort;

    @ApiModelProperty("图片url地址")
    private String imgUrl;

    @ApiModelProperty("视频url地址")
    private String videoUrl;

    @ApiModelProperty("资源描述")
    private String alt;

}
