package com.test.testidea.web.dictionary;

import com.test.testidea.basic.Result;
import com.test.testidea.param.dictionarytype.DictionaryTypeDeleteParam;
import com.test.testidea.param.dictionarytype.DictionaryTypeParam;
import com.test.testidea.param.dictionarytype.DictionaryTypePidParam;
import com.test.testidea.param.dictionarytype.DictionaryTypeSaveParam;
import com.test.testidea.param.dictionarytype.DictionaryTypeUpdateParam;
import com.test.testidea.service.dictionary.DictionaryTypeService;
import com.test.testidea.vo.dictionarytype.DictionaryTypeOrganizationTreeVo;
import com.test.testidea.vo.dictionarytype.DictionaryTypeTreeVo;
import com.test.testidea.vo.dictionarytype.DictionaryTypeVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 字典表操作
 *
 * @author zhangshuai
 * @date 2019/4/17 10:09
 */
@Api(tags = "DictionaryType")
@Validated
@RestController
@RequestMapping("/dictionaryType/")
public class DictionaryTypeController {

    @Autowired
    DictionaryTypeService dictionaryService;

    /**
     * 新建字典
     * @param param {@link DictionaryTypeSaveParam}
     * @return
     */
    @ApiOperation("新建字典")
    @PostMapping("saveDictionary")
    public Result saveDictionary(@Valid DictionaryTypeSaveParam param){
        return dictionaryService.saveDictionaryType(param);
    }

    /**
     * 修改字典
     * @param param {@link DictionaryTypeUpdateParam}
     * @return
     */
    @ApiOperation("修改字典")
    @PostMapping("updateDictionaryType")
    public Result updateDictionaryType(@Valid DictionaryTypeUpdateParam param){
        return dictionaryService.updateDictionaryType(param);
    }

    /**
     * 删除字典表
     * @param param {@link DictionaryTypeDeleteParam}
     * @return
     */
    @ApiOperation("删除字典表")
    @PostMapping("deleteDictionaryType")
    public Result deleteDictionaryType(@Valid DictionaryTypeDeleteParam param){
        return dictionaryService.deleteDictionaryType(param);
    }


    /**
     * 查看字典表
     * @param param {@link DictionaryTypeParam}
     * @return
     */
    @ApiOperation("查看字典表")
    @GetMapping("findDictionaryTypeList")
    public Result<DictionaryTypeVo> findDictionaryTypeList(@Valid DictionaryTypeParam param){
        return dictionaryService.findDictionaryTypeList(param);
    }

    /**
     * 根据根节点查看字典表所有子节点树状图
     * @param param {@link DictionaryTypePidParam}
     * @return
     */
    @ApiOperation("查看字典表树状图")
    @GetMapping("findDictionaryTypeTree")
    public Result<DictionaryTypeTreeVo> findDictionaryTypeTree(@Valid DictionaryTypePidParam param){
        return dictionaryService.findDictionaryTypeTree(param);
    }

    /**
     * 根据根节点查看字典表下级子节点
     * @param param {@link DictionaryTypePidParam}
     * @return
     */
    @ApiOperation("根据根节点查看字典表下级子节点")
    @GetMapping("findDictionaryTypeChildNodeByPid")
    public Result<DictionaryTypeTreeVo> findDictionaryTypeChildNodeByPid(@Valid DictionaryTypePidParam param){
        return dictionaryService.findDictionaryTypeChildNodeByPid(param);
    }

    /**
     * 返回三个组织的所有信息树状结构
     * @return
     */
    @ApiOperation("返回三个组织的所有信息树状结构")
    @GetMapping("findDictionaryTypeByOrganizationTree")
    public Result<DictionaryTypeOrganizationTreeVo> findDictionaryTypeByOrganizationTree(){
        return dictionaryService.findDictionaryTypeByOrganizationTree();
    }

}
