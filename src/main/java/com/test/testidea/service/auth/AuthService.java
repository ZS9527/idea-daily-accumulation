package com.test.testidea.service.auth;

import com.test.testidea.basic.Result;
import com.test.testidea.param.auth.AuthAccountLoginParam;
import com.test.testidea.param.auth.AuthGetMobileCodeParam;
import com.test.testidea.param.auth.AuthMobileLoginParam;
import com.test.testidea.vo.auth.AuthLoginVo;
import javax.servlet.http.HttpServletRequest;

/**
 * 用户认证服务
 *
 * @author fangzhimin
 * @version 0.1
 * @date 2018/9/6 11:22
 */

public interface AuthService {

    /**
     * 检查Token是否有效
     *
     * @param token 令牌
     * @return 检查结果
     */
    Result verify(String token);

    /**
     * 账号密码登陆获取Token
     * @param request HttpServletRequest
     * @param param {@link AuthAccountLoginParam}
     * @return 登录结果
     */
    Result<AuthLoginVo> accountLogin(HttpServletRequest request, AuthAccountLoginParam param);

    /**
     * 手机号验证码登陆获取Token
     * @param request HttpServletRequest
     * @param param {@link AuthMobileLoginParam}
     * @return 登录结果
     */
    Result<AuthLoginVo> mobileLogin(HttpServletRequest request, AuthMobileLoginParam param);

    /**
     * 获取手机验证码
     * @param param {@link AuthGetMobileCodeParam}
     * @return 请求结果
     */
    Result getMobileCode(AuthGetMobileCodeParam param);

}
