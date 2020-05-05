package com.dove.common.shiro.server.util;

import org.apache.shiro.crypto.hash.Sha256Hash;

/**
 * @Description: 密码工具
 * @Auther: qingruizhu
 * @Date: 2020/4/27 14:12
 */
public class EncrypPwdUtil {

    /**
     * 加密 <- 安全散列算法
     *
     * @param secret 设置密码
     * @param salt   盐
     * @return
     */
    public static String sha256(String secret, String salt) {
        return new Sha256Hash(secret, salt).toHex();
    }
}
