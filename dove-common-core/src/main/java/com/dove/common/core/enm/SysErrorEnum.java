package com.dove.common.core.enm;

/**
 * @Description:
 * @Auther: qingruizhu
 * @Date: 2020/4/8 13:25
 */
public enum SysErrorEnum implements IBaseEum {
    SUCCESS(200, "成功"),
    SYSTEM_ERROR(500, "系统内部错误"),
    SYSTEM_ERROR_REQUEST_PARAM_INVALIDATE(501, "非法参数"),
    SYSTEM_ERROR_REQUEST_PARAM_MISSING(502, "必输参数缺失"),
    SYSTEM_ERROR_REQUEST_METHOD(503, "请求方式有误"),
    _ERROR(777, "无码错误");
    ;




    private int code;
    private String message;
    SysErrorEnum(int code, String message) {
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
