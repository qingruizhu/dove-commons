package com.dove.common.oauth2.server.advice;

import com.dove.common.base.advice.GlobalExceptionHandle;
import com.dove.common.base.vo.CommonResult;
import com.dove.common.oauth2.server.enm.OauthErrorEnum;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.common.exceptions.*;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * @Description:关于oauth2的统一异常处理
 * @Author qingruizhu
 * @Date 11:51 上午 2020/8/14
 **/
@RestControllerAdvice
public class OauthExceptionHandle extends GlobalExceptionHandle {


    @ExceptionHandler(UnsupportedGrantTypeException.class)
    public CommonResult handle(UnsupportedGrantTypeException e) {
//        [error="unsupported_grant_type", error_description="Unsupported grant type: passwor"]
        return CommonResult.failed(OauthErrorEnum.OAUTH_ERROR_TOKEN_unsupported_grant_type);
    }

    @ExceptionHandler(InvalidGrantException.class)
    public CommonResult handle(InvalidGrantException e) {
        //[error="invalid_grant", error_description="用户名或密码错误"]
//            return new ResponseEntity(OAuth2Exception.create(OAuth2Exception.INVALID_GRANT, "账户密码错误"), HttpStatus.OK);
        return new CommonResult(false, OauthErrorEnum.OAUTH_ERROR_TOKEN_API.getCode(), OauthErrorEnum.OAUTH_ERROR_TOKEN_API.getMessage(), null);
    }

    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public CommonResult handle(InternalAuthenticationServiceException e) {
//        return new ResponseEntity(OAuth2Exception.create(OAuth2Exception.INVALID_GRANT, "账户名错误"), HttpStatus.OK);
        return new CommonResult(false, OauthErrorEnum.OAUTH_ERROR_TOKEN_API.getCode(), OauthErrorEnum.OAUTH_ERROR_TOKEN_API.getMessage(), null);
    }


    @ExceptionHandler(InvalidTokenException.class)
    public CommonResult handle(InvalidTokenException e) {
//        return new ResponseEntity(OAuth2Exception.create(OAuth2Exception.INVALID_TOKEN, "无效的访问令牌"), HttpStatus.OK);
        return CommonResult.failed(OauthErrorEnum.OAUTH_ERROR_TOKEN_INVALID.getCode(), e.getMessage());
    }

    @ExceptionHandler(InvalidClientException.class)
    public CommonResult handle(InvalidClientException e) {
//        return new ResponseEntity(OAuth2Exception.create(OAuth2Exception.INVALID_CLIENT, null), HttpStatus.OK);
        return CommonResult.failed(OauthErrorEnum.OAUTH_ERROR_TOKEN_invalid_client);
    }

    @ExceptionHandler(InvalidScopeException.class)
    public CommonResult handle(InvalidScopeException e) {
//       return new ResponseEntity(OAuth2Exception.create(OAuth2Exception.INVALID_SCOPE, null), HttpStatus.OK);
        //[error="invalid_scope", error_description="Invalid scope: al", scope="all"]
        return CommonResult.failed(OauthErrorEnum.OAUTH_ERROR_TOKEN_invalid_scope);
    }


    @Override
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public CommonResult handle(HttpRequestMethodNotSupportedException e) {
//        [org.springframework.web.HttpRequestMethodNotSupportedException: Request method 'GET' not supported]
        return CommonResult.failed(OauthErrorEnum.OAUTH_ERROR_TOKEN_METHOD);
    }


    @ExceptionHandler({InvalidRequestException.class})
    public CommonResult handle(InvalidRequestException e) {
//        [org.springframework.web.HttpRequestMethodNotSupportedException: Request method 'GET' not supported]
        return CommonResult.failed(e.getMessage());
    }

}
