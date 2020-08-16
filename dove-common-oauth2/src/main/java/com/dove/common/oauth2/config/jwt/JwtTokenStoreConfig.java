package com.dove.common.oauth2.config.jwt;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * @Description: jwt->存储token
 * @Auther: qingruizhu
 * @Date: 2020/8/12 16:48
 */
@Configuration
@ConditionalOnMissingBean(TokenStore.class)
public class JwtTokenStoreConfig {

    @Bean
    public TokenStore jwtTokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        CustomJwtTokenConvert customJwtTokenConvert = new CustomJwtTokenConvert();
        customJwtTokenConvert.setSigningKey("nicaicaikan");
        return customJwtTokenConvert;
    }
}
