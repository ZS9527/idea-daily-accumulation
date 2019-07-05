package com.test.testidea.vo.guide;

import com.test.testidea.constant.Mock;
import com.test.testidea.util.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * 指南返回
 *
 * @author zhangshuai
 * @date 2019/4/18 10:54
 */

@ApiModel
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GuidePage implements Serializable {

    private static final long serialVersionUID = 3306932620821177928L;

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

    @Excel(title = "状态", width = 25, format = "{ 3: \"未发布\", 1: \"已发布\", 2: \"驳回\" }")
    @ApiModelProperty(value ="状态", example = Mock.REVIEW)
    Integer review;
}
