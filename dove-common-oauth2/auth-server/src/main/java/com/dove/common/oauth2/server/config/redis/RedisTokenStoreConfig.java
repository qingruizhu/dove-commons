//package com.dove.cloud.auth.config.redis;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
//
///**
// * @Description: redis-> 存储token
// * @Auther: qingruizhu
// * @Date: 2020/8/12 10:08
// */
//@Configuration
//public class RedisTokenStoreConfig {
//    @Autowired
//    private RedisConnectionFactory redisConnectionFactory;
//
//    @Bean
//    public RedisTokenStore redisTokenStore() {
//        return new RedisTokenStore(redisConnectionFactory);
//    }
//}
