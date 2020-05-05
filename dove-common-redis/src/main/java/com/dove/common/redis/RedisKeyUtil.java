///*
// * Copyright (c) 2018. paascloud.net All Rights Reserved.
// * 项目名称：paascloud快速搭建企业级分布式微服务平台
// * 类名称：RedisKeyUtil.java
// * 创建人：刘兆明
// * 联系方式：paascloud.net@gmail.com
// * 开源地址: https://github.com/paascloud
// * 博客地址: http://blog.paascloud.net
// * 项目官网: http://paascloud.net
// */
//
//package com.dove.common.redis;
//
//
//import org.springframework.util.StringUtils;
//
//public final class RedisKeyUtil {
//
//    /**
//     * The 用户信息 PREFIX_TOKEN_KEY.
//     */
//    private static final String PREIFIX_TOKEN = "tea:token:";
//
//    /**
//     * The 验证码 PREFIX_AUTHCODE.
//     */
//    private static final String PREFIX_AUTHCODE = "tea:authCode:";
//    private static final long EXPIRE_AUTHCODE = 60;
//
//
//    public static String getTokenKey(String token) {
//        if (StringUtils.isEmpty(token)) {
//            return null;
//        }
//        return new StringBuilder(PREIFIX_TOKEN).append(token).toString();
//    }
//
//    public static String getAuthCodeKey(String prefix) {
//        if (StringUtils.isEmpty(prefix)) {
//            return null;
//        }
//        return new StringBuilder(PREFIX_AUTHCODE).append(prefix).toString();
//    }
//
//    public static long getAuthCodeExpire() {
//        return EXPIRE_AUTHCODE;
//    }
//
//
//}
