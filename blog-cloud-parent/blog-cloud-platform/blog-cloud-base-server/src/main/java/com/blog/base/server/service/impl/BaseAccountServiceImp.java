package com.blog.base.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.base.client.model.entity.BaseAccount;
import com.blog.base.client.model.vo.AdminQueryVo;
import com.blog.base.server.mapper.BaseAccountMapper;
import com.blog.base.server.service.BaseAccountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.base.server.service.BaseAuthorityService;
import com.blog.common.security.OpenAuthority;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author
 * @since 2022-08-31
 */
@Slf4j
@Service
public class BaseAccountServiceImp extends ServiceImpl<BaseAccountMapper, BaseAccount> implements BaseAccountService {

    @Autowired
    private BaseAuthorityService baseAuthorityService;

    @Override
    public BaseAccount userLogin(String username) {
        QueryWrapper<BaseAccount> wrapper = new QueryWrapper<>();

        wrapper.eq("user_name",username);


        BaseAccount baseAccount = this.baseMapper.selectOne(wrapper);
        if (baseAccount==null)return null;

//        判断是否为超级管理员
        if (baseAccount.getId()=="1")
        {
//       超级管理员直接满权限
            List<OpenAuthority> allAuthority = baseAuthorityService.getAllAuthority();
            baseAccount.setOpenAuthoritys(allAuthority);
        }

        return baseAccount;
    }

    @Override
    public IPage<BaseAccount> getUserInfoQuery(AdminQueryVo query) {
        String name = query.getName(); //用户名
        String phone = query.getPhone(); // 手机
        String roleCode = query.getRoleCode()==null?"":query.getRoleCode(); //用户类型
        Date registerDate = query.getRegisterDate(); //注册时间

        QueryWrapper<BaseAccount> queryWrapper = new QueryWrapper<>();

        if (!"".equals(name))queryWrapper.like("user_name",name);
        if (!"".equals(phone))queryWrapper.like("phone",phone);
        if (!"".equals(roleCode))queryWrapper.eq("role_code",roleCode);
        if (registerDate!=null)queryWrapper.eq("gmt_create",registerDate);

        Page<BaseAccount> page = new Page<>();
        page.setSize(query.getPageSize());
        page.setCurrent(query.getPageIndex());

        IPage<BaseAccount> baseAccountIPage = this.getBaseMapper().selectPage(page, queryWrapper);
        return baseAccountIPage;
    }
}
