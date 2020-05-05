package com.dove.common.shiro.core.advice;

import com.dove.common.base.advice.GlobalExceptionHandle;
import com.dove.common.base.vo.CommonResult;
import com.dove.common.jwt.exception.DoveJwtException;
import com.dove.common.shiro.core.exception.DoveShiroException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.dove.common.shiro.core.enm.ShiroErrorEnum.AUTHEN_ERROR;
import static com.dove.common.shiro.core.enm.ShiroErrorEnum.AUTHOR_ERROR_PERMISSION;


/**
 * @Description:
 * @Author qingruizhu
 * @Date 8:49 下午 2020/4/9
 **/
@RestControllerAdvice
public class ShiroExceptionAdvice extends GlobalExceptionHandle {


    @ExceptionHandler(AuthenticationException.class)
    public CommonResult handle(AuthenticationException e) {
        logger.error(snmAppendErrorMsg("【shiro:身份认证】失败--->>>{}"), e.getMessage());
        return CommonResult.failed(AUTHEN_ERROR);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public CommonResult handle(UnauthorizedException e) {
        logger.error(snmAppendErrorMsg("【shiro:无权限】--->>>{}"), e.getMessage());
        return CommonResult.failed(AUTHOR_ERROR_PERMISSION);
    }


    @ExceptionHandler(DoveJwtException.class)
    public CommonResult handle(DoveJwtException e) {
        logger.error(snmAppendErrorMsg("【jwt】失败--->>>"), e);
        return CommonResult.failed(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(DoveShiroException.class)
    public CommonResult handle(DoveShiroException e) {
        logger.error(snmAppendErrorMsg("【shiro】失败--->>>"), e);
        return CommonResult.failed(e.getCode(), e.getMessage());
    }


}
