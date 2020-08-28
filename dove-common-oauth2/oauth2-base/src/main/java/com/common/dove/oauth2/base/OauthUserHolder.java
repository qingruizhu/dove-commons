package com.common.dove.oauth2.base;

import com.common.dove.oauth2.base.principal.OauthUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Description:
 * @Auther: qingruizhu
 * @Date: 2020/8/18 10:34
 */
public class OauthUserHolder {

    private static final Logger LOG = LoggerFactory.getLogger(OauthUserHolder.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static OauthUser user() {
        Authentication authentication = getAuthentication();
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
        String tokenValue = details.getTokenValue();
        String claims = JwtHelper.decode(tokenValue).getClaims();
        Map map = null;
        try {
            map = objectMapper.readValue(claims, Map.class);
        } catch (JsonProcessingException e) {
            LOG.error("OauthUserHolder 获取 OauthUser 异常", e);
            return null;
        }
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
                    user.setUser_id(intUid.longValue());
                } else if (user_id instanceof Long) {
                    user.setUser_id((Long) user_id);
                }
            }
            Object user_mobile = map.get("user_mobile");
            if (null != user_mobile) {
                user.setUser_mobile((String) user_mobile);
            }
            Object user_id_str = map.get("user_id_str");
            if (null != user_id_str) {
                user.setUser_id_str((String) user_id_str);
            }
            return user;
        }
        return null;
    }


    public static String uName() {
        Map body = jwtBody();
        if (null != body) {
            Object user_name = body.get("user_name");
            if (null != user_name) {
                return (String) user_name;
            }
        }
        return null;
    }

    public static Long uId() {
        Map body = jwtBody();
        if (null != body) {
            Object user_id = body.get("user_id");
            if (null != user_id) {
                if (user_id instanceof Integer) {
                    Integer intUid = (Integer) user_id;
                    return intUid.longValue();
                } else if (user_id instanceof Long) {
                    return (Long) user_id;
                }
            }
        }
        return null;
    }


    public static String uMobile() {
        Map body = jwtBody();
        if (null != body) {
            Object user_mobile = body.get("user_mobile");
            if (null != user_mobile) {
                return (String) user_mobile;
            }
        }
        return null;
    }

    public static String uIdStr() {
        Map body = jwtBody();
        if (null != body) {
            Object user_id_str = body.get("user_id_str");
            if (null != user_id_str) {
                return (String) user_id_str;
            }
        }
        return null;
    }

    public static String ext1() {
        Map body = jwtBody();
        if (null != body) {
            Object ext_1 = body.get("ext_1");
            if (null != ext_1) {
                return (String) ext_1;
            }
        }
        return null;
    }

    public static String ext2() {
        Map body = jwtBody();
        if (null != body) {
            Object ext_2 = body.get("ext_2");
            if (null != ext_2) {
                return (String) ext_2;
            }
        }
        return null;
    }

    public static String ext3() {
        Map body = jwtBody();
        if (null != body) {
            Object ext3 = body.get("ext_3");
            if (null != ext3) {
                return (String) ext3;
            }
        }
        return null;
    }

    public static Set<String> uRoles() {
        Collection<? extends GrantedAuthority> authorities = getAuthentication().getAuthorities();
        if (CollectionUtils.isNotEmpty(authorities)) {
            return authorities.stream().map(authority -> {
                return authority.getAuthority();
            }).collect(Collectors.toSet());
        }
        return null;
    }


    private static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    private static Map jwtBody() {
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) getAuthentication().getDetails();
        String claims = JwtHelper.decode(details.getTokenValue()).getClaims();
        Map map = null;
        try {
            map = objectMapper.readValue(claims, Map.class);
        } catch (JsonProcessingException e) {
            LOG.error("OauthUserHolder 获取 OauthUser 异常", e);
            return null;
        }
        return map;
    }


}
