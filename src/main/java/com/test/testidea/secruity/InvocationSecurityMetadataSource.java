package com.test.testidea.secruity;

import com.test.testidea.domain.permission.Permission;
import com.test.testidea.domain.permission.PermissionRepository;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;

/**
 * 权限属性获取拦截器
 *
 * @author
 * @date 2018/9/4 9:43
 */

@Service
public class InvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Autowired
    PermissionRepository permissionRepository;

    /**
     * 缓存所有权限
     */
    private Map<String, Collection<ConfigAttribute>> resourceDefine;

    private void loadResourceDefine() {
        if (resourceDefine != null) {
            return;
        }

        resourceDefine = permissionRepository.findAll().stream()
                .collect(Collectors.toMap(Permission::getAuthority,
                        permission -> Collections.singletonList(new SecurityConfig(permission.getAuthority()))));
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        loadResourceDefine();

        // 判断当前请求的接口是否在权限表中
        HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
        Iterator<String> iterator = resourceDefine.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            String[] params = key.split(" ");
            String url = params[1];
            String method = params[0];
            boolean checked = new AntPathRequestMatcher(url).matches(request) && ("ALL".equals(method) || method.equals(request.getMethod()));
            if (checked) {
                return resourceDefine.get(key);
            }
        }

        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        loadResourceDefine();
        return resourceDefine.values().stream().flatMap(Collection::stream).collect(Collectors.toList());
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

}
