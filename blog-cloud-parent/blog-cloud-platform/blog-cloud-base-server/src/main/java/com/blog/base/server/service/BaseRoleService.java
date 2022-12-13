package com.blog.base.server.service;

import com.blog.base.client.model.entity.BaseAccount;
import com.blog.base.client.model.entity.BaseRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.base.client.model.entity.RoleGroup;

import java.util.List;

/**
 * <p>
 * 角色信息表 服务类
 * </p>
 *
 * @author 
 * @since 2022-09-16
 */
public interface BaseRoleService extends IService<BaseRole> {

    List<RoleGroup> selectRoleAuth();

    List<BaseAccount> selectUserForRole(String roleId,String level);

}
