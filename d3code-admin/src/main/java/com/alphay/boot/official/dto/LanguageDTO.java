package com.alphay.boot.official.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LanguageDTO {

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
     * 旗帜
     */
    private Integer sort;

    /**
     * 是否可用
     */
    private Integer isEnable ;


    /**
     * 是否删除
     */
    private Integer isDelete ;


    /**
     * 创建时间
     */
    private LocalDateTime createTime ;


}
