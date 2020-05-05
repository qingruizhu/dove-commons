package com.dove.common.base.intercepter;

import com.dove.common.base.annotation.CommonResultAnnon;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @Description: 拦截@CommonResult -> {@link CommonResultAnnon}
 * @Auther: qingruizhu
 * @Date: 2020/4/9 17:28
 */
public class CommonResultIntercepter extends HandlerInterceptorAdapter {
    public static String RESPONSE_RESULT_ANNOTATION = "RESPONSE-RESULT-ANNOTATION";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            Class<?> beanType = handlerMethod.getBeanType();
            if (method.isAnnotationPresent(CommonResultAnnon.class)) {/** 处理方法上的 -> @ResponseResult */
                CommonResultAnnon annotation = method.getAnnotation(CommonResultAnnon.class);
                request.setAttribute(RESPONSE_RESULT_ANNOTATION, annotation.use());
            } else if (beanType.isAnnotationPresent(CommonResultAnnon.class)) {/** 处理类上的 -> @ResponseResult */
                CommonResultAnnon annotation = beanType.getAnnotation(CommonResultAnnon.class);
                request.setAttribute(RESPONSE_RESULT_ANNOTATION, annotation.use());
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }
}
