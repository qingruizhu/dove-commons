package com.dove.common.shiro.core.service;


import com.dove.common.shiro.core.credential.ShiroUser;

import java.util.Set;

/**
 * @Description:
 * @Auther: qingruizhu
 * @Date: 2020/4/23 18:00
 */
public interface IUserShiroService {

    /**
     * 根据username获取用户
     *
     * @param username:存入token中的唯一标识
     */
    ShiroUser getUserInfo(String username);

    ShiroUser getUserInfoByPhone(String phone);

    Set<String> getUserRoles(Long uid);

    Set<String> getUserPermissions(Long uid);
}
