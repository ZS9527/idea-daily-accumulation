package com.test.testidea.service.guide.impl;

import com.google.common.collect.Lists;
import com.google.common.io.Files;
import com.test.testidea.basic.Result;
import com.test.testidea.constant.Static;
import com.test.testidea.domain.dictionary.DictionaryType;
import com.test.testidea.domain.dictionary.DictionaryTypeRepository;
import com.test.testidea.domain.guide.Guide;
import com.test.testidea.domain.guide.GuideRepository;
import com.test.testidea.domain.projectinfo.ProjectInfo;
import com.test.testidea.domain.projectinfo.ProjectInfoRepository;
import com.test.testidea.domain.uploadfile.FileOperation;
import com.test.testidea.domain.uploadfile.FileOperationRepository;
import com.test.testidea.param.BasicIDParam;
import com.test.testidea.param.guide.GuideFindExcelParam;
import com.test.testidea.param.guide.GuideFindParam;
import com.test.testidea.param.guide.GuideSaveParam;
import com.test.testidea.service.guide.GuideService;
import com.test.testidea.util.Commons;
import com.test.testidea.util.Excel;
//import com.test.testidea.util.ExcelUtil;
import com.test.testidea.util.ExcelUtil;
import com.test.testidea.util.FileUtil;
import com.test.testidea.vo.guide.FileInfo;
import com.test.testidea.vo.guide.GuideInfoExcelVo;
import com.test.testidea.vo.guide.GuideInfoVo;
import com.test.testidea.vo.guide.GuidePage;
import com.test.testidea.vo.guide.GuidePageVo;
import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.persistence.criteria.Predicate;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.convert.QueryByExamplePredicateBuilder;
import org.springframework.stereotype.Service;

/**
 * 指南
 *
 * @author zhangshuai
 * @date 2019/4/18 10:27
 */
@Slf4j
@Service
public class GuideServiceImpl implements GuideService {

    @Autowired
    DictionaryTypeRepository dictionaryTypeRepository;

    @Autowired
    FileOperationRepository uploadFileRepository;

    @Autowired
    GuideRepository guideRepository;

    @Autowired
    ProjectInfoRepository projectInfoRepository;

    @Override
    public Result saveGuide(GuideSaveParam param) {
        DictionaryType specialMoney = dictionaryTypeRepository.findById(param.getSpecialMoneyId()).orElse(null);
        if (specialMoney == null) {
            return Result.no("不存在的类型");
        }
        List<Long> filesIdsList = param.getUploadFilesIds();
        if (filesIdsList == null) {
            filesIdsList = new ArrayList<>();
        }
        List<FileOperation> files = uploadFileRepository.findAllById(Lists.newArrayList(filesIdsList));
        Guide guide = Guide.builder()
            .guideName(param.getGuideName())
            .createTime(LocalDateTime.now())
            .demand(param.getDemand())
            .specialMoney(specialMoney)
            .uploadText(param.getUploadText())
            .review(Static.WAIT)
            .build();
        if (param.getId() != null) {
            List<ProjectInfo> projectInfoList = projectInfoRepository.findAll(Example.of(ProjectInfo.builder().guide(guideRepository.findById(param.getId()).orElse(null)).build()));
            if (!Commons.isNullOrEmpty(projectInfoList)) {
                return Result.no("本指南绑定了项目，禁止修改");
            }
            Guide guideOld = guideRepository.findById(param.getId()).orElse(null);
            guide.setId(param.getId());
            guide.setReview(guideOld.getReview());
            guide.setUpdateTime(LocalDateTime.now());
        }
        Guide guideSave = guideRepository.save(guide);
        guideSave.setUploadFiles(new HashSet<>(files));
        guideRepository.save(guideSave);
        return Result.ok();
    }

