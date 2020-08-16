package com.dove.common.oauth2.component;

import org.springframework.security.oauth2.provider.client.BaseClientDetails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description:
 * @Auther: qingruizhu
 * @Date: 2020/8/15 10:28
 */
public class TestClientDetailsStore implements ClientDetailsStore {

    @Override
    public List<? extends BaseClientDetails> clientDetails() {
        ArrayList<BaseClientDetails> baseClientDetails = new ArrayList<>();
        BaseClientDetails client1 = new BaseClientDetails();
        client1.setClientId("uac-client");
        client1.setClientSecret("uac-secret-8888");
        client1.setAuthorizedGrantTypes(Arrays.asList("authorization_code", "password", "refresh_token"));
        client1.setAccessTokenValiditySeconds(3600);
        client1.setRefreshTokenValiditySeconds(5400);
        client1.setScope(Arrays.asList("all"));
        baseClientDetails.add(client1);
        return baseClientDetails;
    }
}
