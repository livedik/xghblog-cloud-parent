package com.blog.common.security;

import org.springframework.security.core.GrantedAuthority;

public class OpenAuthority implements GrantedAuthority {

    private static final long serialVersionUID = -4682269495406460314L;

    /**
     * 权限Id
     */
    private String authorityId;

    public String getAuthorityId() {
        return authorityId;
    }

    public void setAuthorityId(String authorityId) {
        this.authorityId = authorityId;
    }

    public OpenAuthority() {
    }

    public OpenAuthority(String authorityId) {
        this.authorityId = authorityId;
    }

    @Override
    public String getAuthority() {
        return this.authorityId;
    }

}
