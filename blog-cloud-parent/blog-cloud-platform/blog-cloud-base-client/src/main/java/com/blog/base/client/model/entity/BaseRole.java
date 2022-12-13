package com.blog.base.client.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

import com.blog.common.mybatis.entity.AbstractEasyFieldEntity;
import com.blog.common.mybatis.entity.AbstractGeneralFieldEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 角色信息表
 * </p>
 *
 * @author 
 * @since 2022-09-16
 */
@Getter
@Setter
@TableName("base_role")
@ApiModel(value = "BaseRole对象", description = "角色信息表")
public class BaseRole extends AbstractEasyFieldEntity implements Serializable  {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("角色ID")
    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("父ID")
    private Integer pId;

    @ApiModelProperty("角色名称")
    private String roleName;

    @ApiModelProperty("角色代码")
    private String roleCode;

    @ApiModelProperty("是否删除 1：是 0： 否")
    @TableLogic
    private Integer isDel;

}
