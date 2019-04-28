package com.test.testidea.param.auth;

import com.test.testidea.constant.Mock;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * 用户登录参数
 *
 * @author ifzm
 * @version 0.1
 * @date 2019/3/21 16:41
 */

@ApiModel
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthAccountLoginParam implements Serializable {

    private static final long serialVersionUID = -3539956485037797901L;

    @ApiModelProperty(value = "账号(用户名/手机号/邮箱)", example = Mock.USER_NAME, required = true)
    @NotEmpty(message = "账号不能为空")
    String account;

    @ApiModelProperty(value = "密码", example = Mock.PASSWORD, required = true)
    @NotEmpty(message = "密码不能为空")
    String password;

}
