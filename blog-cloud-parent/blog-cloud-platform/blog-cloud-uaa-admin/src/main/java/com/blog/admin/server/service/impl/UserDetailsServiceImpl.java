package com.blog.admin.server.service.impl;

import com.blog.admin.server.service.feign.BaseAccountServiceClient;
import com.blog.base.client.model.entity.BaseAccount;
import com.blog.common.model.ResultBody;
import com.blog.common.security.OpenAuthority;
import com.blog.common.security.OpenUserDetail;
import org.springframework.beans.factory.annotation.Autowired;
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
        openUserDetail.setUserId(Long.valueOf(account.getId()));  // 用户ID
        openUserDetail.setUsername(account.getUserName());
        openUserDetail.setPassword(new BCryptPasswordEncoder().encode(account.getPassword()));
        openUserDetail.setClientId("c1");
        openUserDetail.setEnabled(true);
        openUserDetail.setNickName(account.getUserNickname());
        openUserDetail.setAccountNonExpired(true);
        openUserDetail.setAccountNonLocked(true);
        openUserDetail.setEnabled(true);
        openUserDetail.setCredentialsNonExpired(true);

        List<OpenAuthority> authorities = new ArrayList<>();
        OpenAuthority openAuthority = new OpenAuthority();
        openAuthority.setAuthorityId("123456");
        authorities.add(openAuthority);

//        openUserDetail.setAuthorities(account.getOpenAuthoritys());
        openUserDetail.setAuthorities(authorities);

        return openUserDetail;
    }
}
