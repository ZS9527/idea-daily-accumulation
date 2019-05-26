package com.test.testidea.service.findproject;

import com.test.testidea.basic.Result;
import com.test.testidea.param.findproject.department.DepartmentProjectPageExcelParam;
import com.test.testidea.param.findproject.department.DepartmentProjectPageParam;
import com.test.testidea.param.findproject.department.DepartmentProjectParam;
import com.test.testidea.vo.findproject.department.DepartmentProjectInfoVo;
import com.test.testidea.vo.findproject.department.DepartmentProjectPageVo;
import javax.servlet.http.HttpServletResponse;

/**
 * 根据不同角色去操作项目
 *
 * @author zhangshuai
 * @date 2019/4/22 9:16
 */
public interface FindprojectService {

    /**
     * 角色通过id查看项目详情
     * @param param
     * @return
     */
    Result<DepartmentProjectInfoVo> findProjectByDepartment(DepartmentProjectParam param);

    /**
     * 角色分页查看项目列表
     * @param param
     * @return
     */
    Result<DepartmentProjectPageVo> findProjectPageByDepartment(DepartmentProjectPageParam param);

    /**
     * 角色分页查看Excel项目
     * @param response
     * @param param
     * @return
     */
    void findProjectPageExcelFileByDepartment(HttpServletResponse response,
        DepartmentProjectPageExcelParam param) throws Exception;

}
