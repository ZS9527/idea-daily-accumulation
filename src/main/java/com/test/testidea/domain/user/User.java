package com.test.testidea.domain.user;

import com.test.testidea.domain.permission.Permission;
import com.test.testidea.domain.role.Role;
import com.test.testidea.secruity.JwtUser;
import org.hibernate.annotaions.Comment;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * 用户
 *
 * @author fangzhimin
 * @date 2018/9/3 15:27
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Comment("用户")
@Table(name = "BS_USER", indexes = {
        @Index(name = "IDX_USERNAME", columnList = "username", unique = true),
        @Index(name = "IDX_MOBILE", columnList = "mobile", unique = true),
        @Index(name = "IDX_EMAIL", columnList = "email", unique = true)
})
@ApiModel(value = "用户")
public class User implements Serializable {

    private static final long serialVersionUID = -5787847701210907511L;

    @Comment("主键")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Comment("真实姓名")
    @ApiModelProperty(value = "真实姓名")
    String realName;

    @Comment("用户名")
    @ApiModelProperty(value = "用户名")
    @Column(unique = true)
    String username;

    @Comment("密码")
    @ApiModelProperty(value = "密码")
    String password;

    @Comment("手机号")
    @ApiModelProperty(value = "手机号")
    @Column(unique = true)
    String mobile;

    @Comment("邮件地址")
    @ApiModelProperty(value = "邮件地址")
    @Column(unique = true)
    String email;

    @Comment("职称")
    @ApiModelProperty(value = "职称")
    String title;

    @Comment("是否启用")
    @ApiModelProperty(value = "是否启用")
    Boolean enabled;

    @Comment("最后登录IP地址")
    @ApiModelProperty(value = "最后登录IP地址")
    String lastLoginIp;

    @Comment("激活时间")
    @ApiModelProperty(value = "激活时间")
    LocalDateTime activationTime;

    @Comment("权限更新时间")
    @ApiModelProperty(value = "权限更新时间")
    LocalDateTime authorityUpdateTime;

    @Comment("最后重置密码时间")
    @ApiModelProperty(value = "最后重置密码时间")
    LocalDateTime lastPasswordResetTime;

    @Comment("最后登录时间")
    @ApiModelProperty(value = "最后登录时间")
    LocalDateTime lastLoginTime;

    @Comment("最后更新信息时间")
    @ApiModelProperty(value = "最后更新信息时间")
    LocalDateTime lastUpdateTime;

    @Comment("创建时间")
    @ApiModelProperty(value = "创建时间")
    LocalDateTime createTime;

    @Builder.Default
    @Comment("角色集合")
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    Set<Role> roles = new HashSet<>();

    /**
     * 验证码（非持久化）
     */
    @Transient
    String code;

    /**
     * token（非持久化）
     */
    @Transient
    String token;

    /**
     * 转换为 UserDetails
     * @return JwtUser
     */
    public JwtUser toUserDetails() {
        Set<Permission> permissions = this.getRoles().stream()
            .map(Role::getPermissions)
            .flatMap(Set::stream)
            .collect(Collectors.toSet());

        return JwtUser.builder()
            .enabled(this.enabled)
            .username(this.username)
            .password(this.password)
            .realName(this.realName)
            .mobile(this.mobile)
            .mail(this.email)
            .authorityUpdateTime(this.authorityUpdateTime)
            .lastPasswordResetTime(this.lastPasswordResetTime)
            .authorities(permissions)
            .build();
    }
}
