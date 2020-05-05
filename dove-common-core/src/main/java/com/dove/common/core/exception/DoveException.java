package com.dove.common.core.exception;


import com.dove.common.core.enm.SysErrorEnum;

public class DoveException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private Integer code;
    private String message;

    public DoveException(String message) {
        this(SysErrorEnum._ERROR.getCode(), message);
    }

    public DoveException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;

    }

    public DoveException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
