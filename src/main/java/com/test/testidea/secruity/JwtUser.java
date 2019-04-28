package com.test.testidea.secruity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
import java.util.Collection;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * UserDetails
 *
 * @author
 * @date 2018/9/4 11:09
 */

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JwtUser implements UserDetails {
    private static final long serialVersionUID = 5640560948545264509L;

    /**
     * 真实姓名
     */
    String realName;

    /**
     * 登录名
     */
    String username;

    /**
     * 登录密码
     */
    @JsonIgnore
    String password;

    /**
     * 手机号
     */
    String mobile;

    /**
     * 邮件地址
     */
    String mail;

    /**
     * 是否启用
     */
    Boolean enabled;

    /**
     * 最后重置密码时间
     */
    LocalDateTime lastPasswordResetTime;

    /**
     * 权限更新时间
     */
    LocalDateTime authorityUpdateTime;

    /**
     * 权限集合
     */
    Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

}
