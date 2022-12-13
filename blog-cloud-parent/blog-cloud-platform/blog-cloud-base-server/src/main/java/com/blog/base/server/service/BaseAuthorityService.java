package com.blog.base.server.service;

import com.blog.base.client.model.entity.BaseAuthority;
import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.common.security.OpenAuthority;

import java.util.List;

/**
 * <p>
 * 权限信息表 服务类
 * </p>
 *
 * @author 
 * @since 2022-09-16
 */
public interface BaseAuthorityService extends IService<BaseAuthority> {

    List<OpenAuthority> getAllAuthority();

}
