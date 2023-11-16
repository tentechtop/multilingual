package com.alphay.boot.official.dto;

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
public class ProductFileDTO {

    /**
     * ID 主键
     */
    @ApiModelProperty(name = "文件ID",required = true)
    private Integer fileId;

    @ApiModelProperty(name = "文件ID",required = true)
    private Integer productId;

    @ApiModelProperty("排序字段升序ASC")
    private Integer sort;

    @ApiModelProperty(name = "语言标志",required = true)
    private String lang;

    @ApiModelProperty(name = "分类列表",required = true)
    private List<FileCategoryDTO> categoryDTOList;

    @ApiModelProperty(name = "文件格式",required = true)
    private String fileFormat;

    @ApiModelProperty(name = "文件名称",required = true)
    private String fileName;

    @ApiModelProperty("文件url")
    private String fileUrl;

    @ApiModelProperty("文件描述")
    private String fileDesc;

    @ApiModelProperty("文件内容")
    private String fileContent;
}
