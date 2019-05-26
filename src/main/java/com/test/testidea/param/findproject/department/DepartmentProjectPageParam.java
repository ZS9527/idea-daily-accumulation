package com.test.testidea.param.findproject.department;

import com.test.testidea.constant.Mock;
import com.test.testidea.param.BasicPaginationParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * 部门角色查看项目详情
 *
 * @author zhangshuai
 * @date 2019/4/22 11:23
 */

@ApiModel
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DepartmentProjectPageParam extends BasicPaginationParam implements Serializable {

    private static final long serialVersionUID = 2451306147029766642L;

    @ApiModelProperty(value ="项目名称", example = Mock.DEPARTMENTID)
    String keyWord;

    @ApiModelProperty(value ="部门字典的id(不填为全部)", example = Mock.DEPARTMENTID)
    Long departmentId;

    @ApiModelProperty(value ="年度(不填为全部)", example = "2019")
    Integer year;

    @ApiModelProperty(value ="用户id", example = Mock.USERNAME, required = true)
    @NotNull(message = "用户id不为空")
    Long sortUserId;
}
