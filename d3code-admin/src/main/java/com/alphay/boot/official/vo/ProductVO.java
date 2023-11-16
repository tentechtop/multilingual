package com.alphay.boot.official.vo;

import com.alphay.boot.official.dto.ProductCategoryDTO;
import com.alphay.boot.official.dto.ProductPriceDTO;
import com.alphay.boot.official.dto.ProductSeriesDTO;
import com.alphay.boot.official.dto.ProductTagDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "产品")
public class ProductVO {

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

    @ApiModelProperty("产品预览图")
    private String imgUrl;

    @ApiModelProperty("是否可用")
    private Boolean isEnable ;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime ;

    @ApiModelProperty("更新时间")
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
