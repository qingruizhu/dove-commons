package com.dove.common.shiro.core.exception;


import com.dove.common.core.exception.DoveException;
import com.dove.common.shiro.core.enm.ShiroErrorEnum;

/**
 * @Description: shiro异常
 * @Auther: qingruizhu
 * @Date: 2020/4/23 19:54
 */
public class DoveShiroException extends DoveException {
    public DoveShiroException(String message) {
        super(message);
    }

    public DoveShiroException(ShiroErrorEnum enm) {
        super(enm.getCode(), enm.getMessage());
    }
}
