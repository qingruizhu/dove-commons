package com.dove.common.oauth2.component;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @Description:
 * @Auther: qingruizhu
 * @Date: 2020/8/12 09:47
 */
public class DefaultUserDetailsService implements UserDetailsService {
    private final Log LOG = LogFactory.get(DefaultUserDetailsService.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

//        if (!username.equals("admin")) {
//            throw new UsernameNotFoundException("the user:" + username + " is not found");
//        }
//        return new OauthUser(1L, "17345678001", username, passwordEncoder.encode("123456"), Arrays.asList(new SimpleGrantedAuthority("admin")));
        LOG.error("请设置-> 自定义 UserDetailsService");
        throw new UsernameNotFoundException("请设置 UserDetailsService");
    }
}
