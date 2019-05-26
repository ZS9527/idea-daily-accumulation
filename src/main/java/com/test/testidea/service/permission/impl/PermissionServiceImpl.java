package com.test.testidea.service.permission.impl;

import com.google.common.base.Strings;
import com.test.testidea.basic.Result;
import com.test.testidea.domain.permission.Permission;
import com.test.testidea.domain.permission.PermissionRepository;
import com.test.testidea.param.permission.PermissionAddParam;
import com.test.testidea.param.permission.PermissionDeletedParam;
import com.test.testidea.param.permission.PermissionEditParam;
import com.test.testidea.param.permission.PermissionFindParam;
import com.test.testidea.service.permission.PermissionService;
import com.test.testidea.util.Commons;
import com.test.testidea.util.Permissions;
import com.test.testidea.vo.BasicTreeVo;
import com.test.testidea.vo.permission.PermissionFindPageVo;
import com.test.testidea.vo.permission.PermissionFindVo;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.criteria.Predicate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 权限管理
 *
 * @author ifzm
 * @version 0.1
 * @date 2019/4/28 10:59
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    PermissionRepository permissionRepository;

    @Override
    public Result add(PermissionAddParam param) {
        Permission parent = permissionRepository.getOne(param.getPid());
        Permission permission = new Permission();
        BeanUtils.copyProperties(param, permission);
        permissionRepository.save(permission);
        permission.setTreeNode(String.format("%s.%s", parent.getTreeNode(), permission.getId()));
        return Result.ok();
    }

    @Override
    public Result deleted(PermissionDeletedParam param) {
        Permission permission = permissionRepository.getOne(param.getId());
        permissionRepository.delete(permission);
        return Result.ok();
    }

    @Override
    public Result edit(PermissionEditParam param) {
        Permission permission = permissionRepository.getOne(param.getId());
        BeanUtils.copyProperties(param, permission);
        permissionRepository.save(permission);
        return Result.ok();
    }

    @Override
    public Result<PermissionFindPageVo> find(PermissionFindParam param) {
        // 排序、分页、模糊查询
        Sort sort = Commons.isNullOrEmpty(param.getProperties()) ? Sort.unsorted() : Sort.by(Sort.Direction.fromString(param.getDirection()), param.getProperties());
        PageRequest pageRequest = PageRequest.of(param.getPage(), param.getSize(), sort);
        Page<Permission> page = permissionRepository.findAll((root, query, cb) -> {
            List<Predicate> list = new ArrayList<>();

            if (param.getType() != null) {
                list.add(cb.equal(root.get("type"), param.getType()));
            }
            if (param.getRule() != null) {
                list.add(cb.equal(root.get("rule"), param.getRule()));
            }
            if (!Strings.isNullOrEmpty(param.getMethod())) {
                list.add(cb.equal(root.get("method"), param.getMethod()));
            }
            if (!Strings.isNullOrEmpty(param.getPath())) {
                list.add(cb.like(root.get("path"), param.getPath() + "%"));
            }
            if (!Strings.isNullOrEmpty(param.getName())) {
                list.add(cb.like(root.get("name"), "%" + param.getName() + "%"));
            }
            if (!Strings.isNullOrEmpty(param.getTitle())) {
                list.add(cb.like(root.get("title"), "%" + param.getTitle() + "%"));
            }
            if (!Strings.isNullOrEmpty(param.getDescription())) {
                list.add(cb.like(root.get("description"), "%" + param.getDescription() + "%"));
            }

            return query.where(list.toArray(new Predicate[0])).getRestriction();
        }, pageRequest);

        // 转换输出信息
        List<PermissionFindVo> data = page.getContent().stream()
                .map(permission -> {
                    PermissionFindVo vo = new PermissionFindVo();
                    BeanUtils.copyProperties(permission, vo);
                    return vo;
                })
                .collect(Collectors.toList());

        // 构建分页信息结果集
        PermissionFindPageVo vo = new PermissionFindPageVo();
        vo.setData(data);
        vo.setTotal(page.getTotalElements());
        return Result.ok(vo);
    }

    @Override
    public Result<Collection<BasicTreeVo>> findAllToTree() {
        List<Permission> permissions = permissionRepository.findAll();
        List<BasicTreeVo> trees = Permissions.toTrees(permissions);
        return Result.ok(trees);
    }

}
