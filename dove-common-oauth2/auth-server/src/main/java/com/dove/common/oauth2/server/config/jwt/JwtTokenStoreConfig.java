package com.dove.common.oauth2.server.config.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${dove.oauth2.jwt.secret:nicaicaikan}")
    private String jwtTokenSecret;
    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    @Bean
    public TokenStore jwtTokenStore() {
        jwtAccessTokenConverter.setSigningKey(jwtTokenSecret);
        return new JwtTokenStore(jwtAccessTokenConverter);
    }

    @Bean
    @ConditionalOnMissingBean(JwtAccessTokenConverter.class)
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        return new DefaultJwtTokenConvert();
    }

//    @Bean
//    @ConditionalOnMissingBean(TokenEnhancer.class)
//    public TokenEnhancer tokenEnhancer() {
//        return (oAuth2AccessToken, OAuth2Authentication) -> {
//            if (oAuth2AccessToken instanceof DefaultOAuth2AccessToken) {
//                Object principal = OAuth2Authentication.getPrincipal();
//                if (principal instanceof OauthUser) {
//                    OauthUser oauthUser = (OauthUser) principal;
//                    Map<String, Object> info = new HashMap<>();
//                    info.put("user_id", oauthUser.getId());
//                    info.put("user_mobile", oauthUser.getMobile());
//                    ((DefaultOAuth2AccessToken) oAuth2AccessToken).setAdditionalInformation(info);
//                }
//            }
//            return oAuth2AccessToken;
//        };
//    }
}
