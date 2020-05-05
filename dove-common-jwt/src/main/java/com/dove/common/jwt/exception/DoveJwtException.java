package com.dove.common.jwt.exception;


import com.dove.common.core.exception.DoveException;
import com.dove.common.jwt.enm.JwtErrorEnum;

/**
 * @Description: jwt异常
 * @Auther: qingruizhu
 * @Date: 2020/4/23 19:54
 */
public class DoveJwtException extends DoveException {
    /*public DoveJwtException(String message) {
        super(message);
    }*/
    public DoveJwtException(JwtErrorEnum enm) {
        super(enm.getCode(), enm.getMessage());
    }
}
