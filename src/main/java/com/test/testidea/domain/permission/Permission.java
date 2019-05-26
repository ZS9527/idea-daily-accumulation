package com.test.testidea.domain.permission;

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
import org.hibernate.annotaions.Comment;
import org.springframework.security.core.GrantedAuthority;

/**
 * 权限
 *
 * @author ifzm
 * @version 0.3
 * @date 2019/4/28 10:01
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

    @Comment("标签")
    String title;

    @Comment("图标")
    String icon;

    @Comment("权限URL")
    String path;

    @Comment("权限URL模式（eg: *,GET,POST,PUT...）")
    String method;

    @Comment("权限规则（1-前端路由,2-后端接口）")
    Integer rule;

    @Comment("权限类型（1-菜单,2-按钮）")
    Integer type;

    @Comment("按钮指令（当权限类型为按钮时指定，eg：add,delete,edit,query,get,enable,disable,import,export...）")
    String action;

    @Comment("描述")
    String description;

    @Comment("是否隐藏")
    Boolean hidden;

    @Comment("权限父级ID")
    Long pid;

    @Comment("树节点标识")
    String treeNode;

    @Override
    public String getAuthority() {
        return this.method + " " + this.path;
    }

}
