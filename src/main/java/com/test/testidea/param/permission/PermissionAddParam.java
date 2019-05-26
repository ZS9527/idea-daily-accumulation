package com.test.testidea.param.permission;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * 添加权限接口请求参数
 *
 * @author ifzm
 * @version 0.1
 * @date 2019/4/28 10:04
 */

@ApiModel
@Valid
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PermissionAddParam implements Serializable {

    private static final long serialVersionUID = 2489552703596754497L;

    @ApiModelProperty(value = "名称", example = "index", required = true)
    String name;

    @ApiModelProperty(value = "标签", example = "首页", required = true)
    String title;

    @ApiModelProperty(value = "权限URL",example = "/", required = true)
    String path;

    @ApiModelProperty(value = "权限URL模式（eg: *,GET,POST,PUT...）", example = "*", required = true)
    String method;

    @ApiModelProperty(value = "权限规则（1-前端路由,2-后端接口）", example = "1", required = true)
    Integer rule;

    @ApiModelProperty(value = "权限类型（1-菜单,2-按钮）", example = "1", required = true)
    Integer type;

    @ApiModelProperty(value = "权限父级ID", example = "0", required = true)
    Long pid;

    @ApiModelProperty(value = "图标", example = "icon")
    String icon;

    @ApiModelProperty(value = "描述", example = "首页")
    String description;

}
