package com.test.testidea.web.projectinfo;

import com.test.testidea.basic.Result;
import com.test.testidea.param.BasicIDParam;
import com.test.testidea.param.projectinfo.SaveProjectInfoParam;
import com.test.testidea.param.projectinfo.delete.DeleteProjectInfoParam;
import com.test.testidea.service.projectinfo.ProjectInfoService;
import com.test.testidea.vo.projectinfo.ProjectInfoSingleVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 项目
 *
 * @author zhangshuai
 * @date 2019/4/18 14:08
 */
@Api(tags = "ProjectInfo")
@Validated
@RestController
@RequestMapping("/projectInfo/")
public class ProjectInfoController {

    @Autowired
    ProjectInfoService projectInfoService;

    /**
     * 修改项目
     * @param param {@link SaveProjectInfoParam}
     * @return
     */
    @ApiOperation("修改项目")
    @PostMapping("saveProjectInfo")
    public Result saveProjectInfo(@Valid SaveProjectInfoParam param){
        return projectInfoService.saveProjectInfo(param);
    }

    /**
     * 保存项目
     * @param param {@link SaveProjectInfoParam}
     * @return
     */
    @ApiOperation("保存项目")
    @PostMapping("newProjectInfo")
    public Result newProjectInfo(@Valid SaveProjectInfoParam param){
        return projectInfoService.newProjectInfo(param);
    }

    /**
     * 删除项目
     * @param param {@link BasicIDParam}
     * @return
     */
    @ApiOperation("删除项目")
    @PostMapping("deleteProjectInfo")
    public Result deleteProjectInfo(@Valid DeleteProjectInfoParam param){
        return projectInfoService.deleteProjectInfo(param);
    }

    /**
     * 根据id查询项目详情
     * @param param {@link BasicIDParam}
     * @return
     */
    @ApiOperation("根据id查询项目详情")
    @PostMapping("findProjectInfoById")
    public Result<ProjectInfoSingleVo> findProjectInfoById(@Valid BasicIDParam param){
        return projectInfoService.findProjectInfoById(param);
    }

}
