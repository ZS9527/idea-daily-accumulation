package com.test.testidea.vo.user;

import com.test.testidea.constant.Mock;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * 用户的所有信息
 *
 * @author ifzm
 * @version 0.1
 * @date 2019/3/22 15:53
 */

@ApiModel
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserFindData {
    private static final long serialVersionUID = 7791855622159772854L;

    @ApiModelProperty(value = "主键", example = Mock.USER_NAME)
    Long id;

    @ApiModelProperty(value = "用户名", example = Mock.USER_NAME)
    String username;

    @ApiModelProperty(value = "真实名称", example = Mock.REAL_NAME)
    String realName;

    @ApiModelProperty(value = "手机号码", example = Mock.MOBILE)
    String mobile;

    @ApiModelProperty(value = "邮箱", example = Mock.EMAIL)
    String email;

    @ApiModelProperty(value = "职称", example = Mock.REAL_NAME)
    String title;

    @ApiModelProperty(value = "启用状态", example = "true")
    Boolean enabled;

    @ApiModelProperty(value = "最后登录地址", example = Mock.IP)
    String lastLoginIp;

    @ApiModelProperty(value = "激活时间",example = Mock.DATETIME)
    LocalDateTime activationTime;

    @ApiModelProperty(value = "权限更新时间",example = Mock.DATETIME)
    LocalDateTime authorityUpdateTime;

    @ApiModelProperty(value = "最后重置密码时间",example = Mock.DATETIME)
    LocalDateTime lastPasswordResetTime;

    @ApiModelProperty(value = "最后登录时间",example = Mock.DATETIME)
    LocalDateTime lastLoginTime;

    @ApiModelProperty(value = "最后更新信息时间",example = Mock.DATETIME)
    LocalDateTime lastUpdateTime;

    @ApiModelProperty(value = "创建时间",example = Mock.DATETIME)
    LocalDateTime createTime;

    @ApiModelProperty(value = "年龄",example = "25")
    Integer age;

    @ApiModelProperty(value = "性别",example = Mock.DATETIME)
    Integer gender;

    @ApiModelProperty(value = "地址",example = Mock.DATETIME)
    String address;

    @ApiModelProperty(value = "角色id",example = Mock.DATETIME)
    List<Long> roleId;

    @ApiModelProperty(value = "角色名称",example = Mock.DATETIME)
    List<String> roleName;

    @ApiModelProperty(value = "组织id",example = Mock.DATETIME)
    Long organizationId;

    @ApiModelProperty(value = "组织父id",example = Mock.DATETIME)
    Long organizationPid;

}
