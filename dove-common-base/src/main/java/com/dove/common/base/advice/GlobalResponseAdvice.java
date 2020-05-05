package com.dove.common.base.advice;

import com.dove.common.base.intercepter.CommonResultIntercepter;
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
    private static String LOGO_RESPONSE_PREFIX = "\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>----------返回报文------------<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<\n    ####         ####\n   #   #        #   #\n  #####        #####\n #            #\n#            #\n\n";
    private static String LOGO_RESPONSE_SUFFIX = "\n\n      #        #\n######################\n#         #          #\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>------------END-------------<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<";


    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        Method method = methodParameter.getMethod();
        Class<?> returnType = method.getReturnType();
        //String和CommonResult不做处理
        if (returnType == String.class || returnType == CommonResult.class) {
            return false;
        }
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        Boolean responseResultUsed = (Boolean) request.getAttribute(CommonResultIntercepter.RESPONSE_RESULT_ANNOTATION);
        return null == responseResultUsed ? false : responseResultUsed;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        CommonResult<Object> result = CommonResult.success(o);
        Object snm = ThreadLocalMap.get(ThreadLocalKey.SERIAL_NUMBER.name());
        StringBuilder sb = new StringBuilder(snm.toString());
        sb.append(LOGO_RESPONSE_PREFIX);
        logger.info(sb.append(result.toString()).append(LOGO_RESPONSE_SUFFIX).toString());
        return result;
    }


}
