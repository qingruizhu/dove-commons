package com.dove.common.base.vo;


import com.dove.common.core.enm.IBaseEum;
import com.dove.common.core.enm.SysErrorEnum;
import com.dove.common.util.holder.ThreadLocalKey;
import com.dove.common.util.holder.ThreadLocalMap;

/**
 * @Description: 封装结果集
 * @Auther: qingruizhu
 * @Date: 2020/4/8 13:13
 */
public class CommonResult<T> {
    /**
     * 成功
     */
    public static <T> CommonResult<T> success() {
        return success(null);
    }

    public static <T> CommonResult<T> success(T data) {
        return success(data, SysErrorEnum.SUCCESS.getMessage());
    }

    public static <T> CommonResult success(T data, String message) {
        return new CommonResult(true, SysErrorEnum.SUCCESS.getCode(), message, data);
    }

    /**
     * 失败
     */
    public static <T> CommonResult<T> failed() {
        return failed(SysErrorEnum.SYSTEM_ERROR.getMessage());
    }

    public static <T> CommonResult failed(String message) {
        return failed(SysErrorEnum.SYSTEM_ERROR.getCode(), message);
    }

    public static <T> CommonResult<T> failed(IBaseEum eum) {
        return failed(eum.getCode(), eum.getMessage());
    }

    public static <T> CommonResult failed(int code, String message) {
        StringBuilder sb = new StringBuilder(message);
        Object snm = ThreadLocalMap.get(ThreadLocalKey.SERIAL_NUMBER.name());
        if (null != snm && !"".equals(snm)) {
            sb.append("; 流水号:");
            sb.append(snm);
        }
        return new CommonResult(false, code, sb.toString(), null);
    }

    private boolean success;
    private long code;
    private String message;
    private T data;

    public CommonResult(boolean success, long code, String message, T data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("【");
        if (null != data) {
            sb.append(data.toString());
        }
        sb.append("】");
        return "CommonResult{" +
                "success=" + success +
                ", code=" + code +
                ", message='" + message + '\'' +
                ", data=" + sb.toString() +
                '}';
    }
}
