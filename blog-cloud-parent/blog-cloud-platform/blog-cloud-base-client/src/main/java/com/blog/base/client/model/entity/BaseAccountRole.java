package com.blog.base.client.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDate;

import com.blog.common.mybatis.entity.AbstractGeneralFieldEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 用户角色信息表
 * </p>
 *
 * @author
 * @since 2022-09-16
 */
@Getter
@Setter
@TableName("base_account_role")
@ApiModel(value = "BaseAccountRole对象", description = "用户角色信息表")
public class BaseAccountRole extends AbstractGeneralFieldEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键ID")
    private Integer id;

    @ApiModelProperty("用户ID")
    private Integer userId;

    @ApiModelProperty("角色代码")
    private Integer roleCode;

    @ApiModelProperty("创建时间")
    private LocalDate gmtCreate;

    @ApiModelProperty("修改时间")
    private LocalDate gmtModify;

    @ApiModelProperty("创建人ID")
    private Integer createId;

    @ApiModelProperty("修改人ID")
    private Integer modifyId;

    @ApiModelProperty("创建人名称")
    private String createName;

    @ApiModelProperty("修改人名称")
    private String modifyName;

    @ApiModelProperty("是否删除 1：是 0： 否")
    private Integer isDel;

    @ApiModelProperty("操作标识")
    private Integer seqId;
}
