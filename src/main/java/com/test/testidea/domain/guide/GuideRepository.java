package com.test.testidea.domain.guide;

import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 指南JPA
 *
 * @author zhangshuai
 * @date 2019/4/18 10:21
 */
public interface GuideRepository extends JpaRepository<Guide, Serializable>,
    JpaSpecificationExecutor<Guide> {

}
