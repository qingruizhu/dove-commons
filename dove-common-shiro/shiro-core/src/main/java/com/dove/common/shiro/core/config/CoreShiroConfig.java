package com.dove.common.shiro.core.config;

import com.dove.common.jwt.util.JwtTokenUtil;
import com.dove.common.redis.service.RedisService;
import com.dove.common.shiro.core.filter.JwtAuthenFilter;
import com.dove.common.shiro.core.filter.PermissionAuthorFilter;
import com.dove.common.shiro.core.filter.RolesAuthorFilter;
import com.dove.common.shiro.core.realm.JwtShiroRealm;
import org.apache.shiro.authz.Authorizer;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;


/**
 * shiro配置类
 */
@Configuration
//@ComponentScan("com.dove.common.shiro.core")
public class CoreShiroConfig {

    @Value("${shiro.jwt.secret:cWluZ3J1aXpodQ==}")//默认"qingruizhu"
    private String secret;
    //    @Value("${shiro.jwt.expiration:600}")//默认10分钟
    @Value("${shiro.jwt.expiration:1800}")
    private Long expiration;
    @Value("#{'${shiro.anon.path:/test/**}'.split(',')}")
    private Set<String> excludePattern;

    //
    @Bean
    public JwtTokenUtil jwtTokenUtil() {
        return new JwtTokenUtil(secret, expiration);
    }

    /**
     * 注册过滤器
     */
    @Bean
    public FilterRegistrationBean<Filter> filterRegistrationBean(SecurityManager SecurityManager, RedisService redisService) throws Exception {
        FilterRegistrationBean<Filter> filterRegistration = new FilterRegistrationBean<Filter>();
        filterRegistration.setFilter((Filter) shiroFilter(SecurityManager, redisService).getObject());
        filterRegistration.addInitParameter("targetFilterLifecycle", "true");
        filterRegistration.setAsyncSupported(true);
        filterRegistration.setEnabled(true);
        filterRegistration.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.ASYNC);
        return filterRegistration;
    }

    /**
     * 设置过滤器
     */
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager, RedisService redisService) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        //添加filter
        Map<String, Filter> filterMap = factoryBean.getFilters();
        filterMap.put("authcToken", new JwtAuthenFilter(jwtTokenUtil(), redisService));
        filterMap.put("anyRole", new RolesAuthorFilter());
        filterMap.put("anyPermission", new PermissionAuthorFilter());
        factoryBean.setFilters(filterMap);
        //设置 SecurityManager
        factoryBean.setSecurityManager(securityManager);
        //添加认证规则
        factoryBean.setFilterChainDefinitionMap(shiroFilterChainDefinition().getFilterChainMap());
//        factoryBean.setLoginUrl("/**/unlogin");
        return factoryBean;
    }

    /**
     * 设置 SecurityManager
     */
    @Bean
    @Primary
    public DefaultWebSecurityManager defaultWebSecurityManager() {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
//        manager.setAuthenticator(authenticator());
        manager.setRealms(Arrays.asList(jwtShiroRealm()));
        manager.setAuthorizer(authorizer());
        manager.setSubjectDAO(subjectDAO());
        return manager;
    }

    @Bean
    public JwtShiroRealm jwtShiroRealm() {
        JwtShiroRealm jwtShiroRealm = new JwtShiroRealm();
        return jwtShiroRealm;
    }

    @Bean
    protected ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
        chainDefinition.addPathDefinition("/**/login**", "noSessionCreation,anon");//noSessionCreation的作用是用户在操作session时会抛异常
        chainDefinition.addPathDefinition("/**/regist**", "noSessionCreation,anon");//noSessionCreation的作用是用户在操作session时会抛异常
        chainDefinition.addPathDefinition("/**/logout**", "noSessionCreation,authcToken[permissive]"); //做用户认证，permissive参数的作用是当token无效时也允许请求访问，不会返回鉴权未通过的错误

        chainDefinition.addPathDefinition("/swagger-ui.html", "anon");
        chainDefinition.addPathDefinition("/swagger-resources/**", "anon");
        chainDefinition.addPathDefinition("/v2/**", "anon");
        chainDefinition.addPathDefinition("/webjars/**", "anon");
        chainDefinition.addPathDefinition("/configuration/security", "anon");
        chainDefinition.addPathDefinition("/configuration/ui", "anon");

        chainDefinition.addPathDefinition("/image/**", "anon");
//        chainDefinition.addPathDefinition("/admin/**", "noSessionCreation,authcToken,anyRole[admin,manager]"); //只允许admin或manager角色的用户访问
//        chainDefinition.addPathDefinition("/article/*", "noSessionCreation,authcToken[permissive]");
        excludePattern.forEach((pattern) -> {
            chainDefinition.addPathDefinition(pattern, "anon");
        });
        chainDefinition.addPathDefinition("/**", "noSessionCreation,authcToken,anyRole,anyPermission");
        return chainDefinition;
    }

    //
//    @Bean
//    public Authenticator authenticator() {
//        ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();
//        authenticator.setRealms(Arrays.asList(new DbShiroRealm(userService), new JWTShiroRealm(userService, new JWTCredentialsMatcher(jwtTokenUtil()))));
////        authenticator.setAuthenticationStrategy(new FirstSuccessfulStrategy());
//        return authenticator;
//    }
    @Bean
    public Authorizer authorizer() {
        ModularRealmAuthorizer authorizer = new ModularRealmAuthorizer();
        authorizer.setRealms(Arrays.asList(jwtShiroRealm()));
        return authorizer;
    }

    /**
     * 禁用 session
     */
    @Bean
    protected DefaultSubjectDAO subjectDAO() {
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        return subjectDAO;
    }

    /**
     * 开启aop注解支持,如: {@link org.apache.shiro.authz.annotation.RequiresPermissions}
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
}
