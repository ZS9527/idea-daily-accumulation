package com.test.testidea.service.dictionary;

import com.test.testidea.basic.Result;
import com.test.testidea.param.dictionarytype.DictionaryTypeDeleteParam;
import com.test.testidea.param.dictionarytype.DictionaryTypeParam;
import com.test.testidea.param.dictionarytype.DictionaryTypePidParam;
import com.test.testidea.param.dictionarytype.DictionaryTypeSaveParam;
import com.test.testidea.param.dictionarytype.DictionaryTypeUpdateParam;
import com.test.testidea.vo.dictionarytype.DictionaryTypeOrganizationTreeVo;
import com.test.testidea.vo.dictionarytype.DictionaryTypeTreeVo;
import com.test.testidea.vo.dictionarytype.DictionaryTypeVo;

/**
 * 字典表
 *
 * @author zhangshuai
 * @date 2019/4/17 10:10
 */
public interface DictionaryTypeService {

    /**
     * 新建字典
     * @param dictionary 字典类
     * @return
     */
    Result saveDictionaryType(DictionaryTypeSaveParam dictionary);

    /**
     * 修改字典
     * @param dictionary 字典类
     * @return
     */
    Result updateDictionaryType(DictionaryTypeUpdateParam dictionary);

    /**
     * 删除字典
     * @param param key
     * @return
     */
    Result deleteDictionaryType(DictionaryTypeDeleteParam param);

    /**
     * 分页查询数据字典
     * @param param 分页条件参数
     * @return
     */
    Result<DictionaryTypeVo> findDictionaryTypeList(DictionaryTypeParam param);


    /**
     * 根据父节点查询树状结构
     * @param param 父级节点
     * @return
     */
    Result<DictionaryTypeTreeVo> findDictionaryTypeTree(DictionaryTypePidParam param);

    /**
     * 根据父节点查询下级子节点
     * @param param
     * @return
     */
    Result<DictionaryTypeTreeVo> findDictionaryTypeChildNodeByPid(DictionaryTypePidParam param);

    /**
     * 返回三个组织的所有信息树状结构
     * @return
     */
    Result<DictionaryTypeOrganizationTreeVo> findDictionaryTypeByOrganizationTree();
}
