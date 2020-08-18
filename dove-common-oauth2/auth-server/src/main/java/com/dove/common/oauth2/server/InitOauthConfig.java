package com.dove.common.oauth2.server;

import com.dove.common.oauth2.server.component.DefaultUserDetailsService;
import com.dove.common.oauth2.server.config.Oauth2Config;
import com.dove.common.oauth2.server.config.WebSecurityConfig;
import com.dove.common.oauth2.server.config.jwt.JwtTokenStoreConfig;
import com.dove.common.oauth2.server.controller.Oauth2Controller;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @Description:
 * @Auther: qingruizhu
 * @Date: 2020/8/14 16:06
 */

@Configuration
@Import({JwtTokenStoreConfig.class, WebSecurityConfig.class, Oauth2Config.class})
public class InitOauthConfig {

    @Bean
    @ConditionalOnMissingBean(UserDetailsService.class)
    public UserDetailsService userDetailsService() {
        return new DefaultUserDetailsService();
    }

//    @Bean
//    public ClientDetailsService absInMemoryClientDetailsService() {
//        return new AbsInMemoryClientDetailsService();
//    }


    @Bean
    public Oauth2Controller oauth2Controller() {
        return new Oauth2Controller();
    }

}
