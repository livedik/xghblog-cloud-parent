package com.xghblog.base.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xghblog.base.client.model.entity.BaseAccount;
import com.xghblog.base.server.mapper.BaseAccountMapper;
import com.xghblog.base.server.service.BaseAccountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author xgh
 * @since 2022-08-31
 */
@Slf4j
@Service
public class BaseAccountServiceImp extends ServiceImpl<BaseAccountMapper, BaseAccount> implements BaseAccountService {



    @Override
    public BaseAccount userLogin(String username) {
        QueryWrapper<BaseAccount> wrapper = new QueryWrapper<>();

        wrapper.eq("user_name",username);

        BaseAccount baseAccount = this.baseMapper.selectOne(wrapper);

        return baseAccount;
    }
}
