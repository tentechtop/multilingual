package com.alphay.boot.official.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 产品管理列表的
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    @ApiModelProperty("产品ID")
    private Integer productId;

    @ApiModelProperty("语言标志")
    private String lang;

    @ApiModelProperty("产品型号")
    private String productModel;

    @ApiModelProperty("产品名称")
    private String productName;

    @ApiModelProperty("产品昵称")
    private String productNickname;

    @ApiModelProperty("产品描述")
    private String productDesc;

    @ApiModelProperty("排序字段")
    private Integer sort;

    @ApiModelProperty(name = "产品预览图",notes = "")
    private String imgUrl;

    @ApiModelProperty(name = "是否可用",notes = "0不可用/1可用")
    private Boolean isEnable ;

    @ApiModelProperty(name = "创建时间",notes = "")
    private LocalDateTime createTime ;

    @ApiModelProperty(name = "更新时间",notes = "")
    private LocalDateTime updateTime ;

    @ApiModelProperty("产品分类")
    private List<ProductCategoryDTO> productCategoryDTOList;

    @ApiModelProperty("产品系列")
    private List<ProductSeriesDTO> productSeriesDTOList;

    @ApiModelProperty("产品系列")
    private List<ProductTagDTO> productTagDTOList;

    @ApiModelProperty("产品价格")
    private ProductPriceDTO productPriceDTO ;


}
