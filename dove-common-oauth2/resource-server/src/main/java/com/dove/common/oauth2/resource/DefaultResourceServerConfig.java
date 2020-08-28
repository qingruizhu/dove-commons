package com.dove.common.oauth2.resource;

/**
 * 在 OAuth2 的概念里，所有的接口都被称为资源，接口的权限也就是资源的权限，
 * 所以 Spring Security OAuth2 中提供了关于资源的注解 @EnableResourceServer，
 * 和 @EnableWebSecurity的作用类似。
 */

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * @Description:资源服务->配置文件
 * @Auther: qingruizhu
 * @Date: 2020/8/12 10:59
 */
@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ConditionalOnMissingBean(ResourceServerConfigurerAdapter.class)
public class DefaultResourceServerConfig extends ResourceServerConfigurerAdapter {

//    @Autowired
//    private RemoteTokenServices remoteTokenServices;


    @Value("${security.oauth2.resource.jwt.key-value:nicaicaikan}")
    private String jwtTokenSecret;
    @Value("#{'${dove.oauth2.ignore.urls:/test/**}'.split(',')}")
    private String[] ignoreUrls;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        // redis方式->存储token
//        resources.tokenServices(remoteTokenServices);
        //jwt方式->存储token
        resources.tokenStore(jwtTokenStore())
                .authenticationEntryPoint(authExceptionEntryPoint())
                .accessDeniedHandler(accessDeniedHandler());
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .formLogin().disable()
                .headers().frameOptions().disable()
                .and()
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint((authExceptionEntryPoint()))
                .accessDeniedHandler(accessDeniedHandler())
                .and()
                .authorizeRequests().antMatchers("/druid/**", "/swagger-ui.html", "/swagger-resources/**", "/v2/api-docs", "/api/applications").permitAll()
                .and()
                .authorizeRequests().antMatchers(ignoreUrls).permitAll()
                .anyRequest().authenticated();
    }

    @Bean
    public TokenStore jwtTokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
        accessTokenConverter.setSigningKey(jwtTokenSecret);
        accessTokenConverter.setVerifierKey(jwtTokenSecret);
        return accessTokenConverter;
    }


    @Bean
    public DoveAuthExceptionEntryPoint authExceptionEntryPoint() {
        return new DoveAuthExceptionEntryPoint();
    }

    @Bean
    public DoveAccessDeniedHandler accessDeniedHandler() {
        return new DoveAccessDeniedHandler();
    }

}
