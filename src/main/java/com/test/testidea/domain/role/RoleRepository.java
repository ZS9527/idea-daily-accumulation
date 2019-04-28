package com.test.testidea.domain.role;

import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 角色接口
 *
 * @author fangzhimin
 * @date 2018/9/4 8:49
 */
public interface RoleRepository extends JpaRepository<Role, Serializable> {
}
