package com.test.testidea.secruity;

import com.test.testidea.domain.permission.Permission;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

/**
 * 访问决策管理器
 *
 * @author fangzhimin
 * @date 2018/9/4 10:28
 */

@Component
public class CustomAccessDecisionManager implements AccessDecisionManager {

    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        // 校验用户权限
        HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
        for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
            if (grantedAuthority instanceof Permission) {
                Permission permission = (Permission) grantedAuthority;
                boolean checked = new AntPathRequestMatcher(permission.getPath()).matches(request) && ("*".equals(permission.getMethod()) || request.getMethod().equals(permission.getMethod()));
                if (checked) {
                    return;
                }
            }
        }

        throw new AccessDeniedException("no right");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

}
