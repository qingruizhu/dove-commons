package com.dove.common.oauth2.resource;

import cn.hutool.json.JSONUtil;
import com.dove.common.base.vo.CommonResult;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description:
 * @Auther: qingruizhu
 * @Date: 2020/8/17 12:00
 */

public class DoveAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException)
            throws IOException, ServletException {
        response.setStatus(HttpStatus.OK.value());
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        try {
//            response.getWriter().write(ResultJsonUtil.build(
//                    ResponseCodeConstant.REQUEST_FAILED,
//                    ResponseStatusCodeConstant.OAUTH_TOKEN_DENIED,
//                    ResponseMessageConstant.OAUTH_TOKEN_DENIED
//            ));
            response.getWriter().write(JSONUtil.toJsonStr(CommonResult.failed(401, "无效token")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
