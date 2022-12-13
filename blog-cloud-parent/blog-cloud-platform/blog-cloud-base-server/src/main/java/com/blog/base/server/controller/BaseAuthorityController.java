package com.blog.base.server.controller;

import com.blog.base.server.service.BaseAuthorityService;
import com.blog.common.model.ResultBody;
import com.blog.common.security.OpenAuthority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 权限信息表 前端控制器
 * </p>
 *
 * @author
 * @since 2022-09-16
 */
@RestController
@RequestMapping("/baseAuthority")
public class BaseAuthorityController {

    @Autowired
    private BaseAuthorityService baseAuthorityService;


    /***
     * 获取资源下的所有权限 仅内部用
     * @return
     */
    @GetMapping("/getAllAuthority")
    public ResultBody getAllAuthority()
    {
        List<OpenAuthority> authorityList = baseAuthorityService.getAllAuthority();
        return ResultBody.ok().data(authorityList);
    }

}
