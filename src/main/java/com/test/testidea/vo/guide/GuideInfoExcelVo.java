package com.test.testidea.vo.guide;

import com.test.testidea.util.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * 返回指南详情
 *
 * @author zhangshuai
 * @date 2019/4/23 20:18
 */

@ApiModel
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GuideInfoExcelVo implements Serializable {

    private static final long serialVersionUID = 109721821931387058L;

    @Excel(title = "序号", width = 25)
    @ApiModelProperty(value ="主键", example = "1")
    Long id;

    @Excel(title = "指南名称", width = 25)
    @ApiModelProperty(value ="指南名称", example = "科技局的指南")
    String guideName;

    @Excel(title = "资金类型", width = 25)
    @ApiModelProperty(value ="专项资金类型", example = "基础发展类")
    String specialMoney;

    @Excel(title = "发布时间", width = 25)
    @ApiModelProperty(value ="发布时间", example = "2019-04-01 08:00:00")
    LocalDateTime createTime;

    @Excel(title = "要求", width = 25)
    @ApiModelProperty(value ="要求", example = "2")
    String demand;

}
