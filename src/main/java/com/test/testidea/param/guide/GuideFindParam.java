package com.test.testidea.param.guide;

import com.test.testidea.constant.Mock;
import com.test.testidea.param.BasicPaginationParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * 查询指南
 *
 * @author zhangshuai
 * @date 2019/4/18 10:58
 */

@ApiModel
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GuideFindParam extends BasicPaginationParam implements Serializable {

    private static final long serialVersionUID = -1760817746876024521L;

    @ApiModelProperty(value ="关键字", example = Mock.DEPARTMENTID)
    String keyWord;

    @ApiModelProperty(value ="专项资金类型Id(不传为全部)", example = Mock.SPECIALMONEYID)
    Long specialMoneyId;

    @ApiModelProperty(value ="状态", example = Mock.REVIEW)
    Integer review;
}
