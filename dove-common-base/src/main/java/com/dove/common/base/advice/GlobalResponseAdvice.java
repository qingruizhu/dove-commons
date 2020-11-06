package com.dove.common.base.advice;

import com.dove.common.base.annotation.CommonResultAnnon;
import com.dove.common.base.enm.NeedCommonResult;
import com.dove.common.base.vo.CommonResult;
import com.dove.common.util.holder.ThreadLocalKey;
import com.dove.common.util.holder.ThreadLocalMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;


/**
 * @Description: 全局响应处理器
 * @Auther: qingruizhu
 * @Date: 2020/4/9 16:48
 */
@RestControllerAdvice
public class GlobalResponseAdvice implements ResponseBodyAdvice<Object> {

    Logger logger = LoggerFactory.getLogger(GlobalResponseAdvice.class);
    private static String LOGO_RESPONSE_PREFIX = "\n👇👇👇👇  返回报文 👇👇👇    \n \n";
    private static String LOGO_RESPONSE_SUFFIX = "\n \n👆👆👆👆   END  👆👆👆    ️️\n";

    public static String I_AM_OPENFEIGN = "I_AM_OPENFEIGN_REQUESTER";

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        if (openFeignRequest()) return false;
        Method method = methodParameter.getMethod();
        Class<?> returnType = method.getReturnType();
        //String和CommonResult不做处理
        if (returnType == String.class || returnType == CommonResult.class) {
            return false;
        }
        //方法上的注解
        if (method.isAnnotationPresent(CommonResultAnnon.class)) {
            CommonResultAnnon annotation = method.getAnnotation(CommonResultAnnon.class);
            return annotation.use();
        }
        //controller上的注解
        Class<?> controller = method.getDeclaringClass();
        if (controller.isAnnotationPresent(CommonResultAnnon.class)) {
            CommonResultAnnon annotation = controller.getAnnotation(CommonResultAnnon.class);
            return annotation.use();
        }
        return false;
//        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = requestAttributes.getRequest();
//        Boolean responseResultUsed = (Boolean) request.getAttribute(CommonResultIntercepter.RESPONSE_RESULT_ANNOTATION);
//        return null == responseResultUsed ? false : responseResultUsed;

    }

    private boolean openFeignRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String resultHead = request.getHeader(I_AM_OPENFEIGN);
        if (NeedCommonResult.NO.name().equals(resultHead)) return true;
        return false;
    }


    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        Object snm = ThreadLocalMap.get(ThreadLocalKey.SERIAL_NUMBER.name());
        StringBuilder sb = new StringBuilder(snm.toString());
        Object result;
        if (o instanceof CommonResult) {
            result = o;
        } else {
            result = CommonResult.success(o);
        }
        sb.append(LOGO_RESPONSE_PREFIX);
        logger.info(sb.append(result.toString()).append(LOGO_RESPONSE_SUFFIX).toString());
        return result;
    }

}
