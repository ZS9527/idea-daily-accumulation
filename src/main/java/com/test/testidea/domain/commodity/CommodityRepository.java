package com.test.testidea.domain.commodity;

import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 商品实体类JPA
 *
 * @author zhangshuai
 * @date 2019/4/28 21:26
 */
public interface CommodityRepository extends JpaRepository<Commodity, Serializable> {

}
