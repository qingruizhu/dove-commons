package com.common.dove.oauth2.base.handler;

import cn.hutool.json.JSONUtil;
import com.dove.common.base.vo.CommonResult;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Auther: qingruizhu
 * @Date: 2020/8/17 11:58
 */

public class DoveAuthExceptionEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws ServletException {
        Map<String, Object> map = new HashMap<String, Object>();
        Throwable cause = authException.getCause();

        response.setStatus(HttpStatus.OK.value());
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        try {
            if (cause instanceof InvalidTokenException) {
//                response.getWriter().write(ResultJsonUtil.build(
//                        ResponseCodeConstant.REQUEST_FAILED,
//                        ResponseStatusCodeConstant.OAUTH_TOKEN_FAILURE,
//                        ResponseMessageConstant.OAUTH_TOKEN_ILLEGAL
//                ));
                response.getWriter().write(JSONUtil.toJsonStr(CommonResult.failed(401, "无效token")));
            } else {
//                response.getWriter().write(ResultJsonUtil.build(
//                        ResponseCodeConstant.REQUEST_FAILED,
//                        ResponseStatusCodeConstant.OAUTH_TOKEN_MISSING,
//                        ResponseMessageConstant.OAUTH_TOKEN_MISSING
//                ));
                response.getWriter().write(JSONUtil.toJsonStr(CommonResult.failed(401, "token 丢失")));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
