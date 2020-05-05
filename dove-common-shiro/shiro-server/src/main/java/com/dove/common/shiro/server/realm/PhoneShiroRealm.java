package com.dove.common.shiro.server.realm;

import com.dove.common.shiro.core.credential.ShiroUser;
import com.dove.common.shiro.core.service.IUserShiroService;
import com.dove.common.shiro.server.token.PhoneToken;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PhoneShiroRealm extends AuthenticatingRealm {
    private final Logger log = LoggerFactory.getLogger(PhoneShiroRealm.class);

    private IUserShiroService userShiroService;

    //    public PhoneShiroRealm(PhoneCredentialsMatcher matcher) {
//        this.setCredentialsMatcher(matcher);
//    }
    public PhoneShiroRealm(IUserShiroService userShiroService) {
        this.userShiroService = userShiroService;

    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof PhoneToken;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        PhoneToken phoneToken = (PhoneToken) token;
        String phone = phoneToken.getPhone();
        ShiroUser shiroUser = userShiroService.getUserInfoByPhone(phone);
        if (shiroUser == null)
            throw new AuthenticationException("手机号未注册");
        return new SimpleAuthenticationInfo(shiroUser, phone, this.getName());
    }

}
