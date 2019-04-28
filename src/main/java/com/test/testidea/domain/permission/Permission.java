package com.test.testidea.domain.permission;

import org.hibernate.annotaions.Comment;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;

/**
 * 权限
 *
 * @author fangzhimin
 * @date 2018/9/3 17:08
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Comment("权限")
@Table(name = "SYSTEM_PERMISSION")
public class Permission implements GrantedAuthority, Serializable {

    private static final long serialVersionUID = -2001221843529000953L;

    @Comment("主键")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Comment("名称")
    String name;

    @Comment("描述")
    String description;

    @Comment("权限URL")
    String url;

    @Comment("权限URL模式（eg: GET,POST,PUT...）")
    String method;

    @Comment("权限父级ID")
    Long pid;

    @Comment("树节点标识")
    String treeNode;

    @Override
    public String getAuthority() {
        return this.method + " " + this.url;
    }

}
