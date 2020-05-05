package com.dove.common.shiro.core.realm;

import com.dove.common.jwt.util.JwtTokenUtil;
import com.dove.common.redis.service.RedisService;
import com.dove.common.shiro.core.credential.ShiroUser;
import com.dove.common.shiro.core.service.IUserShiroService;
import com.dove.common.shiro.core.token.JWTToken;
import com.dove.common.shiro.core.util.RedisKeyShiro;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.Set;


/**
 * 自定义身份认证
 * 基于HMAC（ 散列消息认证码）的控制域
 */

public class JwtShiroRealm extends AuthorizingRealm {

    @Resource
    private IUserShiroService userService;
    @Autowired
    RedisService redisService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

//    public JwtShiroRealm(JWTCredentialsMatcher matcher) {
//        this.setCredentialsMatcher(matcher);
//    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * 认证信息.(身份验证) : Authentication 是用来验证用户身份
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        JWTToken jwtToken = (JWTToken) authcToken;
        String token = jwtToken.getToken();
        String username = JwtTokenUtil.getSubject(token);
        ShiroUser shiroUser;
        shiroUser = (ShiroUser) redisService.getObj(RedisKeyShiro.user(username));
        if (null == shiroUser) shiroUser = userService.getUserInfo(username);
        if (null == shiroUser) throw new AuthenticationException("该用户不存在");
        // 先验证该token是否过期
        if (!jwtTokenUtil.verify(token, username)) throw new AuthenticationException("token已过期");
        // 再验证该token的发布时间和缓存中的发布时间是否一致
        String issueTimeKey = RedisKeyShiro.tokenIssueTime(username);
        if (!redisService.exist(issueTimeKey)) throw new AuthenticationException("token失效");
        long redisTime = (long) redisService.getObj(issueTimeKey);
        long tokenTime = JwtTokenUtil.getIssuedAtMilli(token);
        if (redisTime != tokenTime) throw new AuthenticationException("token失效");
        return new SimpleAuthenticationInfo(shiroUser, token, this.getName());
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        ShiroUser user = (ShiroUser) principals.getPrimaryPrincipal();
        Set<String> roles = user.getRoles();
        Set<String> permissions = user.getPermissions();
        if (CollectionUtils.isEmpty(roles) || CollectionUtils.isEmpty(permissions)) {
            if (CollectionUtils.isEmpty(roles)) {
                roles = userService.getUserRoles(user.getId());
            }
            if (CollectionUtils.isEmpty(permissions)) {
                permissions = userService.getUserPermissions(user.getId());
            }
            if (roles != null || permissions != null) {
                if (roles != null) {
                    simpleAuthorizationInfo.addRoles(roles);
                    user.setRoles(roles);
                }
                if (permissions != null) {
                    simpleAuthorizationInfo.addStringPermissions(permissions);
                    user.setPermissions(permissions);
                }
                redisService.set(RedisKeyShiro.user(user.getUsername()), user, jwtTokenUtil.getExpiration());
            }
        }
        return simpleAuthorizationInfo;
    }
}
