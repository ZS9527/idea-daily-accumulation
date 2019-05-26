package com.test.testidea.vo.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.test.testidea.constant.Mock;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * 授权登录后的用户信息及权限信息
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
public class UserGetInfoVo implements Serializable {

    private static final long serialVersionUID = 7791855622159772854L;

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

    @ApiModelProperty(value = "最后登录地址", example = Mock.IP)
    String lastLoginIp;

    @ApiModelProperty(value = "头像", example = Mock.IMAGE)
    String avatar;

    @ApiModelProperty(value = "组织id", example = Mock.IMAGE)
    Long organizationId;

    @ApiModelProperty(value = "组织名字", example = Mock.IMAGE)
    String organizationName;

    @ApiModelProperty(value = "角色集合", example = "[\"管理员\"]")
    @Builder.Default
    Set<String> roles = new HashSet<>();

    @ApiModelProperty(value = "权限集合", example = "[]")
    @Builder.Default
    Set<Route> permissions = new HashSet<>();

    @ApiModel("权限")
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Route implements Serializable {

        private static final long serialVersionUID = 5898156277983355941L;

        @ApiModelProperty(value = "ID", example = "1")
        Long id;

        @ApiModelProperty(value = "名称", example = "dashboard")
        String name;

        @ApiModelProperty(value = "Path", example = "/dashboard")
        String path;

        @ApiModelProperty(value = "图标", example = "icon")
        String icon;

        @ApiModelProperty(value = "标签", example = "控制台")
        String title;

        @ApiModelProperty(value = "是否隐藏", example = "false")
        Boolean hidden;

        @JsonIgnore
        @ApiModelProperty(value = "类型（1-菜单,2-按钮）", example = "1")
        Integer type;

        @JsonIgnore
        @ApiModelProperty(value = "按钮指令（当权限类型为按钮时指定，eg：add,delete,edit,query,get,enable,disable,import,export...）", example = "add")
        String action;

        @ApiModelProperty(value = "子权限集合", example = "[]")
        Set<Route> children;

        @ApiModelProperty(value = "按钮集合", example = "[\"add\"]")
        Set<String> actions;

    }

}
