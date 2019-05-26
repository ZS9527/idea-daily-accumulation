package com.test.testidea.service.projectinfo.impl;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.test.testidea.basic.Result;
import com.test.testidea.domain.constructioncontent.ConstructionContent;
import com.test.testidea.domain.constructioncontent.ConstructionContentRepository;
import com.test.testidea.domain.dictionary.DictionaryType;
import com.test.testidea.domain.dictionary.DictionaryTypeRepository;
import com.test.testidea.domain.guide.Guide;
import com.test.testidea.domain.guide.GuideRepository;
import com.test.testidea.domain.projectinfo.ProjectInfo;
import com.test.testidea.domain.projectinfo.ProjectInfoRepository;
import com.test.testidea.domain.uploadfile.FileOperation;
import com.test.testidea.domain.uploadfile.FileOperationRepository;
import com.test.testidea.domain.user.UserRepository;
import com.test.testidea.param.BasicIDParam;
import com.test.testidea.param.projectinfo.SaveProjectInfoParam;
import com.test.testidea.param.projectinfo.delete.DeleteProjectInfoParam;
import com.test.testidea.service.projectinfo.ProjectInfoService;
import com.test.testidea.vo.projectinfo.ProjectInfoSingleVo;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 项目
 *
 * @author zhangshuai
 * @date 2019/4/18 14:17
 */

@Service
@Slf4j
public class ProjectInfoServiceImpl implements ProjectInfoService {

    @Autowired
    DictionaryTypeRepository dictionaryTypeRepository;

    @Autowired
    GuideRepository guideRepository;

    @Autowired
    ProjectInfoRepository projectInfoRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    FileOperationRepository uploadFileRepository;

    @Autowired
    ConstructionContentRepository constructionContentRepository;

    @Override
    public Result saveProjectInfo(SaveProjectInfoParam param) {
        Guide guide = guideRepository.findById(param.getGuideId()).orElse(null);
        DictionaryType department = dictionaryTypeRepository.findById(param.getDepartmentId()).orElse(null);
        if (guide == null || department == null) {
            return Result.no("资料不全");
        }

        String projectName = param.getProjectName();
        if (Strings.isNullOrEmpty(projectName)) {
            projectName = "项目名称";
        }

        if (param.getId() != null) {
            Long projectId = param.getId();
            ProjectInfo projectInfo = projectInfoRepository.findById(projectId).orElse(null);
            if (projectInfo == null) {
                return Result.no("修改项目id为空");
            }

            BeanUtils.copyProperties(param, projectInfo);
            Set<ConstructionContent> constructionContentSet = param.getProjectContent().stream()
                .map(insertRoleParam -> {
                    ConstructionContent content = new ConstructionContent();
                    content.setNum(insertRoleParam.getNum());
                    DictionaryType dictionaryType = dictionaryTypeRepository.findById(insertRoleParam.getDictionaryTypeId()).orElse(null);
                    content.setDictionaryType(dictionaryType);
                    return content;
                })
                .collect(Collectors.toSet());

            //修改项目
            projectInfo.setProjectName(projectName);
            projectInfo.setProjectContent(constructionContentSet);
            projectInfo.setGuide(guide);
            projectInfo.setDepartment(department);

            List<Long> filesIdsList = param.getUploadFilesIds();
            if (filesIdsList == null) {
                filesIdsList = new ArrayList<>();
            }
            List<FileOperation> files = uploadFileRepository.findAllById(Lists.newArrayList(filesIdsList));
            projectInfo.setUploadFiles(new HashSet<>(files));
            projectInfoRepository.save(projectInfo);
        }

        return Result.ok();
    }

    @Override
    public Result newProjectInfo(SaveProjectInfoParam param) {
        DictionaryType department = dictionaryTypeRepository.findById(param.getDepartmentId()).orElse(null);
        Guide guide = guideRepository.findById(param.getGuideId()).orElse(null);
        if (guide == null || department == null) {
            return Result.no("资料不全");
        }

        String projectName = param.getProjectName();
        if (Strings.isNullOrEmpty(projectName)) {
            projectName = "新建项目名称";
        }

        Set<ConstructionContent> constructionContentSet = param.getProjectContent().stream().map(insertRoleParam -> {
            ConstructionContent content = new ConstructionContent();
            DictionaryType dictionaryType = dictionaryTypeRepository.findById(insertRoleParam.getDictionaryTypeId()).orElse(null);
            content.setDictionaryType(dictionaryType);
            content.setNum(insertRoleParam.getNum());
            return content;
        }).collect(Collectors.toSet());
        //新建
        ProjectInfo projectInfo = ProjectInfo.builder()
            .projectContent(constructionContentSet)
            .projectName(projectName)
            .department(department)
            .guide(guide)
            .year(param.getYear())
            .remarks(param.getRemarks())
            .createTime(LocalDateTime.now())
            .build();
        ProjectInfo saveProjectInfo = projectInfoRepository.save(projectInfo);

        List<Long> filesIdsList = param.getUploadFilesIds();
        if (filesIdsList == null) {
            filesIdsList = new ArrayList<>();
        }
        List<FileOperation> files = uploadFileRepository.findAllById(Lists.newArrayList(filesIdsList));

        saveProjectInfo.setUploadFiles(new HashSet<>(files));
        projectInfoRepository.save(saveProjectInfo);

        return null;
    }

    @Override
    public Result deleteProjectInfo(DeleteProjectInfoParam param) {
        List<ProjectInfo> projectInfoList = projectInfoRepository.findAllById(Lists.newArrayList(param.getProjectInfoId()));
        projectInfoList = projectInfoList.stream().collect(Collectors.toList());
        projectInfoRepository.deleteAll(projectInfoList);
        return Result.ok();
    }

    @Override
    public Result<ProjectInfoSingleVo> findProjectInfoById(BasicIDParam param) {
        ProjectInfo projectInfo = projectInfoRepository.findById(param.getId()).orElse(null);
        ProjectInfoSingleVo projectInfoSingleVo = new ProjectInfoSingleVo();
        projectInfoSingleVo.setData(projectInfo);
        return Result.ok(projectInfoSingleVo);
    }

}
