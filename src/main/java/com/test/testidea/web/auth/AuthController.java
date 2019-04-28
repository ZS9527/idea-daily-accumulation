package com.test.testidea.web.auth;

import com.google.common.base.Strings;
import com.test.testidea.basic.Result;
import com.test.testidea.param.auth.AuthAccountLoginParam;
import com.test.testidea.param.auth.AuthGetMobileCodeParam;
import com.test.testidea.param.auth.AuthMobileLoginParam;
import com.test.testidea.secruity.JwtUtils;
import com.test.testidea.service.auth.AuthService;
import com.test.testidea.vo.auth.AuthLoginVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.util.Collections;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 身份验证控制器（包含登录/注册）
 *
 * @author
 * @version 0.1
 * @date 2018/9/4 17:34
 */

@Api(tags = "Authentication")
@Validated
@RestController
@RequestMapping("/business/auth/")
public class AuthController {

    @Autowired
    AuthService authService;

    /**
     * 账号密码登录
     * @param request HttpServletRequest
     * @param param {@link AuthAccountLoginParam}
     * @return 登录结果
     */
    @ApiOperation("账号密码登录")
    @ApiImplicitParam(name = JwtUtils.TOKEN_HEADER, paramType = "header", dataType = "string", readOnly = true)
    @PostMapping("login/account")
    public Result<AuthLoginVo> accountLogin(HttpServletRequest request, @Valid AuthAccountLoginParam param) {
        return authService.accountLogin(request, param);
    }

    /**
     * 手机号验证码登录
     * @param request HttpServletRequest
     * @param param {@link AuthMobileLoginParam}
     * @return 登录结果
     */
    @ApiOperation("手机号验证码登录")
    @ApiImplicitParam(name = JwtUtils.TOKEN_HEADER, paramType = "header", dataType = "string", readOnly = true)
    @PostMapping("login/mobile")
    public Result<AuthLoginVo> mobileLogin(HttpServletRequest request, @Valid AuthMobileLoginParam param) {
        return authService.mobileLogin(request, param);
    }

    /**
     * 获取手机验证码
     * @param param {@link AuthGetMobileCodeParam}
     * @return 请求结果
     */
    @ApiOperation("获取手机验证码")
    @ApiImplicitParam(name = JwtUtils.TOKEN_HEADER, paramType = "header", dataType = "string", readOnly = true)
    @PostMapping("getMobileCode")
    public Result getMobileCode(@Valid AuthGetMobileCodeParam param) {
        return authService.getMobileCode(param);
    }

    /**
     * 更新Token
     * @param request HttpServletRequest
     * @return 更新结果
     */
    @ApiOperation("更新Token")
    @PostMapping("refresh")
    public Result refresh(HttpServletRequest request) {
        String token = JwtUtils.getToken(request);
        if (Strings.isNullOrEmpty(token)) {
            return Result.no();
        }

        return Result.ok(Collections.singletonMap("token", JwtUtils.refreshToken(token)));
    }

    /**
     * 检查Token是否有效
     * @param request HttpServletRequest
     * @return 检查结果
     */
    @ApiOperation("检查Token是否有效")
    @PostMapping("verify")
    public Result verify(HttpServletRequest request) {
        return authService.verify(JwtUtils.getToken(request));
    }

}
