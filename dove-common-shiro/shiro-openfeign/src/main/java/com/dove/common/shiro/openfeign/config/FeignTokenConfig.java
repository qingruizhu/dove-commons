package com.dove.common.shiro.openfeign.config;


import com.dove.common.base.advice.GlobalResponseAdvice;
import com.dove.common.base.enm.NeedCommonResult;
import com.dove.common.shiro.core.filter.JwtAuthenFilter;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description: 处理token传递+消除commonresult影响
 * @Auther: qingruizhu
 * @Date: 2020/5/8 14:47
 */
@Configuration
@ConditionalOnClass(JwtAuthenFilter.class)
public class FeignTokenConfig implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (null != requestAttributes) {
            //转发token
            String secet;
            HttpServletResponse response = requestAttributes.getResponse();
            String refreshToken = response.getHeader(JwtAuthenFilter.AUTHORIZATION_HEADER);
            if (null != refreshToken && !"".equals(refreshToken)) {
                secet = refreshToken;
            } else {
                HttpServletRequest request = requestAttributes.getRequest();
                secet = request.getHeader(JwtAuthenFilter.AUTHORIZATION_HEADER);
            }
            requestTemplate.header(JwtAuthenFilter.AUTHORIZATION_HEADER, secet);
        }
        //openfeign标识
        requestTemplate.header(GlobalResponseAdvice.I_AM_OPENFEIGN, NeedCommonResult.NO.name());
    }
}
