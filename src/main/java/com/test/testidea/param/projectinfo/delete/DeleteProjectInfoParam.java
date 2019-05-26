package com.test.testidea.param.projectinfo.delete;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * 部门上报项目
 *
 * @author zhangshuai
 * @date 2019/4/18 18:02
 */

@ApiModel
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DeleteProjectInfoParam {

    @ApiModelProperty(value ="项目id", example = "1", required = true)
    List<Long> projectInfoId;

}
