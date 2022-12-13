package com.blog.services.demoSpring.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.blog.services.demoSpring.mapper.UserMapper;
import com.blog.services.demoSpring.pojo.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("userDetailsService")
public class MyUserDetails implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        QueryWrapper<Users> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username",username);
        Users users = userMapper.selectOne(userQueryWrapper);

        if (users==null)
        {
            System.out.println("验证失败！");
        }
        System.out.println(users);
        List<GrantedAuthority>  authList = AuthorityUtils.commaSeparatedStringToAuthorityList("admin");
        return new User(username,new BCryptPasswordEncoder().encode(users.getPassword()),authList);
    }
}
