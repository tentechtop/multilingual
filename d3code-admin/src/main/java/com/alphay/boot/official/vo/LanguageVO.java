package com.alphay.boot.official.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "语言")
public class LanguageVO {

    /**
     * languageId
     */
    private Integer languageId;

    /**
     * 语言标志
     */
    private String lang;

    /**
     * 英文名称
     */
    private String enName;

    /**
     * 方言
     */
    private String dialect;

    /**
     * 旗帜
     */
    private String flagImgUrl;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 是否可用
     */
    private Boolean isEnable ;

    /**
     * 是否删除
     */
    private Boolean isDelete ;

    /**
     * 创建时间
     */
    private LocalDateTime createTime ;

}
