package com.blog.base.client.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 系统权限信息表
 * </p>
 *
 * @author
 * @since 2022-09-16
 */
@Getter
@Setter
@TableName("base_authority")
@ApiModel(value = "BaseAuthority对象", description = "系统权限信息表")
public class BaseAuthority  implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    private Long authorityId;

    @ApiModelProperty("接口ID")
    private Long apiId;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty("修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModify;

    @ApiModelProperty("状态（0：禁用 1：激活）")
    private Integer status;
}
