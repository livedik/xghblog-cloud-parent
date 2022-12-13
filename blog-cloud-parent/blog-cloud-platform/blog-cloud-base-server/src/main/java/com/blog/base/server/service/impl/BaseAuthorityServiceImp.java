package com.blog.base.server.service.impl;

import com.blog.base.client.model.entity.BaseAuthority;
import com.blog.base.server.mapper.BaseAuthorityMapper;
import com.blog.base.server.service.BaseAuthorityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.common.security.OpenAuthority;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 权限信息表 服务实现类
 * </p>
 *
 * @author 
 * @since 2022-09-16
 */
@Service
public class BaseAuthorityServiceImp extends ServiceImpl<BaseAuthorityMapper, BaseAuthority> implements BaseAuthorityService {


    @Override
    public List<OpenAuthority> getAllAuthority() {

        List<OpenAuthority> allAuthority = this.getBaseMapper().getAllAuthority();

        return allAuthority;
    }
}
