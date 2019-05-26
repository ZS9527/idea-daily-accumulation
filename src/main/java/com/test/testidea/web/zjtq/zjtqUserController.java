package com.test.testidea.web.zjtq;

import com.test.testidea.service.zjtq.ZjtqUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO
 *
 * @author zhangshuai
 * @date 2019/5/5 16:21
 */

@Api(tags = "ZjtqUser")
@Validated
@RestController
@RequestMapping("/zjtqUser/")
public class zjtqUserController {

    @Autowired
    ZjtqUserService zjtqUserService;

    /**
     * 用户信息复制
     * @return
     */
    @ApiOperation("用户信息复制")
    @GetMapping("UserCopy")
    public void saveFile(){
        zjtqUserService.UserCopy();
    }

}
