package com.blog.base.server.mapper;

import com.blog.base.client.model.entity.BaseAuthority;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.common.security.OpenAuthority;

import java.util.List;

/**
 * <p>
 * 权限信息表 Mapper 接口
 * </p>
 *
 * @author 
 * @since 2022-09-16
 */
public interface BaseAuthorityMapper extends BaseMapper<BaseAuthority> {

    List<OpenAuthority> getAllAuthority();

}
