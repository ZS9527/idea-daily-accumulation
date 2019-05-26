package com.test.testidea.service.dictionary.impl;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.test.testidea.basic.Result;
import com.test.testidea.constant.Static;
import com.test.testidea.domain.dictionary.DictionaryType;
import com.test.testidea.domain.dictionary.DictionaryTypeRepository;
import com.test.testidea.param.dictionarytype.DictionaryTypeDeleteParam;
import com.test.testidea.param.dictionarytype.DictionaryTypeParam;
import com.test.testidea.param.dictionarytype.DictionaryTypePidParam;
import com.test.testidea.param.dictionarytype.DictionaryTypeSaveParam;
import com.test.testidea.param.dictionarytype.DictionaryTypeUpdateParam;
import com.test.testidea.service.dictionary.DictionaryTypeService;
import com.test.testidea.util.Commons;
import com.test.testidea.vo.dictionarytype.DictionaryTypeOrganizationTreeVo;
import com.test.testidea.vo.dictionarytype.DictionaryTypeTreeVo;
import com.test.testidea.vo.dictionarytype.DictionaryTypeVo;
import com.test.testidea.vo.dictionarytype.TypeTree;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * 字典录入
 *
 * @author zhangshuai
 * @date 2019/4/17 10:11
 */
@Service
public class DictionaryTypeServiceImpl implements DictionaryTypeService {

    @Autowired
    DictionaryTypeRepository dictionaryTypeRepository;

    @Override
    public Result saveDictionaryType(DictionaryTypeSaveParam param) {
        DictionaryType pData = dictionaryTypeRepository.findById(param.getPid()).orElse(null);
        String treeNode = "";
        if (pData != null) {
            treeNode = pData.getTreeNode();
            if (!Strings.isNullOrEmpty(treeNode)) {
                treeNode = treeNode + ".";
            }
        } else {
            return Result.no("Pid错误");
        }
        pData.setIsLeaf(false);
        DictionaryType newData = DictionaryType.builder()
            .description(param.getDescription())
            .isLeaf(true)
            .name(param.getName())
            .pid(param.getPid())
            .build();
        DictionaryType saveData = dictionaryTypeRepository.save(newData);
        //当子节点确定后将父节点状态修改
        dictionaryTypeRepository.save(pData);
        //保存子节点树结构路径
        newData.setTreeNode(treeNode + saveData.getId());
        dictionaryTypeRepository.save(newData);
        return Result.ok();
    }

    @Override
    public Result updateDictionaryType(DictionaryTypeUpdateParam param) {
        if (param.getId() == 1) {
            return Result.no("不允许修改根节点");
        }
        DictionaryType dictionaryType = dictionaryTypeRepository.findById(param.getId()).orElse(null);
        if (dictionaryType == null) {
            return Result.no("输入id错误");
        }
        dictionaryType.setName(param.getName());
        dictionaryType.setDescription(param.getDescription());
        dictionaryTypeRepository.save(dictionaryType);
        return Result.ok();
    }

    @Override
    public Result<DictionaryTypeVo> findDictionaryTypeList(DictionaryTypeParam param) {
        Page<DictionaryType> dictionaryTypePage = dictionaryTypeRepository.findAll(Example.of(DictionaryType.builder().name(param.getName()).build()) ,PageRequest.of(param.getPage(), param.getSize()));

        DictionaryTypeVo dictionaryTypeVo = new DictionaryTypeVo();
        dictionaryTypeVo.setData(dictionaryTypePage.getContent());
        dictionaryTypeVo.setTotal(dictionaryTypePage.getTotalElements());

        return Result.ok(dictionaryTypeVo);
    }

    @Override
    public Result deleteDictionaryType(DictionaryTypeDeleteParam param) {
        Long id = param.getPid();
        if (Static.ZERO.equals(id)) {
            return Result.no("总结点不可删除");
        }
        DictionaryType dictionaryType = dictionaryTypeRepository.findById(id).orElse(null);
        if (dictionaryType == null) {
            return Result.no();
        }
        String treeNodeId = dictionaryType.getTreeNode();
        List<DictionaryType> dictionaryTypeList = dictionaryTypeRepository.findAllByPidTree(treeNodeId + ".%");
        dictionaryTypeList.add(dictionaryType);
        dictionaryTypeRepository.deleteInBatch(dictionaryTypeList);
        return Result.ok();
    }

