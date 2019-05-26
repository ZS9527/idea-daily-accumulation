package com.test.testidea.param.findproject;

import com.test.testidea.constant.Mock;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * 默认的项目查询参数类
 *
 * @author zhangshuai
 * @date 2019/5/15 9:08
 */

@ApiModel
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FindProjectParam {

    @ApiModelProperty(value ="项目名称", example = Mock.DEPARTMENTID)
    String projectName;

    @ApiModelProperty(value ="区域字典的id(不填为全部)", example = Mock.DEPARTMENTID)
    Long townshipId;

    @ApiModelProperty(value ="年度(不填为全部)", example = "2019")
    Integer year;

    @ApiModelProperty(value ="用户id", example = Mock.USERNAME)
    Long userId;

    @ApiModelProperty(value ="页数", example = "0")
    Integer pageStart;

    @ApiModelProperty(value ="条数", example = "10")
    Integer sizeStart;
}
