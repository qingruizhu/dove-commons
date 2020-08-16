package com.dove.common.oauth2.principal;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * @Description:
 * @Auther: qingruizhu
 * @Date: 2020/8/13 11:20
 */
public class OauthUser extends User {

    private Long id;
    private String idStr;
    private String mobile;


    public OauthUser(Long id, String mobile, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.id = id;
        this.mobile = mobile;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setPhone(String mobile) {
        this.mobile = mobile;
    }

    public String getIdStr() {
        return idStr;
    }

    public void setIdStr(String idStr) {
        this.idStr = idStr;
    }
}