    @Override
    public Result<GuidePageVo> findGuidePage(GuideFindParam param) {
        DictionaryType specialMoneyDictionary = null;
        if (param.getSpecialMoneyId() != null) {
            specialMoneyDictionary = dictionaryTypeRepository.findById(param.getSpecialMoneyId()).orElse(null);
        }

        Guide guideFind = new Guide();
        boolean flagPro = false;

        if (Objects.nonNull(param.getReview())) {
            guideFind.setReview(param.getReview());
            flagPro = true;
        }
        if (specialMoneyDictionary != null) {
            guideFind.setSpecialMoney(specialMoneyDictionary);
            flagPro = true;
        }
        if (Objects.nonNull(param.getKeyWord())) {
            flagPro = true;
        }

        Page<Guide> guidePage;
        Example example = Example.of(guideFind);
        Pageable pageable = PageRequest.of(param.getPage(), param.getSize());
        if (flagPro) {
            guidePage = guideRepository.findAll((root, query, cb) -> {
                List<Predicate> list = new ArrayList<>();
                list.add(QueryByExamplePredicateBuilder.getPredicate(root, cb, example));
                if (Objects.nonNull(param.getKeyWord())) {
                    Predicate guideName = cb.like(root.get("guideName"), "%" + param.getKeyWord() + "%");
//                    Predicate demand = cb.like(root.get("demand"), "%" + param.getKeyWord() + "%");
//                    list.add(cb.or(demand, guideName));
                    list.add(guideName);
                }
                return query.where(list.toArray(new Predicate[0])).getRestriction();
            }, pageable);
        } else {
            guidePage = guideRepository.findAll(PageRequest.of(param.getPage(), param.getSize()));
        }
        List<GuidePage> data = guidePage.get().map(guide -> {
            GuidePage page = new GuidePage();
            page.setId(guide.getId());
            page.setCreateTime(guide.getCreateTime());
            page.setGuideName(guide.getGuideName());
            page.setSpecialMoney(guide.getSpecialMoney().getName());
            page.setReview(guide.getReview());
            return page;
        }).collect(Collectors.toList());
        GuidePageVo guidePageVo = new GuidePageVo();
        guidePageVo.setData(data);
        guidePageVo.setTotal(guidePage.getTotalElements());

        return Result.ok(guidePageVo);
    }

    @Override
    public void findGuideExcelFile(HttpServletResponse response,
        GuideFindExcelParam param) throws Exception {

        List<Guide> guidePage;
        if (Commons.isNullOrEmpty(param.getIds())) {
            DictionaryType specialMoneyDictionary = null;
            if (param.getSpecialMoneyId() != null) {
                specialMoneyDictionary = dictionaryTypeRepository.findById(param.getSpecialMoneyId()).orElse(null);
            }

            Guide guideFind = new Guide();
            boolean flagPro = false;
            if (specialMoneyDictionary != null) {
                guideFind.setSpecialMoney(specialMoneyDictionary);
                flagPro = true;
            }
            if (Objects.nonNull(param.getKeyWord())) {
                flagPro = true;
            }

            if (flagPro) {
                guidePage = guideRepository.findAll((root, query, cb) -> {
                    List<Predicate> list = new ArrayList<>();
                    list.add(QueryByExamplePredicateBuilder.getPredicate(root, cb, Example.of(guideFind)));
                    if (Objects.nonNull(param.getKeyWord())) {
                        Predicate guideName = cb.like(root.get("guideName"), "%" + param.getKeyWord() + "%");
                        list.add(guideName);
                    }
                    return query.where(list.toArray(new Predicate[0])).getRestriction();
                });
            } else {
                guidePage = guideRepository.findAll();
            }

        } else {
            guidePage = guideRepository.findAllById(Lists.newArrayList(param.getIds()));
        }
        List<GuideInfoExcelVo> data = guidePage.stream().map(guide -> {
            GuideInfoExcelVo page = GuideInfoExcelVo.builder()
                .id(guide.getId())
                .guideName(guide.getGuideName())
                .specialMoney(guide.getSpecialMoney().getName())
                .createTime(guide.getCreateTime())
                .demand(guide.getDemand())
                .build();

            return page;
        }).collect(Collectors.toList());

//        ExcelUtil excelUtil = new ExcelUtil();
//        File excelFile = excelUtil.export(data, Excel.XLS);
//        Files.copy(excelFile, new File("D://finance/" + "myFile" + Excel.XLS));
//        FileUtil.out(response, excelFile, "指南信息列表" + Excel.XLS);
    }

    @Override
    public Result deleteGuideById(BasicIDParam param) {
        try {
            guideRepository.deleteById(param.getId());
        } catch (Exception e) {
            e.printStackTrace();
            return Result.no("关联项目未删除");
        }
        return Result.ok();
    }

    @Override
    public Result<GuideInfoVo> findGuideById(BasicIDParam param) {
        Guide guide = guideRepository.findById(param.getId()).orElse(null);
        if (guide == null) {
            return Result.no("id错误");
        }
        List<FileInfo> fileInfoList = guide.getUploadFiles().stream().map(file -> {
            FileInfo fileInfo = new FileInfo();
            return fileInfo;
        }).collect(Collectors.toList());
        GuideInfoVo guideInfoVo = GuideInfoVo.builder()
            .guideName(guide.getGuideName())
            .demand(guide.getDemand())
            .specialMoney(guide.getSpecialMoney())
            .uploadText(guide.getUploadText())
            .fileInfos(fileInfoList)
            .build();

        return Result.ok(guideInfoVo);
    }

    @Override
    public Result releaseGuide(BasicIDParam param) {
        Guide guide = guideRepository.findById(param.getId()).orElse(null);
        guide.setReview(Static.AGREE);
        guideRepository.save(guide);
        return Result.ok();
    }
}
