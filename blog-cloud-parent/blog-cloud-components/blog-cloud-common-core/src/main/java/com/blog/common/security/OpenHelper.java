package com.blog.common.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;


/***
 * 用户认证辅助类
 */
public class OpenHelper {


    /**
     * 获取认证用户信息
     *
     * @return
     */
    public static OpenUserDetail getUser()
    {
//        获取当前用户认证信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        非空 && 是否认证过 && 是否 Oauth2.0 认证
        if (authentication!=null && authentication.isAuthenticated() && authentication instanceof OAuth2Authentication)
        {
            OAuth2Authentication auth2Authentication = (OAuth2Authentication)authentication;
            OAuth2Request clientToken = auth2Authentication.getOAuth2Request();
            if(auth2Authentication!=null)
            {
                OpenUserDetail openUserDetail = new OpenUserDetail();
                openUserDetail.setUsername(auth2Authentication.getName());
                openUserDetail.setAuthorities(auth2Authentication.getAuthorities());
                openUserDetail.setClientId(clientToken.getClientId());
                return openUserDetail;
            }
        }
        return null;
    }
}
