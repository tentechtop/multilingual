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

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("off_product_file")
public class ProductFile {

    /**
     * ID 主键
     */
    @ApiModelProperty("产品ID")
    @TableId(value = "product_file_id",type = IdType.AUTO)
    private Integer productFileId;

    /**
     * ID 主键
     */
    @ApiModelProperty("产品ID")
    @TableField(value = "product_id")
    private Integer productId;

    /**
     * ID 主键
     */
    @ApiModelProperty("文件ID")
    @TableField(value = "file_id")
    private Integer fileId;
}
