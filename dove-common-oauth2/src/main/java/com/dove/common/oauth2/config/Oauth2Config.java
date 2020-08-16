package com.dove.common.oauth2.config;

import com.dove.common.oauth2.component.ClientDetailsStore;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.client.InMemoryClientDetailsService;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 授权服务暴露的主要接口
 * {@link org.springframework.security.oauth2.provider.endpoint.AbstractEndpoint}
 * POST /oauth/authorize  授权码模式认证授权接口
 * GET/POST /oauth/token  获取 token 的接口
 * POST  /oauth/check_token  检查 token 合法性接口
 */

/**
 * @Description:
 * @Auther: qingruizhu
 * @Date: 2020/8/12 09:52
 */
@Configuration
@EnableAuthorizationServer
public class Oauth2Config extends AuthorizationServerConfigurerAdapter {


    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private UserDetailsService userDetailsService;
    @Resource
    ClientDetailsStore clientDetailsStore;
    //    @Autowired
//    private OAuth2ExceptionTranslator oAuth2ExceptionTranslator;
    //    @Autowired
//    private TokenStore redisTokenStore;
    @Resource
    private TokenStore jwtTokenStore;
    @Resource
    private AccessTokenConverter jwtAccessTokenConverter;
    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                // 使用 authenticationManager -> 支持 password 模式
                .authenticationManager(authenticationManager)
                //用户验证
                .userDetailsService(userDetailsService)
                //自定义异常处理
//                .exceptionTranslator(oAuth2ExceptionTranslator)
                //redis存储token
//                .tokenStore(redisTokenStore);
                // jwt存储token
                .tokenStore(jwtTokenStore)
                .accessTokenConverter(jwtAccessTokenConverter);


    }

    /**
     * 配置客户端
     *
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetailsService());
//        clients.inMemory().clients(clientDetailsService());
//        clients.inMemory()
//                .withClient("uac-client")
//                .secret(passwordEncoder.encode("uac-secret-8888"))
//                // 授权码模式，密码模式，可刷新token
//                .authorizedGrantTypes("authorization_code", "password", "refresh_token")
//                // token 有效期
//                .accessTokenValiditySeconds(3600)
//                .refreshTokenValiditySeconds(5400)
//                .scopes("all")
//                .and()
//                .withClient("client2-client")
//                .secret(passwordEncoder.encode("client2-secret-8888"))
//                // 授权码模式，密码模式，可刷新token
//                .authorizedGrantTypes("authorization_code", "password", "refresh_token")
//                // token 有效期
//                .accessTokenValiditySeconds(3600)
//                .refreshTokenValiditySeconds(5400)
//                .scopes("all");
    }

    public ClientDetailsService clientDetailsService() {
        InMemoryClientDetailsService clientDetailsService = new InMemoryClientDetailsService();
        Map<String, ClientDetails> map = new HashMap<>();
        List<? extends BaseClientDetails> clientDetails = clientDetailsStore.clientDetails();
        if (CollectionUtils.isNotEmpty(clientDetails)) {
            clientDetails.forEach((client -> {
                client.setClientSecret(passwordEncoder.encode(client.getClientSecret()));
                map.put(client.getClientId(), client);
            }));
        }
//        BaseClientDetails client1 = new BaseClientDetails();
//        client1.setClientId("uac-client");
//        client1.setClientSecret(passwordEncoder.encode("uac-secret-8888"));
//        client1.setAuthorizedGrantTypes(Arrays.asList("authorization_code", "password", "refresh_token"));
//        client1.setAccessTokenValiditySeconds(3600);
//        client1.setRefreshTokenValiditySeconds(5400);
//        client1.setScope(Arrays.asList("all"));
//        map.put(client1.getClientId(), client1);
        clientDetailsService.setClientDetailsStore(map);
        return clientDetailsService;
    }


    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        // 允许客户端访问 OAuth2 授权接口，否则请求 token 会返回 401。
        security.allowFormAuthenticationForClients();
        //允许已授权用户访问 checkToken 接口。
        security.checkTokenAccess("isAuthenticated()");
        //允许已授权用户访问 获取 token 接口。
        security.tokenKeyAccess("isAuthenticated()");
//        security.accessDeniedHandler();
//        security.authenticationEntryPoint()
    }

}
