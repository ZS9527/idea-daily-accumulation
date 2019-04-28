package com.test.testidea.service.auth.impl;

import com.google.common.base.Strings;
import com.test.testidea.basic.Result;
import com.test.testidea.core.MessageService;
import com.test.testidea.core.Redis;
import com.test.testidea.domain.user.User;
import com.test.testidea.domain.user.UserRepository;
import com.test.testidea.param.auth.AuthAccountLoginParam;
import com.test.testidea.param.auth.AuthGetMobileCodeParam;
import com.test.testidea.param.auth.AuthMobileLoginParam;
import com.test.testidea.secruity.AccountPasswordAuthenticationToken;
import com.test.testidea.secruity.JwtUser;
import com.test.testidea.secruity.JwtUtils;
import com.test.testidea.secruity.MobileCodeAuthenticationToken;
import com.test.testidea.service.auth.AuthService;
import com.test.testidea.util.CodeSegments;
import com.test.testidea.util.Commons;
import com.test.testidea.vo.auth.AuthLoginVo;
import java.time.LocalDateTime;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户认证服务
 *
 * @author fangzhimin
 * @date 2018/9/6 11:22
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class AuthServiceImpl implements AuthService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    Redis redis;
    @Autowired
    MessageService messageService;

    @Override
    public Result verify(String token) {
        if (!Strings.isNullOrEmpty(token)) {
            String username = JwtUtils.getUsernameFromToken(token);
            User user = userRepository.findByUsername(username);
            if (user != null && JwtUtils.validateToken(token, user.toUserDetails())) {
                return Result.ok();
            }
        }

        return Result.no();
    }

    @Override
    public Result<AuthLoginVo> accountLogin(HttpServletRequest request, AuthAccountLoginParam param) {
        // 校验用户名密码
        AccountPasswordAuthenticationToken asToken = new AccountPasswordAuthenticationToken(param.getAccount(), param.getPassword());
        final Authentication authentication = authenticationManager.authenticate(asToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return login(request, param.getAccount());
    }

    @Override
    public Result<AuthLoginVo> mobileLogin(HttpServletRequest request, AuthMobileLoginParam param) {
        String mobile = param.getMobile();
        String code = param.getCode();

        // 不存在时是否新建账户
        if (param.getNewCreate()) {
            User user = userRepository.findByMobile(mobile);
            if (user == null) {
                CodeSegments.verificationMobileCode(mobile, code, redis);

                // 将数据创建时间推到过去5秒钟
                // Fix: Java LocalDateTime to sql.Timestamp 可能会出现1秒内时间差
                LocalDateTime time = LocalDateTime.now().minusSeconds(5);

                user = User.builder()
                        .enabled(true)
                        .mobile(mobile)
                        .username(mobile)
                        .authorityUpdateTime(time)
                        .activationTime(time)
                        .lastUpdateTime(time)
                        .createTime(time)
                        .lastPasswordResetTime(time)
                        .build();
                userRepository.saveAndFlush(user);
            }
        }

        // 校验手机号与密码
        MobileCodeAuthenticationToken mcToken = new MobileCodeAuthenticationToken(mobile, code);
        final Authentication authentication = authenticationManager.authenticate(mcToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return login(request, mobile);
    }

    @Override
    public Result getMobileCode(AuthGetMobileCodeParam param) {
        return messageService.sendSmsCode(param.getMobile());
    }

    private Result<AuthLoginVo> login(HttpServletRequest request, String account) {
        // 更新用户信息
        User user = userRepository.findByAccount(account);
        user.setLastLoginIp(Commons.getClientIpAddress(request));
        user.setLastLoginTime(LocalDateTime.now());
        user.setLastUpdateTime(LocalDateTime.now());
        userRepository.saveAndFlush(user);

        // 生成Token
        final JwtUser userDetails = user.toUserDetails();
        AuthLoginVo vo = AuthLoginVo.builder()
                .username(userDetails.getUsername())
                .realName(userDetails.getRealName())
                .token(JwtUtils.generateToken(userDetails))
                .build();
        return Result.ok(vo);
    }

}
