package com.test.testidea.vo.permission;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * 查询权限信息
 *
 * @author ifzm
 * @version 0.1
 * @date 2019/4/28 10:04
 */

@ApiModel("权限信息")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PermissionFindVo implements Serializable {

    private static final long serialVersionUID = 2489552703596754497L;

    @ApiModelProperty(value = "主键", example = "1")
    Long id;

    @ApiModelProperty(value = "名称", example = "index")
    String name;

    @ApiModelProperty(value = "标签", example = "首页")
    String title;

    @ApiModelProperty(value = "图标", example = "icon")
    String icon;

    @ApiModelProperty(value = "权限URL",example = "/")
    String path;

    @ApiModelProperty(value = "权限URL模式（eg: *,GET,POST,PUT...）", example = "*")
    String method;

    @ApiModelProperty(value = "权限类型（1-前端路由,2-后端接口）", example = "1")
    Integer type;

    @ApiModelProperty(value = "描述", example = "首页")
    String description;

    @ApiModelProperty(value = "权限父级ID", example = "1")
    Long pid;

}
