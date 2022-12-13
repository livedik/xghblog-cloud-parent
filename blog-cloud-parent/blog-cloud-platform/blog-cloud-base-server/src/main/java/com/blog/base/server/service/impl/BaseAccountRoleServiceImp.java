package com.blog.base.server.service.impl;

import com.blog.base.client.model.entity.BaseAccountRole;
import com.blog.base.server.mapper.BaseAccountRoleMapper;
import com.blog.base.server.service.BaseAccountRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户角色信息表 服务实现类
 * </p>
 *
 * @author
 * @since 2022-09-16
 */
@Service
public class BaseAccountRoleServiceImp extends ServiceImpl<BaseAccountRoleMapper, BaseAccountRole> implements BaseAccountRoleService {

}
