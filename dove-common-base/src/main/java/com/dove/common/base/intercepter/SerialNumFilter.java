package com.dove.common.base.intercepter;

import com.dove.common.util.holder.ThreadLocalKey;
import com.dove.common.util.holder.ThreadLocalMap;
import com.dove.common.util.random.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description:
 * @Auther: qingruizhu
 * @Date: 2020/4/25 19:04
 */
//@Configuration
@Order(0)
public class SerialNumFilter extends OncePerRequestFilter {
    Logger logger = LoggerFactory.getLogger(SerialNumFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        try {
            String requestURI = httpServletRequest.getRequestURI();
            StringBuffer requestURL = httpServletRequest.getRequestURL();
            String serialnum = RandomUtil.generateUniqCode().toString();
            ThreadLocalMap.put(ThreadLocalKey.SERIAL_NUMBER.name(), serialnum);
            logger.info("the request's serialnum : {}", serialnum);
        } catch (Exception e) {
            logger.error("generate serialnum error", e);
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
