package com.test.testidea.secruity;

import com.google.common.base.Strings;
import com.test.testidea.domain.user.User;
import com.test.testidea.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * TODO
 *
 * @author
 * @version 0.1
 * @date 2019/3/23 9:48
 */

@Component
public class AccountPasswordAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(
        Authentication authentication) throws AuthenticationException {
        String account = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        if (Strings.isNullOrEmpty(account)) {
            throw new BadCredentialsException("账号不能为空");
        }

        if (Strings.isNullOrEmpty(password)) {
            throw new BadCredentialsException("密码不能为空");
        }

        User user = userRepository.findByAccount(account);
        if (user == null) {
            throw new BadCredentialsException("用户不存在");
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("用户名或密码错误");
        }

        AccountPasswordAuthenticationToken auth = new AccountPasswordAuthenticationToken(account, password, user
            .toUserDetails().getAuthorities());
        auth.setDetails(authentication.getDetails());
        return auth;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return AccountPasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
