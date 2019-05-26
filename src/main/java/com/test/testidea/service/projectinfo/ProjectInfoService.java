package com.test.testidea.service.projectinfo;


import com.test.testidea.basic.Result;
import com.test.testidea.param.BasicIDParam;
import com.test.testidea.param.projectinfo.SaveProjectInfoParam;
import com.test.testidea.param.projectinfo.delete.DeleteProjectInfoParam;
import com.test.testidea.vo.projectinfo.ProjectInfoSingleVo;

/**
 * 项目
 *
 * @author zhangshuai
 * @date 2019/4/18 14:17
 */
public interface ProjectInfoService {

    /**
     * 保存项目
     * @param param
     * @return
     */
    Result saveProjectInfo(SaveProjectInfoParam param);

    /**
     * 新建项目
     * @param param
     * @return
     */
    Result newProjectInfo(SaveProjectInfoParam param);

    /**
     * 删除项目
     * @param param
     * @return
     */
    Result deleteProjectInfo(DeleteProjectInfoParam param);

    /**
     * 根据id查询项目详情
     * @param param
     * @return
     */
    Result<ProjectInfoSingleVo> findProjectInfoById(BasicIDParam param);
}


