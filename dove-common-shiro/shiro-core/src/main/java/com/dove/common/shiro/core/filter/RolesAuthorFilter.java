package com.dove.common.shiro.core.filter;

import cn.hutool.json.JSONUtil;
import com.dove.common.base.vo.CommonResult;
import org.apache.commons.lang.BooleanUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.RolesAuthorizationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.dove.common.shiro.core.enm.ShiroErrorEnum.AUTHOR_ERROR_ROLE;


public class RolesAuthorFilter extends RolesAuthorizationFilter {


    @Override
    public boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object mappedValue) throws IOException {
        Boolean afterFiltered = (Boolean) (servletRequest.getAttribute("anyRolesAuthFilter.FILTERED"));
        if (BooleanUtils.isTrue(afterFiltered)) return true;

        Subject subject = this.getSubject(servletRequest, servletResponse);
        String[] rolesArray = (String[]) ((String[]) mappedValue);
        if (rolesArray != null && rolesArray.length != 0) {
            //若当前用户是rolesArray中的任何一个，则有权限访问
            for (String role : rolesArray) {
                if (subject.hasRole(role)) {
                    return true;
                }
            }
            return false;
        }
        //没有角色限制，有权限访问
        return true;
    }


    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        HttpServletResponse httpResponse = WebUtils.toHttp(response);
        httpResponse.setCharacterEncoding("UTF-8");
        httpResponse.setContentType("application/json;charset=utf-8");
//        httpResponse.setStatus(HttpStatus.SC_UNAUTHORIZED);
        CommonResult failed = CommonResult.failed(AUTHOR_ERROR_ROLE.getCode(), AUTHOR_ERROR_ROLE.getMessage());
        httpResponse.getWriter().print(JSONUtil.toJsonStr(failed));
        return false;
    }

    @Override
    protected void postHandle(ServletRequest request, ServletResponse response) {
        request.setAttribute("anyRolesAuthFilter.FILTERED", true);
    }

}
