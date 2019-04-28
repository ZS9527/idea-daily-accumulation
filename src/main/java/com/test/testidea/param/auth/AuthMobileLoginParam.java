package com.test.testidea.param.auth;

import com.test.testidea.constant.Mock;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
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
public class AuthMobileLoginParam implements Serializable {

    private static final long serialVersionUID = -3539956485037797901L;

    @ApiModelProperty(value = "手机号", example = Mock.MOBILE, required = true)
    @NotEmpty(message = "手机号不能为空")
    @Pattern(regexp = "^1[0-9]{10}$", message = "手机号码格式不正确")
    String mobile;

    @ApiModelProperty(value = "验证码", example = Mock.CODE, required = true)
    @NotEmpty(message = "验证码不能为空")
    String code;

    @ApiModelProperty(value = "不存在时是否新建账户", example = Mock.FALSE, dataType = "Boolean")
    @Builder.Default
    Boolean newCreate = false;

}
