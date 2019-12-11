package com.test.testidea.service.findproject.impl;

import com.google.common.collect.Lists;
import com.test.testidea.basic.Result;
import com.test.testidea.domain.dictionary.DictionaryTypeRepository;
import com.test.testidea.domain.projectinfo.ProjectInfo;
import com.test.testidea.domain.projectinfo.ProjectInfoRepository;
import com.test.testidea.domain.user.UserRepository;
import com.test.testidea.param.findproject.FindProjectParam;
import com.test.testidea.param.findproject.department.DepartmentProjectPageExcelParam;
import com.test.testidea.param.findproject.department.DepartmentProjectPageParam;
import com.test.testidea.param.findproject.department.DepartmentProjectParam;
import com.test.testidea.service.findproject.FindprojectService;
import com.test.testidea.util.Commons;
import com.test.testidea.vo.findproject.department.DepartmentProjectExcelInfoVo;
import com.test.testidea.vo.findproject.department.DepartmentProjectInfoVo;
import com.test.testidea.vo.findproject.department.DepartmentProjectPage;
import com.test.testidea.vo.findproject.department.DepartmentProjectPageVo;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 根据不同角色去操作项目
 *
 * @author zhangshuai
 * @date 2019/4/22 9:16
 */
@Service
@Slf4j
public class FindprojectServiceImpl implements FindprojectService {

    @Autowired
    ProjectInfoRepository projectInfoRepository;

    @Autowired
    DictionaryTypeRepository dictionaryTypeRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public Result<DepartmentProjectInfoVo> findProjectByDepartment(DepartmentProjectParam param) {
        ProjectInfo projectInfo = projectInfoRepository.findById(param.getId()).orElse(null);
        if (projectInfo == null) {
            return Result.no("id编号不对");
        }
        DepartmentProjectInfoVo projectInfoVo = DepartmentProjectInfoVo.builder()
            .guideName(projectInfo.getGuide().getGuideName())
            .projectName(projectInfo.getProjectName())
            .year(projectInfo.getYear())
            .projectContent(new ArrayList<>(projectInfo.getProjectContent()))
            .remarks(projectInfo.getRemarks())
            .uploadFiles(new ArrayList<>(projectInfo.getUploadFiles()))
            .build();
        return Result.ok(projectInfoVo);
    }

    @Override
    public Result<DepartmentProjectPageVo> findProjectPageByDepartment(
        DepartmentProjectPageParam param) {

        Integer page = param.getPage();
        Integer size = param.getSize();

        FindProjectParam projectParam = new FindProjectParam();

        if (Objects.nonNull(page) && Objects.nonNull(size)) {
            projectParam.setPageStart(page * size);
            projectParam.setSizeStart(size);
        }
        BeanUtils.copyProperties(param, projectParam);
        Long userId = param.getSortUserId();
        projectParam.setUserId(userId);
        if (Objects.nonNull(param.getKeyWord())) {
            projectParam.setProjectName("%" + param.getKeyWord() + "%");
        }

        List<ProjectInfo> projectInfoPage = projectInfoRepository.findAllPageProjectByParam(projectParam);
        Long count = projectInfoRepository.findAllCount(projectParam);

        List<DepartmentProjectPage> departmentProjectPages = projectInfoPage.stream().map(projectInfo -> {
            DepartmentProjectPage projectPage = DepartmentProjectPage.builder()
                .createTime(projectInfo.getCreateTime())
                .id(projectInfo.getId())
                .projectName(projectInfo.getProjectName())
                .build();
            return projectPage;
        }).collect(Collectors.toList());
        DepartmentProjectPageVo departmentProjectPageVo = new DepartmentProjectPageVo();
        departmentProjectPageVo.setData(departmentProjectPages);
        departmentProjectPageVo.setTotal(count);

        return Result.ok(departmentProjectPageVo);
    }

    @Override
    public void findProjectPageExcelFileByDepartment(HttpServletResponse response,
        DepartmentProjectPageExcelParam param) throws Exception {

        List<ProjectInfo> projectInfoList;
        if (Commons.isNullOrEmpty(param.getIds())) {
            FindProjectParam projectParam = new FindProjectParam();
            BeanUtils.copyProperties(param, projectParam);
            Long userId = param.getSortUserId();
            projectParam.setUserId(userId);
            if (Objects.nonNull(param.getKeyWord())) {
                projectParam.setProjectName("%" + param.getKeyWord() + "%");
            }
            projectInfoList = projectInfoRepository.findAllByParam(projectParam);

        } else {
            projectInfoList = projectInfoRepository.findAllById(Lists.newArrayList(param.getIds()));
        }
        List<DepartmentProjectExcelInfoVo> departmentProjectExcelInfoVos = projectInfoList.stream().map(projectInfo -> {
            String projectContent = projectInfo.getProjectContent().stream()
                .map(cc ->
                    String.format("内容:%s,数量:%f%s", cc.getDictionaryType().getName(), cc.getNum(), cc.getDictionaryType().getDescription())).collect(
                Collectors.joining(";"));
            DepartmentProjectExcelInfoVo projectPage = DepartmentProjectExcelInfoVo.builder()
                .id(projectInfo.getId())
                .createTime(projectInfo.getCreateTime())
                .projectName(projectInfo.getProjectName())
                .guideName(projectInfo.getGuide().getGuideName())
                .year(projectInfo.getYear())
                .projectContent(projectContent)
                .remarks(projectInfo.getRemarks())
                .build();
            return projectPage;
        }).collect(Collectors.toList());
//        ExcelUtil excelUtil = new ExcelUtil();
//        File excelFile = excelUtil.export(departmentProjectExcelInfoVos, Excel.XLS);
//        FileUtil.out(response, excelFile, "部门上报项目列表" + Excel.XLS);
    }

}
