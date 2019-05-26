package com.test.testidea.domain.projectinfo;

import com.test.testidea.param.findproject.FindProjectParam;
import java.io.Serializable;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * 项目
 *
 * @author zhangshuai
 * @date 2019/4/18 15:01
 */
public interface ProjectInfoRepository extends JpaRepository<ProjectInfo, Serializable> ,
    JpaSpecificationExecutor<ProjectInfo> {

    @Query(value = "SELECT "
        + "p.* "
        + " FROM "
        + " bs_project_info p "
        + " LEFT JOIN ( bs_project_info_weight_sorts pw JOIN bs_weight_sort w ON w.id = pw.weight_sorts_id AND w.user_id = :#{#param.userId} ) ON pw.project_info_id = p.id "
        + "where  "
        + " IF( :#{#param.townshipId} IS NOT null, p.township_id = :#{#param.townshipId}, 1=1) and "
        + " IF( :#{#param.year} IS NOT null, p.year = :#{#param.year},  1=1) and "
        + " IF( :#{#param.projectName} IS NOT null, p.project_name like :#{#param.projectName},  1=1)"
        + "ORDER BY "
        + "w.weight desc, p.create_time desc limit :#{#param.pageStart}, :#{#param.sizeStart}", nativeQuery=true)
    List<ProjectInfo> findAllPageProjectByParam(@Param("param") FindProjectParam param);

    @Query(value = "SELECT "
        + "p.* "
        + " FROM "
        + " bs_project_info p "
        + " LEFT JOIN ( bs_project_info_weight_sorts pw JOIN bs_weight_sort w ON w.id = pw.weight_sorts_id AND w.user_id = :#{#param.userId} ) ON pw.project_info_id = p.id "
        + "where  "
        + " IF( :#{#param.townshipId} IS NOT null, p.township_id = :#{#param.townshipId}, 1=1) and "
        + " IF( :#{#param.year} IS NOT null, p.year = :#{#param.year},  1=1) and "
        + " IF( :#{#param.projectName} IS NOT null, p.project_name like :#{#param.projectName},  1=1)"
        + "ORDER BY "
        + "w.weight desc, p.create_time desc ", nativeQuery=true)
    List<ProjectInfo> findAllByParam(@Param("param") FindProjectParam param);

    @Query(value = "SELECT "
        + "count(p.id) "
        + " FROM "
        + " bs_project_info p "
        + " LEFT JOIN ( bs_project_info_weight_sorts pw JOIN bs_weight_sort w ON w.id = pw.weight_sorts_id AND w.user_id = :#{#param.userId} ) ON pw.project_info_id = p.id "
        + "where  "
        + " IF( :#{#param.townshipId} IS NOT null, p.township_id = :#{#param.townshipId}, 1=1) and "
        + " IF( :#{#param.year} IS NOT null, p.year = :#{#param.year},  1=1) and "
        + " IF( :#{#param.projectName} IS NOT null, p.project_name like :#{#param.projectName},  1=1)"
        + "ORDER BY "
        + "w.weight desc, p.create_time desc", nativeQuery=true)
    Long findAllCount(@Param("param") FindProjectParam param);
}
