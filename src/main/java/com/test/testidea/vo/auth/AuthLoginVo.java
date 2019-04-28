package com.test.testidea.vo.auth;

import com.test.testidea.constant.Mock;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * 授权登录后的
 *
 * @author
 * @version 0.1
 * @date 2019/3/22 15:53
 */

@ApiModel
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthLoginVo implements Serializable {

    private static final long serialVersionUID = 7791855622159772854L;

    @ApiModelProperty(value = "Token", example = Mock.TOKEN)
    String token;

    @ApiModelProperty(value = "用户名", example = Mock.USER_NAME)
    String username;

    @ApiModelProperty(value = "真实姓名", example = Mock.USER_NAME)
    String realName;

}
