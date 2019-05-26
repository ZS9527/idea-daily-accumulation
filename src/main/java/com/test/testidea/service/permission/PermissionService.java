package com.test.testidea.service.permission;


import com.test.testidea.basic.Result;
import com.test.testidea.param.permission.PermissionAddParam;
import com.test.testidea.param.permission.PermissionDeletedParam;
import com.test.testidea.param.permission.PermissionEditParam;
import com.test.testidea.param.permission.PermissionFindParam;
import com.test.testidea.vo.BasicTreeVo;
import com.test.testidea.vo.permission.PermissionFindPageVo;
import java.util.Collection;

/**
 * 权限
 *
 * @author ifzm
 * @version 0.1
 * @date 2019/4/28 10:34
 */

public interface PermissionService {

    /**
     * 添加权限
     * @param param {@link PermissionAddParam}
     * @return 结果
     */
    Result add(PermissionAddParam param);

    /**
     * 删除权限
     * @param param {@link PermissionDeletedParam}
     * @return 结果
     */
    Result deleted(PermissionDeletedParam param);

    /**
     * 修改权限
     * @param param {@link PermissionEditParam}
     * @return 结果
     */
    Result edit(PermissionEditParam param);

    /**
     * 查询权限
     * @param param {@link PermissionFindParam}
     * @return 结果
     */
    Result<PermissionFindPageVo> find(PermissionFindParam param);

    /**
     * 查询所有权限构建树状结果集
     * @return 结果集
     */
    Result<Collection<BasicTreeVo>> findAllToTree();
}
