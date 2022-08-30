package com.xghblog.admin.server.bean;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

import java.util.Date;

public class OpenAuthority implements GrantedAuthority {

    private static final long serialVersionUID = -4682269495406460314L;

    /**
     * 权限Id
     */
    private String authorityId;
    /**
     * 权限标识
     */
    private String authority;


    public OpenAuthority() {
    }

    public OpenAuthority(String authority) {
        Assert.hasText(authority, "A granted authority textual representation is required");
        this.authority = authority;
    }

    public OpenAuthority(String authority, Date expireTime) {
        Assert.hasText(authority, "A granted authority textual representation is required");
        this.authority = authority;
    }

    public OpenAuthority(String authorityId, String authority) {
        this.authorityId = authorityId;
        this.authority = authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }



    @Override
    public String getAuthority() {
        return this.authority;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else {
            return obj instanceof OpenAuthority ? this.authority.equals(((OpenAuthority) obj).authority) : false;
        }
    }

    @Override
    public int hashCode() {
        return this.authority.hashCode();
    }

    @Override
    public String toString() {
        return this.authority;
    }

    public String getAuthorityId() {
        return authorityId;
    }

    public void setAuthorityId(String authorityId) {
        this.authorityId = authorityId;
    }

}
