package com.blog.base.client.service;

import com.blog.base.client.model.entity.BaseAccount;
import com.blog.common.model.ResultBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface IBaseAccountServiceClient {

    /***
     * 用户登录校验
     * @param username
     * @return
     */
    @PostMapping("/baseAccount/userLogin")
    ResultBody<BaseAccount> userLogin(@RequestParam("username") String username);
}
