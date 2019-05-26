package com.test.testidea.util;

import com.test.testidea.domain.permission.Permission;
import com.test.testidea.vo.BasicTreeVo;
import com.test.testidea.vo.user.UserGetInfoVo;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 权限工具类
 *
 * @author ifzm
 * @version 0.1
 * @date 2019/3/22 11:19
 */

public class Permissions {

    /**
     * 根节点权限ID（虚构）
     */
    private static final Long ROOT_PERMISSION_ID = 0L;

    /**
     * 按钮权限类型
     */
    private static final Integer PERMISSION_TYPE_ACTION = 2;

    private Permissions() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 将权限信息构建成前端权限树
     * @param permissions 权限集合
     * @return 权限树
     */
    public static List<BasicTreeVo> toTrees(Collection<Permission> permissions) {
        return toTrees(ROOT_PERMISSION_ID, permissions);
    }

    /**
     * 递归构建权限树
     * @param id 节点ID
     * @param permissions 权限集合
     * @return 权限树
     */
    private static List<BasicTreeVo> toTrees(Long id, Collection<Permission> permissions) {
        return permissions.stream()
                .filter(permission -> id.equals(permission.getPid()))
                .map(permission -> {
                    List<BasicTreeVo> children = toTrees(permission.getId(), permissions);
                    boolean isLeaf = Commons.isNullOrEmpty(children);
                    return BasicTreeVo.builder()
                            .isLeaf(isLeaf)
                            .children(isLeaf ? null : children)
                            .key(permission.getId())
                            .icon(permission.getIcon())
                            .title(permission.getTitle())
                            .build();
                })
                .collect(Collectors.toList());
    }

    /**
     * 将权限转换为前端路由集合
     * @param permissions 权限集合
     * @return 路由集合
     */
    public static Set<UserGetInfoVo.Route> toRoutes(Collection<Permission> permissions) {
        return toRoutes(ROOT_PERMISSION_ID, permissions);
    }

    /**
     * 将权限转换为前端路由集合
     * @param id 节点ID
     * @param permissions 权限集合
     * @return 路由集合
     */
    private static Set<UserGetInfoVo.Route> toRoutes(Long id, Collection<Permission> permissions) {
        return permissions.stream()
                .filter(permission -> id.equals(permission.getPid()))
                .map(permission -> {
                    Set<UserGetInfoVo.Route> routes = toRoutes(permission.getId(), permissions);

                    Set<UserGetInfoVo.Route> children = routes.stream()
                            .filter(route -> !PERMISSION_TYPE_ACTION.equals(route.getType()))
                            .collect(Collectors.toSet());
                    Set<String> actions = routes.stream()
                            .filter(route -> PERMISSION_TYPE_ACTION.equals(route.getType()))
                            .map(UserGetInfoVo.Route::getAction)
                            .collect(Collectors.toSet());

                    return UserGetInfoVo.Route.builder()
                            .id(permission.getId())
                            .icon(permission.getIcon())
                            .name(permission.getName())
                            .path(permission.getPath())
                            .type(permission.getType())
                            .title(permission.getTitle())
                            .hidden(permission.getHidden())
                            .action(permission.getAction())
                            .actions(Commons.isNullOrEmpty(actions) ? null : actions)
                            .children(Commons.isNullOrEmpty(children) ? null : children)
                            .build();
                })
                .collect(Collectors.toSet());
    }

}
