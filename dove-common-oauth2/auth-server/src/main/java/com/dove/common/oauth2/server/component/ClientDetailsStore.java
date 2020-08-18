package com.dove.common.oauth2.server.component;

import org.springframework.security.oauth2.provider.client.BaseClientDetails;

import java.util.List;

/**
 * @Description: 获取 BaseClientDetails 列表
 * pass: 需服务端实现
 * @Auther: qingruizhu
 * @Date: 2020/8/15 10:26
 */
public interface ClientDetailsStore {
    List<? extends BaseClientDetails> clientDetails();
}
