package com.xghblog.base.client.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.xhgblog.common.mybatis.entity.AbstractGeneralFieldEntity;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 用户信息表
 * </p>
 *
 * @author xgh
 * @since 2022-08-31
 */
@Getter
@Setter
@TableName("base_account")
@ApiModel(value = "BaseAccount对象", description = "用户信息表")
public class BaseAccount extends AbstractGeneralFieldEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键ID")
    private Integer id;

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("昵称")
    private String userNickname;

    @ApiModelProperty("密码凭证")
    private String password;
}
