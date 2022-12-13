package com.blog.base.server.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.blog.base.client.model.entity.BaseAccount;
import com.blog.base.client.model.entity.BaseRole;
import com.blog.base.client.model.entity.Role;
import com.blog.base.client.model.entity.RoleGroup;
import com.blog.base.server.mapper.BaseRoleMapper;
import com.blog.base.server.service.BaseAccountService;
import com.blog.base.server.service.BaseRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 角色信息表 服务实现类
 * </p>
 *
 * @author 
 * @since 2022-09-16
 */
@Service
public class BaseRoleServiceImp extends ServiceImpl<BaseRoleMapper, BaseRole> implements BaseRoleService {

    @Autowired
    private BaseAccountService baseAccountService;

    @Override
    public List<RoleGroup> selectRoleAuth() {
        List<RoleGroup> roleGroups = this.getBaseMapper().selectRoleGroups();

        for (RoleGroup roleGroup : roleGroups) {
            List<Role> roles = this.getBaseMapper().selectRoleForGroup(roleGroup.getId());
            roleGroup.setChildren(roles);
        }
        return roleGroups;
    }


    @Override
    public List<BaseAccount> selectUserForRole(String roleId,String level) {

        QueryWrapper<BaseAccount> queryWrapper = new QueryWrapper<>();
        if ("1".equals(level))
        {
            List<String> roleIds = this.getBaseMapper().getRoleIdForPid(roleId);
            List<String> userIdList = this.getBaseMapper().getUserIdByRoleIds(roleIds);
            if(userIdList.size()==0) return null;
            queryWrapper.in("user_id",userIdList);
            List<BaseAccount> userList = baseAccountService.getBaseMapper().selectList(queryWrapper);
            return userList;
        }
        else {
            List<String> userIdList = this.getBaseMapper().getUserIdByRoleId(roleId);
            if(userIdList.size()==0) return null;
            queryWrapper.in("user_id",userIdList);
            List<BaseAccount> userList = baseAccountService.getBaseMapper().selectList(queryWrapper);
            return userList;
        }

    }
}
