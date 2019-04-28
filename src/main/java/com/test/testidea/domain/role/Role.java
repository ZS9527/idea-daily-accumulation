package com.test.testidea.domain.role;

import com.test.testidea.domain.permission.Permission;
import org.hibernate.annotaions.Comment;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * 角色
 *
 * @author fangzhimin
 * @date 2018/9/3 15:45
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Comment("角色")
@Table(name = "SYSTEM_ROLE")
public class Role implements Serializable {

    private static final long serialVersionUID = 910404402503275957L;

    @Comment("主键")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Comment("名称")
    String name;

    @Comment("描述")
    String description;

    @Builder.Default
    @Comment("权限集合")
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    Set<Permission> permissions = new HashSet<>();

}
