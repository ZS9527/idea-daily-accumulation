package com.test.testidea.domain.permission;

import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 权限接口
 *
 * @author fangzhimin
 * @date 2018/9/4 8:48
 */
@Repository
public interface PermissionRepository extends JpaRepository<Permission, Serializable>, JpaSpecificationExecutor<Permission> {
}
