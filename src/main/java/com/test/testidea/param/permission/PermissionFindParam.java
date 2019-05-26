package com.test.testidea.param.permission;

import com.test.testidea.param.BasicPaginationParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * 查询权限接口请求参数
 *
 * @author ifzm
 * @version 0.1
 * @date 2019/4/28 10:04
 */

@ApiModel
@Valid
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PermissionFindParam extends BasicPaginationParam {

    private static final long serialVersionUID = 2489552703596754497L;

    @ApiModelProperty(value = "名称", example = "index")
    String name;

    @ApiModelProperty(value = "标签", example = "首页")
    String title;

    @ApiModelProperty(value = "权限URL",example = "/")
    String path;

    @ApiModelProperty(value = "权限URL模式（eg: *,GET,POST,PUT...）", example = "*")
    String method;

    @ApiModelProperty(value = "权限规则（1-前端路由,2-后端接口）", example = "1")
    Integer rule;

    @ApiModelProperty(value = "权限类型（1-菜单,2-按钮）", example = "1")
    Integer type;

    @ApiModelProperty(value = "描述", example = "首页")
    String description;

}
