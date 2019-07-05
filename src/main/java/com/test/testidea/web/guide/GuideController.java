package com.test.testidea.web.guide;

import com.test.testidea.basic.Result;
import com.test.testidea.param.BasicIDParam;
import com.test.testidea.param.guide.GuideFindExcelParam;
import com.test.testidea.param.guide.GuideFindParam;
import com.test.testidea.param.guide.GuideSaveParam;
import com.test.testidea.service.guide.GuideService;
import com.test.testidea.vo.guide.GuideInfoVo;
import com.test.testidea.vo.guide.GuidePageVo;
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
 * 指南
 *
 * @author zhangshuai
 * @date 2019/4/18 10:25
 */

@Api(tags = "Guide")
@Validated
@RestController
@RequestMapping("/guide/")
public class GuideController {

    @Autowired
    GuideService guideService;

    /**
     * 保存指南
     * @param param {@link GuideSaveParam}
     * @return
     */
    @ApiOperation("保存和修改指南")
    @PostMapping("saveGuide")
    public Result saveGuide(@Valid GuideSaveParam param){
        return guideService.saveGuide(param);
    }

    /**
     * 分页查询指南
     * @param param {@link GuideFindParam}
     * @return
     */
    @ApiOperation("分页查询指南")
    @PostMapping("findGuidePage")
    public Result<GuidePageVo> findGuidePage(@Valid GuideFindParam param){
        return guideService.findGuidePage(param);
    }

    /**
     * 分页查看Excel指南
     * @param param {@link GuideFindExcelParam}
     * @return
     */
    @ApiOperation("分页查看Excel指南")
    @PostMapping("findGuideExcelFile")
    public void findGuideExcelFile(HttpServletResponse response, @Valid GuideFindExcelParam param)
        throws Exception {
        guideService.findGuideExcelFile(response, param);
    }

    /**
     * 根据id删除指南
     * @param param {@link BasicIDParam}
     * @return
     */
    @ApiOperation("根据id删除指南")
    @PostMapping("deleteGuideById")
    public Result deleteGuideById(@Valid BasicIDParam param){
        return guideService.deleteGuideById(param);
    }

    /**
     * 根据id查询指南
     * @param param {@link BasicIDParam}
     * @return
     */
    @ApiOperation("根据id查询指南")
    @PostMapping("findGuideById")
    public Result<GuideInfoVo> findGuideById(@Valid BasicIDParam param){
        return guideService.findGuideById(param);
    }

    /**
     * 发布指南
     * @param param {@link BasicIDParam}
     * @return
     */
    @ApiOperation("发布指南")
    @PostMapping("releaseGuide")
    public Result releaseGuide(@Valid BasicIDParam param){
        return guideService.releaseGuide(param);
    }

}
