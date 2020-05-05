package com.dove.common.shiro.server.realm;

import com.dove.common.shiro.core.credential.ShiroUser;
import com.dove.common.shiro.core.service.IUserShiroService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserNameShiroRealm extends AuthenticatingRealm {
    private final Logger log = LoggerFactory.getLogger(UserNameShiroRealm.class);


    private IUserShiroService userShiroService;

    public UserNameShiroRealm(IUserShiroService userShiroService) {
        this.userShiroService = userShiroService;
        this.setCredentialsMatcher(new HashedCredentialsMatcher(Sha256Hash.ALGORITHM_NAME));
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken userpasswordToken = (UsernamePasswordToken) token;
        String username = userpasswordToken.getUsername();
        ShiroUser shiroUser = userShiroService.getUserInfo(username);
        if (shiroUser == null)
            throw new AuthenticationException("用户不存在");
        return new SimpleAuthenticationInfo(shiroUser, shiroUser.getPassword(), ByteSource.Util.bytes(shiroUser.getPwdSalt()), this.getName());
    }

}
