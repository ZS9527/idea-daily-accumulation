package com.test.testidea.web.user;

import com.test.testidea.service.user.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户有关接口
 *
 * @author zhangshuai
 * @date 2019/5/3 9:42
 */
@Api(tags = "User")
@Validated
@RestController
@RequestMapping("/user/")
public class UserController {

    @Autowired
    UserService userService;



}
