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

//规格参数
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("off_specs_params")
public class SpecsParams {

    /**
     * ID 主键
     */
    @ApiModelProperty("参数ID")
    @TableId(value = "params_id",type = IdType.AUTO)
    private Integer paramsId;

    @ApiModelProperty("规格ID")
    @TableField(value = "specs_id")
    private Integer specsId;

    @ApiModelProperty("排序字段")
    @TableField(value = "sort")
    private Integer sort;

    @ApiModelProperty("参数标题")
    @TableField(value = "params_title")
    private String paramsTitle;

    @ApiModelProperty("参数值类型")
    @TableField(value = "value_type")
    private String valueType;

    @ApiModelProperty("int类型参数")
    @TableField(value = "number_value")
    private Integer numberValue;

    @ApiModelProperty("字符串类型参数")
    @TableField(value = "string_value")
    private String stringValue;

    @ApiModelProperty(name = "是否可用",notes = "0不可用/1可用")
    @TableField("is_enable")
    private Boolean isEnable ;

    @ApiModelProperty(name = "是否删除",notes = "0不删除/1删除")
    @TableField("is_delete")
    private Boolean isDelete ;

}
