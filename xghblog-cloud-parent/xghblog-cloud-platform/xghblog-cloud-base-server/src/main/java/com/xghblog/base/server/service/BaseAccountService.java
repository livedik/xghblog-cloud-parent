package com.xghblog.base.server.service;

import com.xghblog.base.client.model.entity.BaseAccount;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户信息表 服务类
 * </p>
 *
 * @author xgh
 * @since 2022-08-31
 */
public interface BaseAccountService extends IService<BaseAccount> {

    BaseAccount userLogin(String username);
}
