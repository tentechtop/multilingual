package com.alphay.boot.official.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SeoDTO {


    private Integer seoId;

    private Integer pageId;

    private String lang;

    private String title;

    private String keywords;

    private String description;


}
