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


/**
 * 常见问题解答与通知公告表
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("off_faq")
public class Faq {

    /**
     * ID 主键
     */
    @ApiModelProperty(name = "常见问题与回答ID",required = true)
    @TableId(value = "faq_id",type = IdType.AUTO)
    private Integer faqId;

    @ApiModelProperty(name = "排序字段")
    @TableField("sort")
    private Integer sort ;

    @ApiModelProperty(name = "问题概述")
    @TableField("faq_title")
    private String faqTitle ;

    @ApiModelProperty(name = "问题详细")
    @TableField("faq_question")
    private String faqQuestion ;

    @ApiModelProperty(name = "问题解答")
    @TableField("faq_answers")
    private String faqAnswers ;

    @ApiModelProperty(name = "点赞数量")
    @TableField("like_number")
    private Integer likeNumber ;

    @ApiModelProperty(name = "是否删除",notes = "0不删除/1删除")
    @TableField("is_delete")
    private Boolean isDelete ;

    @ApiModelProperty(name = "创建时间")
    private LocalDateTime createTime ;

    @ApiModelProperty(name = "更新时间")
    private LocalDateTime updateTime ;

}
