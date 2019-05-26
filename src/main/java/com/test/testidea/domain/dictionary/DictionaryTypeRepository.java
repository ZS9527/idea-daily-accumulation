package com.test.testidea.domain.dictionary;

import java.io.Serializable;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * 字典实现jpa
 *
 * @author zhangshuai
 * @date 2019/4/17 10:25
 */
public interface DictionaryTypeRepository extends JpaRepository<DictionaryType, Serializable> {

    /**
     * 根据父节点查询
     * @param pid 父节点
     * @return
     */
    @Query("select a from DictionaryType a where a.treeNode like ?1")
    List<DictionaryType> findAllByPidTree(String pid);

    /**
     * 根据pid查询下一层叶子结点
     * @param pid
     * @return
     */
    List<DictionaryType> findAllByPid(Long pid);
}
