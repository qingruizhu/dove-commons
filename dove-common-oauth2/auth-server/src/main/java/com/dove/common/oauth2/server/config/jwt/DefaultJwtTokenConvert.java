package com.dove.common.oauth2.server.config.jwt;

import com.common.dove.oauth2.base.principal.OauthUser;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: token 增强转换器
 * @Auther: qingruizhu
 * @Date: 2020/8/13 11:40
 */
public class DefaultJwtTokenConvert extends JwtAccessTokenConverter {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        if (accessToken instanceof DefaultOAuth2AccessToken) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof OauthUser) {
                OauthUser oauthUser = (OauthUser) principal;
                Map<String, Object> info = new HashMap<>();
                info.put("user_id", oauthUser.getUser_id());
                info.put("user_mobile", oauthUser.getUser_mobile());
                ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
            }
        }
        return super.enhance(accessToken, authentication);
    }
}
