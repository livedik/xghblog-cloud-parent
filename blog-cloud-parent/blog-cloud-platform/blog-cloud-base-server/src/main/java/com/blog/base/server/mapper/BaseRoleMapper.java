package com.blog.base.server.mapper;

import com.blog.base.client.model.entity.BaseRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.base.client.model.entity.Role;
import com.blog.base.client.model.entity.RoleGroup;

import java.util.List;

/**
 * <p>
 * 角色信息表 Mapper 接口
 * </p>
 *
 * @author
 * @since 2022-09-16
 */
public interface BaseRoleMapper extends BaseMapper<BaseRole> {

    List<RoleGroup> selectRoleGroups();

    List<Role> selectRoleForGroup(String pid);

    List<String> getUserIdByRoleId(String roleId);

    List<String> getRoleIdForPid(String roleId);

    List<String> getUserIdByRoleIds(List<String> roleIds);
}
