package com.test.testidea.web.permission;

import com.test.testidea.basic.Result;
import com.test.testidea.domain.permission.PermissionRepository;
import com.test.testidea.param.permission.PermissionAddParam;
import com.test.testidea.param.permission.PermissionDeletedParam;
import com.test.testidea.param.permission.PermissionEditParam;
import com.test.testidea.param.permission.PermissionFindParam;
import com.test.testidea.service.permission.PermissionService;
import com.test.testidea.vo.BasicTreeVo;
import com.test.testidea.vo.permission.PermissionFindPageVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.Collection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 权限管理
 *
 * @author ifzm
 * @version 0.1
 * @date 2019/4/28 10:01
 */

@Api(tags = "权限管理")
@Slf4j
@Validated
@RestController
@RequestMapping("/permission/")
public class PermissionController {

    @Autowired
    PermissionRepository permissionRepository;
    @Autowired
    PermissionService permissionService;

    /**
     * 添加权限
     * @param param {@link PermissionAddParam}
     * @return 结果
     */
    @ApiOperation("添加权限")
    @PostMapping("add")
    public Result add(PermissionAddParam param) {
        return permissionService.add(param);
    }

    /**
     * 删除权限
     * @param param {@link PermissionDeletedParam}
     * @return 结果
     */
    @ApiOperation("删除权限")
    @PostMapping("deleted")
    public Result deleted(PermissionDeletedParam param) {
        return permissionService.deleted(param);
    }

    /**
     * 修改权限
     * @param param {@link PermissionEditParam}
     * @return 结果
     */
    @ApiOperation("修改权限")
    @PostMapping("edit")
    public Result edit(PermissionEditParam param) {
        return permissionService.edit(param);
    }

    /**
     * 查询权限
     * @param param {@link PermissionFindParam}
     * @return 结果
     */
    @ApiOperation("查询权限")
    @PostMapping("find")
    public Result<PermissionFindPageVo> find(PermissionFindParam param) {
        return permissionService.find(param);
    }

    /**
     * 查询所有权限构建树状结果集
     * @return 结果集
     */
    @ApiOperation("查询权限树结构")
    @PostMapping("findAllToTree")
    public Result<Collection<BasicTreeVo>> findAllToTree() {
        return permissionService.findAllToTree();
    }

    /**
     * 导入前端路由
     * @return 结果
     */
    @ApiOperation("导入前端路由（临时）")
    @PostMapping("importRoutes")
    public Result importRoutes() {
//        try {
//            File file = new File("C:\\Users\\Administrator\\Desktop\\router.json");
//            JSONArray array = JSON.parseArray(String.join("", Files.readLines(file, Charset.defaultCharset())));
//            List<Permission> permissions = array.stream()
//                    .map(obj -> ((JSONObject) obj).toJavaObject(Permission.class))
//                    .collect(Collectors.toList());
//            permissionRepository.saveAll(permissions);
//            return Result.ok();
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
//            return Result.no(e.getMessage());
//        }

        return Result.no();
    }

}
