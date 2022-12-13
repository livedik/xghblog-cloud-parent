package com.blog.base.client.model.vo;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 后台管理员 用户信息表 查询条件 VO
 */
@Getter
@Setter
@ApiModel(value = "用户信息表查询条件", description = "用户信息表查询条件")
public class AdminQueryVo {

//    用户名
    private String name;

//    手机号
    private String phone;

//    用户类型
    private String roleCode;

//    注册时间  yyyy-MM-dd
    private Date registerDate;

    private Integer pageSize;

    private Integer pageIndex;



}
