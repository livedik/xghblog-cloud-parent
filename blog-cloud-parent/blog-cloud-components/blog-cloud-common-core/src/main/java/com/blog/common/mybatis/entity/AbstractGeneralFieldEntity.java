package com.blog.common.mybatis.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.time.LocalDate;


public abstract class AbstractGeneralFieldEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("创建时间")
    @ExcelIgnore
    @TableField(fill = FieldFill.INSERT)
    private LocalDate gmtCreate;

    @ApiModelProperty("修改时间")
    @ExcelIgnore
    @TableField(fill = FieldFill.UPDATE)
    private LocalDate gmtModify;

    @ApiModelProperty("创建人名称")
    @ExcelIgnore
    private String createName;

    @ApiModelProperty("修改人名称")
    @ExcelIgnore
    private String modifyName;

    @ApiModelProperty("创建人ID")
    @ExcelIgnore
    @TableField("create_id")
    private Long createId;

    @ApiModelProperty("修改人ID")
    @ExcelIgnore
    @TableField("modify_id")
    private Long modifyId;

    @ApiModelProperty("是否删除")
    @TableLogic
    private Integer isDel;



}
