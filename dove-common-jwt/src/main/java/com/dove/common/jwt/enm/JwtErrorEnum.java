package com.dove.common.jwt.enm;

import com.dove.common.core.enm.IBaseEum;

/**
 * @Description:
 * @Auther: qingruizhu
 * @Date: 2020/4/23 13:25
 */
public enum JwtErrorEnum implements IBaseEum {

    JWT_ERROR_SIGN(300, "jwt签名失败"),
    JWT_ERROR_SIGN_HVT_SUBJECT(500, "jwt签名失败,[claims]未设置[uid]"),
    JWT_ERROR_GET_ALGORITHM(500, "jwt异常,获取[Algorithm]失败")


    ;

    private int code;
    private String message;

    JwtErrorEnum(int code, String message) {
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
