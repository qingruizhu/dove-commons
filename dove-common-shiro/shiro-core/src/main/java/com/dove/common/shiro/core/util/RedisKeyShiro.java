package com.dove.common.shiro.core.util;

/**
 * @Description: 存redis的shiro数据的key
 * @Auther: qingruizhu
 * @Date: 2020/4/29 10:10
 */
public class RedisKeyShiro {

    /**
     * token 发布的毫秒
     */
    private static String SHIRO_PREFIX_TOKEN_ISSUE_MILLI = "shiro:token:issue:milli";
    private static String SHIRO_PREFIX_USER = "shiro:user";
    private static String COLON = ":";

    public static String tokenIssueTime(String subject) {
        StringBuffer sb = new StringBuffer(SHIRO_PREFIX_TOKEN_ISSUE_MILLI);
        sb.append(COLON);
        sb.append(subject);
        return sb.toString();
    }

    public static String user(String username) {
        StringBuffer sb = new StringBuffer(SHIRO_PREFIX_USER);
        sb.append(COLON);
        sb.append(username);
        return sb.toString();
    }

}
