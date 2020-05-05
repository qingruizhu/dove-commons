package com.dove.common.shiro.server.config;

import com.dove.common.shiro.core.realm.JwtShiroRealm;
import com.dove.common.shiro.core.service.IUserShiroService;
import com.dove.common.shiro.server.realm.PhoneShiroRealm;
import com.dove.common.shiro.server.realm.UserNameShiroRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;


/**
 * shiro配置类
 */
@Configuration
public class ServerShiroConfig {


    /**
     * 设置 SecurityManager
     */
    @Bean
    @Qualifier("defaultWebSecurityManager")
    public SecurityManager securityManager(
            DefaultWebSecurityManager defaultWebSecurityManager,
            JwtShiroRealm jwtShiroRealm,
            IUserShiroService userShiroService) {
        defaultWebSecurityManager.setRealms(Arrays.asList(
                jwtShiroRealm,
                userNameShiroRealm(userShiroService),
                phoneShiroRealm(userShiroService)));
        return defaultWebSecurityManager;
    }

    @Bean
    public UserNameShiroRealm userNameShiroRealm(IUserShiroService userShiroService) {
        return new UserNameShiroRealm(userShiroService);
    }

    @Bean
    public PhoneShiroRealm phoneShiroRealm(IUserShiroService userShiroService) {
        return new PhoneShiroRealm(userShiroService);
    }

}
