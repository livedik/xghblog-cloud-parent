package com.blog.base.client.model.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.baomidou.mybatisplus.annotation.*;
import com.blog.common.mybatis.entity.AbstractGeneralFieldEntity;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.blog.common.security.OpenAuthority;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.poi.ss.usermodel.DataFormat;

/**
 * <p>
 * 用户信息表
 * </p>
 *
 * @author 
 * @since 2022-08-31
 */
@Getter
@Setter
@TableName("base_account")
@ApiModel(value = "BaseAccount对象", description = "用户信息表")
public class BaseAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键ID")
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("用户ID")
    private String userId;

    @ApiModelProperty("昵称")
    private String userNickname;

    @ApiModelProperty("密码凭证")
    private String password;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("角色")
    private String roleCode;

    @ApiModelProperty("状态") //1:正常  2:禁用
    private Long status;

    @ApiModelProperty("是否删除")
    @TableLogic
    private Integer isDel;

    @ApiModelProperty("注册时间")
    @ExcelIgnore
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date gmtCreate;

    @ApiModelProperty("修改时间")
    @ExcelIgnore
    @TableField(fill = FieldFill.UPDATE)
    private Date gmtModify;

    @TableField(exist = false)
    private List<OpenAuthority> openAuthoritys = new ArrayList<>();
}