    @Override
    public Result<DictionaryTypeTreeVo> findDictionaryTypeTree(DictionaryTypePidParam param) {
        Long pid = param.getPid();

        DictionaryType dictionaryType = dictionaryTypeRepository.findById(pid).orElse(null);
        if (Objects.isNull(dictionaryType)) {
            return Result.no("父id不存在");
        }

        String treeNodeId = dictionaryType.getTreeNode();
        List<DictionaryType> dictionaryTypeList = dictionaryTypeRepository.findAllByPidTree(treeNodeId + ".%");

        List<TypeTree> treeChildNode = arrayDirTree(pid, dictionaryTypeList);

        DictionaryTypeTreeVo typeTreeVo = new DictionaryTypeTreeVo();
        typeTreeVo.setData(treeChildNode);
        typeTreeVo.setTotal((long)treeChildNode.size());
        return Result.ok(typeTreeVo);
    }

    @Override
    public Result<DictionaryTypeTreeVo> findDictionaryTypeChildNodeByPid(DictionaryTypePidParam param) {
        List<TypeTree> treeVoList = dictionaryTypeRepository.findAllByPid(param.getPid())
            .stream().map(dictionaryType -> {
                TypeTree typeTree = new TypeTree();
                typeTree.setChildren(new ArrayList<>());
                typeTree.setIsLeaf(dictionaryType.getIsLeaf());
                typeTree.setKey(dictionaryType.getId());
                typeTree.setTitle(dictionaryType.getName());
                typeTree.setDescription(dictionaryType.getDescription());
                return typeTree;
            }).collect(Collectors.toList());
        DictionaryTypeTreeVo typeTreeVo = new DictionaryTypeTreeVo();
        typeTreeVo.setData(treeVoList);
        typeTreeVo.setTotal((long)treeVoList.size());
        return Result.ok(typeTreeVo);
    }

    @Override
    public Result<DictionaryTypeOrganizationTreeVo> findDictionaryTypeByOrganizationTree() {
        List<TypeTree> typeTreeList = dictionaryTypeRepository.findAllById(
            Lists.newArrayList(Static.BUREAU, Static.DEPARTMENT, Static.TOWNSHIP))
            .stream()
            .map(dictionaryType -> {
                List<TypeTree> children = dictionaryTypeRepository.findAllByPid(dictionaryType.getId()).stream()
                    .map(dt ->
                        TypeTree.builder()
                            .key(dt.getId())
                            .title(dt.getName())
                            .isLeaf(false)
                            .description(dt.getDescription())
                            .build()
                    )
                    .collect(Collectors.toList());

                return TypeTree.builder()
                    .key(dictionaryType.getId())
                    .title(dictionaryType.getName())
                    .isLeaf(true)
                    .description(dictionaryType.getDescription())
                    .children(children)
                    .build();
            })
            .collect(Collectors.toList());
        DictionaryTypeOrganizationTreeVo typeTreeVo = new DictionaryTypeOrganizationTreeVo();
        typeTreeVo.setData(typeTreeList);
        typeTreeVo.setTotal((long)typeTreeList.size());

        return Result.ok(typeTreeVo);
    }

    /**
     * 将数据库里的字典按照树状结构排序
     *
     * @param id 父级id
     * @param services 总数据集合
     * @return 树状结构排序
     */
    private List<TypeTree> arrayDirTree(Long id, List<DictionaryType> services) {
        List<TypeTree> children = new ArrayList<>();
        for (DictionaryType type : services) {
            if (id.equals(type.getPid())) {
                TypeTree treeVo = TypeTree.builder().key(type.getId()).title(type.getName()).description(type.getDescription()).build();
                List<TypeTree> treeChildNode = arrayDirTree(type.getId(), services);
                treeVo.setChildren(Commons.isNullOrEmpty(treeChildNode) ? null : treeChildNode);
                children.add(treeVo);
            }
        }
        return children;
    }

}
