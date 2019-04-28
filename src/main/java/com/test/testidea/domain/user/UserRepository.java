package com.test.testidea.domain.user;

import java.io.Serializable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * 用户接口
 *
 * @author fangzhimin
 * @date 2018/9/4 8:50
 */
@Repository
public interface UserRepository extends JpaRepository<User, Serializable> {

    /**
     * 根据用户名称查询用户信息
     *
     * @param username 用户名
     * @return 用户信息
     */
    User findByUsername(String username);

    /**
     * 根据手机号查询用户信息
     *
     * @param mobile 手机号
     * @return 用户信息
     */
    User findByMobile(String mobile);

    /**
     * 根据用户名/手机号/邮箱查询用户信息
     *
     * @param account 账号
     * @return 用户信息
     */
    @Query("SELECT u FROM User u WHERE u.username = ?1 OR u.mobile = ?1 OR u.email = ?1")
    User findByAccount(String account);

}
