package com.dove.common.base.exception;

import com.dove.common.core.enm.IBaseEum;
import com.dove.common.core.exception.DoveException;

/**
 * @Description: 业务异常
 * @Auther: qingruizhu
 * @Date: 2020/4/9 19:54
 */
public class BaseException extends DoveException {
    public BaseException(String message) {
        super(message);
    }

    public BaseException(IBaseEum enm) {
        super(enm.getCode(), enm.getMessage());
    }
}
