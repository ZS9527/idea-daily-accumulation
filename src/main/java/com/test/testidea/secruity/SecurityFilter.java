package com.test.testidea.secruity;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;

/**
 * 安全过滤器
 *
 * @author
 * @date 2018/9/4 16:54
 */

@Component
public class SecurityFilter extends AbstractSecurityInterceptor implements Filter {

    @Autowired
    InvocationSecurityMetadataSource invocationSecurityMetadataSource;

    @Autowired
    public void setAccessDecisionManager(CustomAccessDecisionManager customAccessDecisionManager) {
        super.setAccessDecisionManager(customAccessDecisionManager);
    }

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(
        ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        invoke(new FilterInvocation(request, response, chain));
    }

    @Override
    public void destroy() {

    }

    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return invocationSecurityMetadataSource;
    }

    public void invoke(FilterInvocation filterInvocation) throws IOException, ServletException {
        InterceptorStatusToken token = super.beforeInvocation(filterInvocation);
        try {
            filterInvocation.getChain().doFilter(filterInvocation.getRequest(), filterInvocation.getResponse());
        } finally {
            super.afterInvocation(token, null);
        }
    }

}
