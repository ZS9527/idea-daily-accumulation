package com.test.testidea.web.findproject;

import com.test.testidea.basic.Result;
import com.test.testidea.param.findproject.department.DepartmentProjectPageExcelParam;
import com.test.testidea.param.findproject.department.DepartmentProjectPageParam;
import com.test.testidea.param.findproject.department.DepartmentProjectParam;
import com.test.testidea.service.findproject.FindprojectService;
import com.test.testidea.vo.findproject.department.DepartmentProjectInfoVo;
import com.test.testidea.vo.findproject.department.DepartmentProjectPageVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 根据不同角色去操作项目
 *
 * @author zhangshuai
 * @date 2019/4/22 9:14
 */

@Api(tags = "FindProject")
@Validated
@RestController
@RequestMapping("/findProject/")
public class FindprojectController {

    @Autowired
    FindprojectService findprojectService;

    /**
     * 角色根据id查看项目详情
     * @param param {@link DepartmentProjectParam}
     * @return
     */
    @ApiOperation("角色根据id查看项目详情")
    @PostMapping("findProjectByTownship")
    public Result<DepartmentProjectInfoVo> findProjectByTownship(@Valid DepartmentProjectParam param){
        return findprojectService.findProjectByDepartment(param);
    }

    /**
     * 角色查看分页项目列表
     * @param param {@link DepartmentProjectPageParam}
     * @return
     */
    @ApiOperation("角色查看分页项目列表")
    @PostMapping("findProjectPageByTownship")
    public Result<DepartmentProjectPageVo> findProjectPageByTownship(@Valid DepartmentProjectPageParam param){
        return findprojectService.findProjectPageByDepartment(param);
    }

    /**
     * 角色分页查看项目列表导出Excel
     * @param param {@link DepartmentProjectPageExcelParam}
     * @return
     */
    @ApiOperation("角色分页查看项目导出Excel")
    @PostMapping("findProjectPageExcelFileByTownship")
    public void findProjectPageExcelFileByTownship(HttpServletResponse response,@Valid DepartmentProjectPageExcelParam param) throws Exception {
        findprojectService.findProjectPageExcelFileByDepartment(response, param);
    }

}
