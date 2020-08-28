package com.common.dove.oauth2.base.principal;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Description:
 * @Auther: qingruizhu
 * @Date: 2020/8/13 11:20
 */
public class OauthUser extends User {

    private Long user_id;
    private String user_id_str;
    private String user_mobile;
    //拓展字段1
    private String ext_1;
    //拓展字段2
    private String ext_2;
    //拓展字段3
    private String ext_3;

    private Set<String> roles;


    public OauthUser(String username, String password, Set<String> roles) {
        this(username, password, roles.stream().map(value -> {
            return new SimpleGrantedAuthority(value);
        }).collect(Collectors.toList()));
    }

    public OauthUser(Long user_id, String user_id_str, String user_mobile, String username, String password, Set<String> roles) {
        this(username, password, roles);
        this.user_id = user_id;
        this.user_id_str = user_id_str;
        this.user_mobile = user_mobile;
    }

    public OauthUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        if (CollectionUtils.isNotEmpty(authorities)) {
            this.roles = authorities.stream().map(authority -> {
                return authority.getAuthority();
            }).collect(Collectors.toSet());
        }
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getUser_id_str() {
        return user_id_str;
    }

    public void setUser_id_str(String user_id_str) {
        this.user_id_str = user_id_str;
    }

    public String getUser_mobile() {
        return user_mobile;
    }

    public void setUser_mobile(String user_mobile) {
        this.user_mobile = user_mobile;
    }

    public String getExt_1() {
        return ext_1;
    }

    public void setExt_1(String ext_1) {
        this.ext_1 = ext_1;
    }

    public String getExt_2() {
        return ext_2;
    }

    public void setExt_2(String ext_2) {
        this.ext_2 = ext_2;
    }

    public String getExt_3() {
        return ext_3;
    }

    public void setExt_3(String ext_3) {
        this.ext_3 = ext_3;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
