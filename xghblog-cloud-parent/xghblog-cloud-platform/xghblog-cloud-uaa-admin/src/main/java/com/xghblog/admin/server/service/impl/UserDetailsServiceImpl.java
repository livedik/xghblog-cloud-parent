package com.xghblog.admin.server.service.impl;

import com.xghblog.admin.server.bean.OpenAuthority;
import com.xghblog.admin.server.bean.OpenUserDetail;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        OpenUserDetail openUserDetail = new OpenUserDetail();
        openUserDetail.setUserId(111111L);
        openUserDetail.setUsername("admin");
        openUserDetail.setPassword(new BCryptPasswordEncoder().encode("123"));
        openUserDetail.setClientId("c1");
        openUserDetail.setAvatar("hahahaha");
        openUserDetail.setEnabled(true);
        openUserDetail.setNickName("傻逼");
        openUserDetail.setAccountNonExpired(true);
        openUserDetail.setAccountNonLocked(true);
        openUserDetail.setEnabled(true);
        openUserDetail.setCredentialsNonExpired(true);
        List<GrantedAuthority> authorityList = new ArrayList<>();

        authorityList.add(new OpenAuthority("1","a"));
        authorityList.add(new OpenAuthority("2","b"));


        openUserDetail.setAuthorities(authorityList);

        return openUserDetail;
    }
}
