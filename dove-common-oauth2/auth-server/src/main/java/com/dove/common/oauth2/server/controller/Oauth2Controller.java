package com.dove.common.oauth2.server.controller;

import com.dove.common.base.annotation.CommonResultAnnon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

/**
 * @Description: 登录
 * @Auther: qingruizhu
 * @Date: 2020/8/14 11:26
 */
@RestController
@RequestMapping("/oauth")
@CommonResultAnnon
public class Oauth2Controller {

    @Autowired
    private TokenEndpoint tokenEndpoint;

    @PostMapping("/token")
    public ResponseEntity<OAuth2AccessToken> token(Principal principal, @RequestParam Map<String, String> parameters) throws HttpRequestMethodNotSupportedException {
        return tokenEndpoint.postAccessToken(principal, parameters);
    }

}
