package com.xghblog.admin.server.service.impl;

import com.xghblog.admin.server.bean.OpenAuthority;
import com.xghblog.admin.server.bean.OpenUserDetail;
import com.xghblog.admin.server.service.feign.BaseAccountServiceClient;
import com.xghblog.base.client.model.entity.BaseAccount;
import com.xhgblog.common.model.ResultBody;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private BaseAccountServiceClient baseAccountServiceClient;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//
        ResultBody<BaseAccount> resultBody = baseAccountServiceClient.userLogin(username);
        BaseAccount account = resultBody.getData();

        OpenUserDetail openUserDetail = new OpenUserDetail();
        openUserDetail.setUserId(111111L);
//        openUserDetail.setUsername("admin");
        openUserDetail.setUsername(account.getUserName());
        openUserDetail.setPassword(new BCryptPasswordEncoder().encode(account.getPassword()));
//        openUserDetail.setPassword(new BCryptPasswordEncoder().encode("123456"));
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
