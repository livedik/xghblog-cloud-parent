package com.blog.base.server.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.blog.base.client.model.entity.BaseAccount;
import com.blog.base.client.model.entity.BaseRole;
import com.blog.base.client.model.entity.RoleGroup;
import com.blog.base.server.service.BaseRoleService;
import com.blog.common.model.ResultBody;
import com.blog.common.utils.CommonUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 角色信息表 前端控制器
 * </p>
 *
 * @author 
 * @since 2022-09-16
 */
@RestController
@RequestMapping("/baseRole")
public class BaseRoleController {

    @Autowired
    private BaseRoleService baseRoleService;

    @ApiOperation(value = "下拉角色选择",notes = "下拉角色选择")
    @GetMapping("/selectRoles")
    public ResultBody selectRoles()
    {
        QueryWrapper<BaseRole> queryWrapper = new QueryWrapper<BaseRole>().select("role_name","role_code");
        List<BaseRole> roles = baseRoleService.getBaseMapper().selectList(queryWrapper);

        return ResultBody.ok().data(roles);
    }


    @ApiOperation(value = "角色权限级联查询",notes = "角色权限级联查询")
    @GetMapping("/selectRoleGroups")
    public ResultBody selectRoleAuth()
    {
        List<RoleGroup> roleGs = baseRoleService.selectRoleAuth();
        return ResultBody.ok().data(roleGs);
    }

    @ApiOperation(value = "新增角色组",notes = "新增角色组")
    @PostMapping("/addRoleGroup")
    public ResultBody addRoleGroup(@RequestParam("name") String gName)
    {
        BaseRole entity = new BaseRole();
        entity.setRoleName(gName);
        String uuid = CommonUtils.getUUID();
        entity.setRoleCode(uuid);
        boolean saveResult = baseRoleService.save(entity);
        return saveResult==true?ResultBody.ok():ResultBody.fail().msg("添加角色组失败,角色组可能已存在！请重试");
    }

    @ApiOperation(value = "添加角色",notes = "添加角色")
    @PostMapping("/addRole")
    public ResultBody addRole(@RequestParam("pid") String pid,
                              @RequestParam("name") String gName)
    {
        BaseRole entity = new BaseRole();
        entity.setRoleName(gName);
        String uuid = CommonUtils.getUUID();
        entity.setRoleCode(uuid);
        entity.setPId(Integer.valueOf(pid));
        boolean saveResult = baseRoleService.save(entity);
        return saveResult==true?ResultBody.ok():ResultBody.fail().msg("添加角色失败,角色名称或编码已存在！请重试");
    }

    @ApiOperation(value = "修改角色组 or 角色名称",notes = "修改角色组 or 角色名称")
    @PostMapping("/updateName")
    public ResultBody updateName(@RequestParam("id") String id,
                              @RequestParam("name") String Name)
    {
        BaseRole entity = new BaseRole();
        entity.setId(Integer.valueOf(id));
        entity.setRoleName(Name);
        boolean saveResult = baseRoleService.updateById(entity);
        return saveResult==true?ResultBody.ok():ResultBody.fail().msg("修改角色组名称失败,名称或已存在！请重试");
    }

    /***
     * 查询角色用户列表
     * @param roleId
     * @param level  2 为角色  1 为角色组   level为1时 需查询角色组下所有角色
     * @return
     */
    @ApiOperation(value = "查询角色用户列表",notes = "查询角色用户列表")
    @GetMapping("/selectUserForRole")
    public ResultBody selectUserForRole(@RequestParam("roleId") String roleId,@RequestParam("level") String level)
    {

        List<BaseAccount> baseAccountList = baseRoleService.selectUserForRole(roleId,level);

        return ResultBody.ok().data(baseAccountList);
    }
}
