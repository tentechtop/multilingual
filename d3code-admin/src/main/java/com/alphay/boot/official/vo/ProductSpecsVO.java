package com.alphay.boot.official.vo;

import com.alphay.boot.official.dto.SpecsParamsDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductSpecsVO {

    /**
     * ID 主键
     */
    @ApiModelProperty("规格ID")
    private Integer specsId;

    @ApiModelProperty("产品ID")
    private Integer productId;

    @ApiModelProperty("语言标志")
    private String lang;

    @ApiModelProperty("排序字段")
    private Integer sort;

    @ApiModelProperty("规格名称")
    private String specsName;

    @ApiModelProperty("规格图片")
    private String imgUrl;

    @ApiModelProperty("规格图片")
    private List<SpecsParamsDTO> paramsDTOList;
}
