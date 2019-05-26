package com.test.testidea.domain.weightsort;

import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 用户关联项目权重表
 *
 * @author zhangshuai
 * @date 2019/5/9 11:13
 */
public interface WeightSortRepository extends JpaRepository<WeightSort, Serializable>,
    JpaSpecificationExecutor<WeightSort> {

}
