package com.blog.base.client.model.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * API信息表
 * </p>
 *
 * @author 
 * @since 2022-09-21
 */
@Getter
@Setter
@TableName("base_api")
@ApiModel(value = "BaseApi对象", description = "API信息表")
public class BaseApi implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    private Long apiId;

    @ApiModelProperty("接口描述")
    private String apiDesc;

    @ApiModelProperty("接口名称")
    private String apiName;

    @ApiModelProperty("接口路径")
    private String path;

    @ApiModelProperty("服务ID")
    private String serverId;

    @ApiModelProperty("接口编码")
    private String apiCode;

    @ApiModelProperty("请求方式")
    private String requestMethod;

    @ApiModelProperty("类名")
    private String className;

    @ApiModelProperty("方法名")
    private String methodName;

    @ApiModelProperty("创建时间")
    @ExcelIgnore
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty("修改时间")
    @ExcelIgnore
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModify;

    @ApiModelProperty("是否删除")
    @TableLogic
    private Integer isDel;
}
