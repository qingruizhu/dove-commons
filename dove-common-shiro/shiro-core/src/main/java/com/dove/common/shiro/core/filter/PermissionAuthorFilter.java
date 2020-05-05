package com.dove.common.shiro.core.filter;

import cn.hutool.json.JSONUtil;
import com.dove.common.base.vo.CommonResult;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.dove.common.shiro.core.enm.ShiroErrorEnum.AUTHOR_ERROR_PERMISSION;


public class PermissionAuthorFilter extends PermissionsAuthorizationFilter {


    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        HttpServletResponse httpResponse = WebUtils.toHttp(response);
        httpResponse.setCharacterEncoding("UTF-8");
        httpResponse.setContentType("application/json;charset=utf-8");
//        httpResponse.setStatus(HttpStatus.SC_UNAUTHORIZED);
        CommonResult failed = CommonResult.failed(AUTHOR_ERROR_PERMISSION.getCode(), AUTHOR_ERROR_PERMISSION.getMessage());
        httpResponse.getWriter().print(JSONUtil.toJsonStr(failed));
        return false;
    }

    @Override
    protected void postHandle(ServletRequest request, ServletResponse response) {
        request.setAttribute("anyRolesAuthFilter.FILTERED", true);
    }

}
