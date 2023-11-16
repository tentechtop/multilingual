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
@TableName("off_faq_category")
public class FaqCategory {


    /**
     * ID 主键
     */
    @ApiModelProperty(name = "常见问题与回答分类ID",required = true)
    @TableId(value = "faq_category_id",type = IdType.AUTO)
    private Integer faqCategoryId;

    @ApiModelProperty(name = "常见问题ID",notes = "",required = true)
    @TableField("faq_id")
    private Integer faqId ;

    @ApiModelProperty(name = "分类ID",notes = "",required = true)
    @TableField("category_id")
    private Integer categoryId ;


}
