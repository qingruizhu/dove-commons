package com.dove.common.oauth2.enm;

import com.dove.common.core.enm.IBaseEum;

/**
 * @Description:
 * @Auther: qingruizhu
 * @Date: 2020/8/14 13:53
 */
public enum OauthErrorEnum implements IBaseEum {


    /**
     *
     */
    OAUTH_ERROR_TOKEN_unsupported_grant_type(400, "unsupported grant type"),
    OAUTH_ERROR_TOKEN_invalid_scope(400, "invalid_scope"),
    OAUTH_ERROR_TOKEN_invalid_client(400, "invalid_client"),
    OAUTH_ERROR_TOKEN_API(400, "用户名或者密码错误"),
    OAUTH_ERROR_TOKEN_METHOD(405, "请求token接口方式有误"),

    OAUTH_ERROR_TOKEN_INVALID(401, "token失效"),


    ;

    private int code;
    private String message;

    OauthErrorEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
