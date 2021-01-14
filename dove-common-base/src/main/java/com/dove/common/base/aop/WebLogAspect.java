package com.dove.common.base.aop;

import com.dove.common.util.holder.ThreadLocalKey;
import com.dove.common.util.holder.ThreadLocalMap;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.concurrent.Executor;


@Component
@Aspect
public class WebLogAspect {

    private Logger LOGGER = LoggerFactory.getLogger(getClass());
    private static String LOG_PREFIX_REQUEST = "\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>----------➡️ 请求报文 ⬅️------------<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<\n";
    private static String LOG_SUFFIX_REQUEST = "\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>----------➡️  end  ⬅️------------<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<\n";
    @Autowired
    private Executor asyncExecutor;

    @Pointcut("execution(public * com..controller..*.*(..))")
    public void webLog() {
    }

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) {
        // 接收到请求
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Object snm = ThreadLocalMap.get(ThreadLocalKey.SERIAL_NUMBER.name());
        // 记录下请求内容
        asyncExecutor.execute(() -> {
            LOGGER.info(snm.toString());
            LOGGER.info(LOG_PREFIX_REQUEST);
            LOGGER.info("URL : " + request.getRequestURL().toString());
            LOGGER.info("HTTP_METHOD : " + request.getMethod());
            LOGGER.info("IP : " + request.getRemoteAddr());
            LOGGER.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
            LOGGER.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));
            LOGGER.info(LOG_SUFFIX_REQUEST);
        });


    }

    @AfterReturning(value = "webLog()", returning = "result")
    public void doAfterReturning(Object result) {
        // 处理完请求，返回内容
        asyncExecutor.execute(() -> {
            LOGGER.info("模拟->成功日志信息入库");
        });
    }

    @AfterThrowing(value = "webLog()", throwing = "e")
    public void doAfterThrowing(Throwable e) {
        // 处理完请求，返回内容
        asyncExecutor.execute(() -> LOGGER.error("模拟->失败日志信息入库"));
    }


}
