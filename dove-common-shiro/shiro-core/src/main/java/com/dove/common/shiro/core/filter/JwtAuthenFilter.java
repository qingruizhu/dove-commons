package com.dove.common.shiro.core.filter;

import cn.hutool.json.JSONUtil;
import com.dove.common.base.enm.NeedCommonResult;
import com.dove.common.base.vo.CommonResult;
import com.dove.common.jwt.util.JwtTokenUtil;
import com.dove.common.redis.service.RedisService;
import com.dove.common.shiro.core.token.JWTToken;
import com.dove.common.shiro.core.util.RedisKeyShiro;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Locale;

import static com.dove.common.base.advice.GlobalResponseAdvice.I_AM_OPENFEIGN;
import static com.dove.common.shiro.core.enm.ShiroErrorEnum.AUTHEN_ERROR_TOKEN;


public class JwtAuthenFilter extends BasicHttpAuthenticationFilter {
    private final Logger log = LoggerFactory.getLogger(JwtAuthenFilter.class);

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String JWT_AUTHZSCHEME = "Bearer";

    private JwtTokenUtil jwtTokenUtil;
    private RedisService redisService;

    public JwtAuthenFilter(JwtTokenUtil jwtTokenUtil, RedisService redisService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.redisService = redisService;
//        this.setLoginUrl("/login");
    }

    /**
     * 1.前置
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name()))
            //对于OPTION请求做拦截 -> 不做token校验
            return false;
        return super.preHandle(request, response);
    }

    /**
     * 2.验证
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (this.isLoginRequest(request, response)) return true;//登录验证直接放行
        Boolean afterFiltered = (Boolean) (request.getAttribute("jwtShiroFilter.FILTERED"));
        if (BooleanUtils.isTrue(afterFiltered)) return true;

        if (!this.isJwtAttempt(request)) return false;
        boolean allowed = false;
        try {
            // 进行Shiro的JWTShiroRealm
            allowed = this.executeLogin(request, response);
        } catch (IllegalStateException e) { //not found any token
            log.error("Not found any token");
        } catch (Exception e) {
            log.error("Error occurs when login", e);
        }
        return allowed || super.isPermissive(mappedValue);
    }

    /**
     * 3.尝试验证->主要校验token是否存在
     */
    private boolean isJwtAttempt(ServletRequest request/*, ServletResponse response*/) {
        String authzHeader = this.getAuthzHeader(request);
        if (null == authzHeader) {
            log.error("token 不存在");
            return false;
        }
        String scheme = JWT_AUTHZSCHEME.toLowerCase(Locale.ENGLISH);
        return authzHeader.toLowerCase(Locale.ENGLISH).startsWith(scheme);
    }


//    @Override
//    protected String getAuthzHeader(ServletRequest request) {
//        HttpServletRequest httpRequest = WebUtils.toHttp(request);
//        String header = httpRequest.getHeader(AUTHORIZATION_HEADER);
//        return StringUtils.removeStart(header, "Bearer ");
//    }

    /**
     * 4.{@link #executeLogin(ServletRequest, ServletResponse)}
     */
    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) {
        String allToken = this.getAuthzHeader(servletRequest);
        String jwtToken = StringUtils.removeStart(allToken, JWT_AUTHZSCHEME).trim();
//      token过期提到matcher里面验证
//        if (JwtTokenUtil.isTokenExpired(jwtToken)) {
//            log.error("token 过期");
//            return null;
//        }
        return null == jwtToken || "".equals(jwtToken) ? null : new JWTToken(jwtToken);
    }

    /**
     * 5.成功
     */
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        if (openFeignRequest(request)) return true; // openfeign调用不刷新token
        if (token instanceof JWTToken) {
            JWTToken jwtToken = (JWTToken) token;
            String oldToken = jwtToken.getToken();
            boolean shouldRefresh = shouldRefreshToken(JwtTokenUtil.getIssuedAt(oldToken));
            if (shouldRefresh) {
                String username = JwtTokenUtil.getSubject(oldToken);
                log.info("即将刷新【{}】的token", username);
                String issueTimeKey = RedisKeyShiro.tokenIssueTime(username);
                long issueTime = System.currentTimeMillis();
                String newToken = jwtTokenUtil.sign(username, issueTime);
                redisService.set(issueTimeKey, issueTime, jwtTokenUtil.getExpiration());
                //重置缓存user的过期时间
                if (redisService.exist(RedisKeyShiro.user(username))) {
                    redisService.expire(RedisKeyShiro.user(username), jwtTokenUtil.getExpiration());
                } else {
                    redisService.set(RedisKeyShiro.user(username), subject.getPrincipal(), jwtTokenUtil.getExpiration());
                }
                WebUtils.toHttp(response).setHeader(AUTHORIZATION_HEADER, JWT_AUTHZSCHEME + " " + newToken);
                log.info("刷新token成功");
            }
        }
        return true;
    }

    private boolean shouldRefreshToken(Date issueAt) {
        long issuTime = issueAt.getTime();
        long refreshTime = (jwtTokenUtil.getExpiration() * 1000) >> 2;
        long minus = System.currentTimeMillis() - refreshTime;
        boolean refresh = minus > issuTime;
        return refresh;
    }

    /**
     * 6.登录验证失败后进行
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        log.error("Validate token fail, token:{}, error:{}", token.toString(), e.getMessage());
        return false;
    }


    /**
     * 7.验证不通过后执行
     */
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        log.error("token 校验失败");
        if (openFeignRequest(servletRequest)) return false;
        HttpServletResponse httpResponse = WebUtils.toHttp(servletResponse);
        httpResponse.setCharacterEncoding("UTF-8");
        httpResponse.setContentType("application/json;charset=UTF-8");
//        httpResponse.setStatus(HttpStatus.SC_NON_AUTHORITATIVE_INFORMATION);
        CommonResult failed = CommonResult.failed(AUTHEN_ERROR_TOKEN);
        httpResponse.getWriter().print(JSONUtil.toJsonStr(failed));
        return false;
    }

    private boolean openFeignRequest(ServletRequest servletRequest) {
        HttpServletRequest httpServletRequest = WebUtils.toHttp(servletRequest);
        String header = httpServletRequest.getHeader(I_AM_OPENFEIGN);
        if (NeedCommonResult.NO.name().equals(header)) {
            return true;
        }
        return false;
    }

    /**
     * 8.后置
     */
    @Override
    protected void postHandle(ServletRequest request, ServletResponse response) {
        this.fillCorsHeader(WebUtils.toHttp(request), WebUtils.toHttp(response));
        request.setAttribute("jwtShiroFilter.FILTERED", true);
    }

    /**
     * 8.设置跨域
     */
    private void fillCorsHeader(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,HEAD");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
    }
}
