package com.test.testidea.service.guide;

import com.test.testidea.basic.Result;
import com.test.testidea.param.BasicIDParam;
import com.test.testidea.param.guide.GuideFindExcelParam;
import com.test.testidea.param.guide.GuideFindParam;
import com.test.testidea.param.guide.GuideSaveParam;
import com.test.testidea.vo.guide.GuideInfoVo;
import com.test.testidea.vo.guide.GuidePageVo;
import javax.servlet.http.HttpServletResponse;

/**
 * 指南
 *
 * @author zhangshuai
 * @date 2019/4/18 10:26
 */
public interface GuideService {

    /**
     * 保存指南
     * @param param
     * @return
     */
    Result saveGuide(GuideSaveParam param);

    /**
     * 分页查询指南
     * @param param
     * @return
     */
    Result<GuidePageVo> findGuidePage(GuideFindParam param);

    /**
     * 分页查看Excel指南
     * @param response
     * @param param
     * @return
     */
    void findGuideExcelFile(HttpServletResponse response, GuideFindExcelParam param) throws Exception;

    /**
     * 根据id删除指南
     * @param param
     * @return
     */
    Result deleteGuideById(BasicIDParam param);

    /**
     * 根据id查询指南
     * @param param
     * @return
     */
    Result<GuideInfoVo> findGuideById(BasicIDParam param);

    /**
     * 发布指南
     * @param param
     * @return
     */
    Result releaseGuide(BasicIDParam param);
}
