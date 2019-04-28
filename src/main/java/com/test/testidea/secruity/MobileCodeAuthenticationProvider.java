package com.test.testidea.secruity;

import com.google.common.base.Strings;
import com.test.testidea.constant.RedisKey;
import com.test.testidea.core.Redis;
import com.test.testidea.domain.user.User;
import com.test.testidea.domain.user.UserRepository;
import com.test.testidea.util.CodeSegments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 * TODO
 *
 * @author
 * @version 0.1
 * @date 2019/3/23 9:48
 */

@Component
public class MobileCodeAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    UserRepository userRepository;
    @Autowired
    Redis redis;

    @Override
    public Authentication authenticate(
        Authentication authentication) throws AuthenticationException {
        String mobile = (String) authentication.getPrincipal();
        String code = (String) authentication.getCredentials();

        if (Strings.isNullOrEmpty(mobile)) {
            throw new BadCredentialsException("手机号不能为空");
        }

        if (Strings.isNullOrEmpty(code)) {
            throw new BadCredentialsException("验证码不能为空");
        }

        User user = userRepository.findByMobile(mobile);
        if (user == null) {
            throw new BadCredentialsException("用户不存在");
        }

        // 校验验证码后删除验证码
        CodeSegments.verificationMobileCode(mobile, code, redis);
        redis.hdel(RedisKey.SMS_VERIFICATION_CODE, mobile);

        MobileCodeAuthenticationToken auth = new MobileCodeAuthenticationToken(mobile, code, user
            .toUserDetails().getAuthorities());
        auth.setDetails(authentication.getDetails());
        return auth;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return MobileCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
