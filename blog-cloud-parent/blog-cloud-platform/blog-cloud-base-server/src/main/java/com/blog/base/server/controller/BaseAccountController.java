package com.blog.base.server.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.blog.base.client.model.entity.BaseAccount;
import com.blog.base.client.model.vo.AdminQueryVo;
import com.blog.base.server.service.BaseAccountService;
import com.blog.common.model.ResultBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * <p>
 * 用户信息表 前端控制器
 * </p>
 * what
 * @author
 * @since 2022-08-31
 */
@Slf4j
@Api(value = "用户信息表",tags = "用户信息表")
@RestController
@RequestMapping("/baseAccount")
public class BaseAccountController{

    @Autowired
    private BaseAccountService baseAccountService;


    /***
     *    管理后台 用户信息表  多条件查询用户信息  仅 超级VVIP 可用
     * @return
     */
    @ApiOperation(value = "管理后台多条件查询用户信息",notes = "管理后台多条件查询用户信息")
    @PostMapping("/getUserInfoQuery")
    public ResultBody getUserInfoQuery(@RequestBody AdminQueryVo query)
    {
        IPage<BaseAccount> pageEntitys = baseAccountService.getUserInfoQuery(query);
        List<BaseAccount> records = pageEntitys.getRecords();
        long total = pageEntitys.getTotal();
        HashMap<String, Object> map = new HashMap<>();
        map.put("list",records);
        map.put("pageTotal",total);
        return ResultBody.ok().data(map);
    }


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
     *  通用根据ID修改
     * @return
     */
    @ApiOperation(value = "通用根据ID修改",notes = "通用根据ID修改")
    @PostMapping("/update")
    public ResultBody update(@RequestBody BaseAccount entity)
    {
        boolean result = baseAccountService.updateById(entity);
        return result?ResultBody.ok():ResultBody.fail();
    }


    /***
     *  根据ID删除
     * @param id
     * @return
     */
    @ApiOperation(value = "根据ID删除",notes = "根据ID删除")
    @DeleteMapping("/remove")
    public ResultBody remove(@RequestParam("id") String id)
    {
        return baseAccountService.removeById(id)?ResultBody.ok().msg("删除成功"):ResultBody.fail().msg("删除失败");
    }

    /***
     *  批量根据ID删除用户
     * @param ids
     * @return
     */
    @ApiOperation(value = "批量根据ID删除用户",notes = "批量根据ID删除用户")
    @PostMapping("/removeBatchByIds")
    public ResultBody removeBatchById(@RequestBody String[] ids)
    {
        List<String> ListId = Arrays.asList(ids);
        return baseAccountService.removeByIds(ListId)?ResultBody.ok().msg("删除成功"):ResultBody.fail().msg("删除失败");
    }

    /***
     * 用户登录校验
     * @param username  用户名
     * @return
     */
    @ApiOperation(value = "用户校验",notes = "用户校验")
    @PostMapping("/userLogin")
    public ResultBody<BaseAccount> userLogin(@RequestParam String username)
    {
        BaseAccount baseAccount = baseAccountService.userLogin(username);
        return ResultBody.ok().data(baseAccount);
    }














}
