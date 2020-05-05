package com.dove.common.shiro.core.holder;

import com.dove.common.shiro.core.credential.ShiroUser;
import com.dove.common.shiro.core.exception.DoveShiroException;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.dove.common.shiro.core.enm.ShiroErrorEnum.SSO_USERHOLDER_ERROR;


/**
 * @Description: 齁住user
 * @Auther: qingruizhu
 * @Date: 2020/4/30 13:41
 */
public class UserHolder {
    private static Logger LOGGER = LoggerFactory.getLogger(UserHolder.class);

    public static Long getUid() {
        Long id = getUser().getId();
        if (null == id) throw new DoveShiroException(SSO_USERHOLDER_ERROR);
        return id;
    }

    public static String getUname() {
        String username = getUser().getUsername();
        if (null == username || "".equals(username)) throw new DoveShiroException(SSO_USERHOLDER_ERROR);
        return username;
    }

    public static ShiroUser getUser() {
        try {
            ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
            if (null == shiroUser) throw new DoveShiroException(SSO_USERHOLDER_ERROR);
            return shiroUser;
        } catch (Exception e) {
            LOGGER.error("UserHolder#getUser异常", e);
            throw new DoveShiroException(SSO_USERHOLDER_ERROR);
        }
    }
}
