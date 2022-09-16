package com.xghblog.base.server.controller;

import com.xghblog.base.client.model.entity.BaseAccount;
import com.xghblog.base.client.service.IBaseAccountServiceClient;
import com.xghblog.base.server.service.BaseAccountService;
import com.xhgblog.common.model.ResultBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户信息表 前端控制器
 * </p>
 *
 * @author xgh
 * @since 2022-08-31
 */
@Slf4j
@Api(value = "用户信息表",tags = "用户信息表")
@RestController
public class BaseAccountController{

    @Autowired
    private BaseAccountService baseAccountService;


    /***
     *  根据ID查询
     * @param id
     * @return
     */
    @ApiOperation(value = "根据ID查询",notes = "根据ID查询")
    @GetMapping("/get/{id}")
    public ResultBody get(@PathVariable("id") Long id)
    {
        BaseAccount entity = baseAccountService.getById(id);
        return ResultBody.ok().data(entity);
    }


    /***
     *  根据ID删除
     * @param id
     * @return
     */
    @ApiOperation(value = "根据ID删除",notes = "根据ID删除")
    @GetMapping("/remove/{id}")
    public ResultBody remove(@PathVariable("id") Long id)
    {

        return baseAccountService.removeById(id)?ResultBody.ok().msg("删除成功"):ResultBody.fail().msg("删除失败");
    }

    /***
     * 用户登录校验
     * @param username  用户名
     * @return
     */
    @ApiOperation(value = "用户校验",notes = "用户校验")
    @PostMapping("/baseAccount/userLogin")
    public ResultBody<BaseAccount> userLogin(@RequestParam String username)
    {
        BaseAccount baseAccount = baseAccountService.userLogin(username);
        return ResultBody.ok().data(baseAccount);
    }

}
