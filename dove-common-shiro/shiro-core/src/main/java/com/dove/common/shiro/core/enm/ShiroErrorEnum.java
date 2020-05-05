package com.dove.common.shiro.core.enm;

import com.dove.common.core.enm.IBaseEum;
import com.dove.common.shiro.core.holder.UserHolder;

/**
 * @Description:
 * @Auther: qingruizhu
 * @Date: 2020/4/23 13:25
 */
public enum ShiroErrorEnum implements IBaseEum {
    /**
     * {@link UserHolder}
     */
    SSO_USERHOLDER_ERROR(1, "UserHolder操作异常,请联系管理员！"),

    AUTHEN_ERROR(1, "身份认证失败"),
    AUTHEN_ERROR_TOKEN(1, "身份认证失败,请重新获取[token]"),
    AUTHOR_ERROR_PERMISSION(2, "没有操作权限"),
    AUTHOR_ERROR_ROLE(2, "没有操作角色"),
    ;
    private int code;
    private String message;

    ShiroErrorEnum(int code, String message) {
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
