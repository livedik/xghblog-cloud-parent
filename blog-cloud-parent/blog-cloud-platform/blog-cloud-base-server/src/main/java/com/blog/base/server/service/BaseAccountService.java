package com.blog.base.server.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.blog.base.client.model.entity.BaseAccount;
import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.base.client.model.vo.AdminQueryVo;

import java.util.List;

/**
 * <p>
 * 用户信息表 服务类
 * </p>
 *
 * @author
 * @since 2022-08-31
 */
public interface BaseAccountService extends IService<BaseAccount> {

    BaseAccount userLogin(String username);

    IPage<BaseAccount> getUserInfoQuery(AdminQueryVo query);

}
