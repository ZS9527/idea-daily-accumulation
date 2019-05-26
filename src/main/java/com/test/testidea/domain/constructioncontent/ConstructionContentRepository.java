package com.test.testidea.domain.constructioncontent;

import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 项目内容jpa
 *
 * @author zhangshuai
 * @date 2019/5/6 9:25
 */
public interface ConstructionContentRepository extends JpaRepository<ConstructionContent, Serializable> {
}
