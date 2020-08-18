package com.common.dove.oauth2.base;

import com.common.dove.oauth2.base.principal.OauthUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

import java.util.Map;

/**
 * @Description:
 * @Auther: qingruizhu
 * @Date: 2020/8/18 10:34
 */
public class OauthUserHolder {

    private static final Logger LOG = LoggerFactory.getLogger(OauthUserHolder.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static OauthUser user() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
        String tokenValue = details.getTokenValue();
        String claims = JwtHelper.decode(tokenValue).getClaims();
        try {
            Map map = objectMapper.readValue(claims, Map.class);
            if (null != map) {
                Object user_name = map.get("user_name");
                if (null == user_name) {
                    return null;
                }
                OauthUser user = new OauthUser((String) user_name, (String) authentication.getCredentials(), authentication.getAuthorities());
                Object user_id = map.get("user_id");
                if (null != user_id) {
                    if (user_id instanceof Integer) {
                        Integer intUid = (Integer) user_id;
                        user.setId(intUid.longValue());
                    } else if (user_id instanceof Long) {
                        user.setId((Long) user_id);
                    }
                }
                Object user_mobile = map.get("user_mobile");
                if (null != user_mobile) {
                    user.setMobile((String) user_mobile);
                }
                Object user_id_str = map.get("user_id_str");
                if (null != user_id_str) {
                    user.setIdStr((String) user_id_str);
                }
                return user;
            }
            return null;
        } catch (JsonProcessingException e) {
            LOG.error("OauthUserHolder 获取 OauthUser 异常", e);
            return null;
        }
    }

    public static String uName() {
        OauthUser user = user();
        if (null != user) {
            return user.getUsername();
        }
        return null;
    }

    public static Long uId() {
        OauthUser user = user();
        if (null != user) {
            return user.getId();
        }
        return null;
    }

    public static String uMobile() {
        OauthUser user = user();
        if (null != user) {
            return user.getMobile();
        }
        return null;
    }

    public static String uIdStr() {
        OauthUser user = user();
        if (null != user) {
            return user.getIdStr();
        }
        return null;
    }
}
