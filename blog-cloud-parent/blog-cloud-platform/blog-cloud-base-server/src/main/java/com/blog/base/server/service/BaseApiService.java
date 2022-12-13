package com.blog.base.server.service;

import com.blog.base.client.model.entity.BaseApi;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 权限信息表 服务类
 * </p>
 *
 * @author 
 * @since 2022-09-21
 */
public interface BaseApiService extends IService<BaseApi> {

    BaseApi getApi(String apiCode);

}
