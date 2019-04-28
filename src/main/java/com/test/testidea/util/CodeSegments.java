package com.test.testidea.util;

import com.google.common.base.Strings;
import com.test.testidea.constant.RedisKey;
import com.test.testidea.core.Redis;
import org.springframework.security.authentication.BadCredentialsException;

/**
 * 抽离一些可公用的代码片段
 *
 * @author ifzm
 * @version 0.1
 * @date 2019/3/26 20:07
 */

public class CodeSegments {

    /**
     * 验证手机号验证码是否匹配
     * @param mobile 手机号
     * @param code 验证码
     * @param redis redis
     */
    public static void verificationMobileCode(String mobile, String code, Redis redis) {
        String verificationCode = String.valueOf(redis.hget(RedisKey.SMS_VERIFICATION_CODE, mobile));
        if (Strings.isNullOrEmpty(verificationCode) || !verificationCode.equals(code)) {
            throw new BadCredentialsException("验证码错误");
        }
    }

}
