package com.blog.base.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.blog.base.client.model.entity.BaseApi;
import com.blog.base.server.mapper.BaseApiMapper;
import com.blog.base.server.service.BaseApiService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 权限信息表 服务实现类
 * </p>
 *
 * @author 
 * @since 2022-09-21
 */
@Service
public class BaseApiServiceImp extends ServiceImpl<BaseApiMapper, BaseApi> implements BaseApiService {

    @Override
    public BaseApi getApi(String apiCode) {
//        创建查询条件
        QueryWrapper<BaseApi> baseApiQueryWrapper = new QueryWrapper<>();
        baseApiQueryWrapper.eq("api_code",apiCode);
        return this.getOne(baseApiQueryWrapper);
    }
}
