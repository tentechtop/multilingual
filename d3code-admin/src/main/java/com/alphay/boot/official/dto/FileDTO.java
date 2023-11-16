package com.alphay.boot.official.dto;

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
public class FileDTO {

    /**
     * ID 主键
     */
    @ApiModelProperty(name = "文件ID",required = true)
    private Integer fileId;

    @ApiModelProperty(name = "语言标志",required = true)
    private String lang;

    @ApiModelProperty("排序字段升序ASC")
    private Integer sort ;

    @ApiModelProperty(name = "分类列表",required = true)
    private List<CategoryDTO> categoryDTOList;

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

    @ApiModelProperty(name = "是否可用",notes = "0不可用/1可用")
    private Boolean isEnable ;

    @ApiModelProperty(name = "是否删除",notes = "0不删除/1删除")
    private Boolean isDelete ;

    @ApiModelProperty(name = "创建人ID",notes = "")
    private Integer createByUid ;

    @ApiModelProperty(name = "创建时间",notes = "")
    private LocalDateTime createTime ;

    @ApiModelProperty(name = "更新人",notes = "")
    private Integer updateByUid ;

    @ApiModelProperty(name = "更新时间",notes = "")
    private LocalDateTime updateTime ;
}
