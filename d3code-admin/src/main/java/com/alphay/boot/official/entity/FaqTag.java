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
@TableName("off_faq_tag")
public class FaqTag {

    /**
     * ID 主键
     */
    @ApiModelProperty(name = "常见问题标签ID",required = true)
    @TableId(value = "faq_tag_id",type = IdType.AUTO)
    private Integer faqTagId;

    @ApiModelProperty(name = "常见问题ID",notes = "",required = true)
    @TableField("faq_id")
    private Integer faqId ;

    @ApiModelProperty(name = "标签ID",notes = "",required = true)
    @TableField("tag_id")
    private Integer tagId ;

}
