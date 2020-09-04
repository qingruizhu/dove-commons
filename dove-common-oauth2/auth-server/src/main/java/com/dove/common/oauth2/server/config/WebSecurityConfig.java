package com.dove.common.oauth2.server.config;

import com.common.dove.oauth2.base.handler.DoveAccessDeniedHandler;
import com.common.dove.oauth2.base.handler.DoveAuthExceptionEntryPoint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Description:
 * @Auther: qingruizhu
 * @Date: 2020/8/12 09:37
 */
@Configuration
@EnableWebSecurity
@ConditionalOnMissingBean(WebSecurityConfigurer.class)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("#{'${dove.oauth2.ignore.urls:/test/**}'.split(',')}")
    private String[] ignoreUrls;

    /**
     * 密码加密工具类，它可以实现不可逆的加密。
     */
    @Bean
    @ConditionalOnMissingBean(PasswordEncoder.class)
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 为了实现 OAuth2 的 password 模式必须要指定的授权管理 Bean。
     */
    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    /**
     * 允许所有接口匿名访问。
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .formLogin().disable()
//                .authorizeRequests().antMatchers("/**").permitAll();

        http
                .formLogin().disable()
                .headers().frameOptions().disable()
                .and()
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint((authExceptionEntryPoint()))
                .accessDeniedHandler(accessDeniedHandler())
                .and()
                .authorizeRequests().antMatchers(
                "/oauth/**",
                "/druid/**", "/swagger-ui.html", "/swagger-resources/**", "/v2/api-docs", "/api/applications")
                .permitAll()
                .and()
                .authorizeRequests().antMatchers(ignoreUrls).permitAll()
                .anyRequest().authenticated();
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
