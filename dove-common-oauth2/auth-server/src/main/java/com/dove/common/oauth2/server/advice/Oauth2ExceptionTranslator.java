//package com.dove.cloud.auth.advice;
//
//import com.dove.common.base.vo.CommonResult;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.InternalAuthenticationServiceException;
//import org.springframework.security.oauth2.common.DefaultThrowableAnalyzer;
//import org.springframework.security.oauth2.common.exceptions.*;
//import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
//import org.springframework.security.web.util.ThrowableAnalyzer;
//import org.springframework.stereotype.Component;
//
///**
// * @Description: 自定义oauth2统一异常处理
// * @Auther: qingruizhu
// * @Date: 2020/8/13 14:05
// * pass: 因为重写了关于token的接口,所以可以用 OauthExceptionHandle 来替换此类
// */
//@Component
//public class OAuth2ExceptionTranslator implements WebResponseExceptionTranslator {
//
//    private ThrowableAnalyzer throwableAnalyzer = new DefaultThrowableAnalyzer();
//
//    @Override
//    public ResponseEntity translate(Exception e) throws Exception {
//        Throwable[] causeChain = throwableAnalyzer.determineCauseChain(e);
//        Throwable exception = throwableAnalyzer.getFirstThrowableOfType(InvalidGrantException.class, causeChain);
//        CommonResult commonResult = new CommonResult(false, 401, e.getMessage(), null);
//        if (exception != null) {
//            commonResult.setData(OAuth2Exception.INVALID_GRANT);
//            return ResponseEntity.ok(commonResult);
////            return new ResponseEntity(OAuth2Exception.create(OAuth2Exception.INVALID_GRANT, "账户名或密码错误"), HttpStatus.OK);
//        }
//        exception = throwableAnalyzer.getFirstThrowableOfType(InternalAuthenticationServiceException.class, causeChain);
//        if (exception != null) {
//            commonResult.setData(OAuth2Exception.INVALID_GRANT);
//            return ResponseEntity.ok(commonResult);
////            return new ResponseEntity(OAuth2Exception.create(OAuth2Exception.INVALID_GRANT, "账户名错误"), HttpStatus.OK);
//        }
//        exception = throwableAnalyzer.getFirstThrowableOfType(InvalidTokenException.class, causeChain);
//        if (exception != null) {
//            commonResult.setData(OAuth2Exception.INVALID_TOKEN);
//            return ResponseEntity.ok(commonResult);
////            return new ResponseEntity(OAuth2Exception.create(OAuth2Exception.INVALID_TOKEN, "无效的访问令牌"), HttpStatus.OK);
//        }
//        exception = throwableAnalyzer.getFirstThrowableOfType(InvalidClientException.class, causeChain);
//        if (exception != null) {
//            commonResult.setData(OAuth2Exception.INVALID_CLIENT);
//            return ResponseEntity.ok(commonResult);
////            return new ResponseEntity(OAuth2Exception.create(OAuth2Exception.INVALID_CLIENT, null), HttpStatus.OK);
//        }
//        exception = throwableAnalyzer.getFirstThrowableOfType(InvalidScopeException.class, causeChain);
//        if (exception != null) {
//            commonResult.setData(OAuth2Exception.INVALID_SCOPE);
//            return ResponseEntity.ok(commonResult);
////            return new ResponseEntity(OAuth2Exception.create(OAuth2Exception.INVALID_SCOPE, null), HttpStatus.OK);
//        }
//        commonResult.setCode(500);
//        commonResult.setData(OAuth2Exception.INVALID_REQUEST);
//        return ResponseEntity.ok(commonResult);
////        return new ResponseEntity(OAuth2Exception.create(OAuth2Exception.INVALID_REQUEST, null), HttpStatus.OK);
//    }
//
////    @Override
////    public ResponseEntity translate(Exception e) throws Exception {
////        Throwable[] causeChain = throwableAnalyzer.determineCauseChain(e);
////        Throwable exception = throwableAnalyzer.getFirstThrowableOfType(InvalidGrantException.class, causeChain);
////        if (exception != null) {
////            return new ResponseEntity(OAuth2Exception.create(OAuth2Exception.INVALID_GRANT, "账户密码错误"), HttpStatus.OK);
////        }
////        exception = throwableAnalyzer.getFirstThrowableOfType(InternalAuthenticationServiceException.class, causeChain);
////        if (exception != null) {
////            return new ResponseEntity(OAuth2Exception.create(OAuth2Exception.INVALID_GRANT, "账户名错误"), HttpStatus.OK);
////        }
////        exception = throwableAnalyzer.getFirstThrowableOfType(InvalidTokenException.class, causeChain);
////        if (exception != null) {
////            return new ResponseEntity(OAuth2Exception.create(OAuth2Exception.INVALID_TOKEN, "无效的访问令牌"), HttpStatus.OK);
////        }
////        exception = throwableAnalyzer.getFirstThrowableOfType(InvalidClientException.class, causeChain);
////        if (exception != null) {
////            return new ResponseEntity(OAuth2Exception.create(OAuth2Exception.INVALID_CLIENT, null), HttpStatus.OK);
////        }
////        exception = throwableAnalyzer.getFirstThrowableOfType(InvalidScopeException.class, causeChain);
////        if (exception != null) {
////            return new ResponseEntity(OAuth2Exception.create(OAuth2Exception.INVALID_SCOPE, null), HttpStatus.OK);
////        }
////        return new ResponseEntity(OAuth2Exception.create(OAuth2Exception.INVALID_REQUEST, null), HttpStatus.OK);
////    }
//
//}
//
