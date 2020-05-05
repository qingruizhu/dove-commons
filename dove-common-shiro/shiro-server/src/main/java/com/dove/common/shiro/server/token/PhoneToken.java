package com.dove.common.shiro.server.token;

import org.apache.shiro.authc.HostAuthenticationToken;
import org.apache.shiro.authc.RememberMeAuthenticationToken;

public class PhoneToken implements HostAuthenticationToken, RememberMeAuthenticationToken {
    private String phone;
    private boolean rememberMe;
    private String host;

    public PhoneToken() {
        this.rememberMe = false;
    }

    public PhoneToken(String phone) {
        this(phone, false, null);
    }

    public PhoneToken(String phone, String host) {
        this(phone, false, host);
    }


    public PhoneToken(String phone, boolean rememberMe) {
        this(phone, rememberMe, null);
    }


    public PhoneToken(String phone, boolean rememberMe, String host) {
        this.rememberMe = false;
        this.phone = phone;
        this.rememberMe = rememberMe;
        this.host = host;
    }


    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Object getPrincipal() {
        return this.getPhone();
    }

    public Object getCredentials() {
        return this.getPhone();
    }

    public String getHost() {
        return this.host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public boolean isRememberMe() {
        return this.rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    public void clear() {
        this.phone = null;
        this.host = null;
        this.rememberMe = false;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getClass().getName());
        sb.append(" - ");
        sb.append(this.phone);
        sb.append(", rememberMe=").append(this.rememberMe);
        if (this.host != null) {
            sb.append(" (").append(this.host).append(")");
        }

        return sb.toString();
    }
}