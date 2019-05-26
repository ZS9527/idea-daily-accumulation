package com.test.testidea.service.user.impl;

import com.test.testidea.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 用户接口
 *
 * @author zhangshuai
 * @date 2019/5/3 9:44
 */

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    PasswordEncoder passwordEncoder;
}
