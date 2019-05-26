package com.test.testidea.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * 公共的树结构数据输出VO
 *
 * @author ifzm
 * @version 0.1
 * @date 2019/4/28 14:54
 */

@ApiModel
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BasicTreeVo implements Serializable {

    private static final long serialVersionUID = 8123962356537492053L;

    @ApiModelProperty(value = "key/id", example = "1")
    Long key;

    @ApiModelProperty(value = "节点图标", example = "icon")
    String icon;

    @ApiModelProperty(value = "节点名称", example = "根节点")
    String title;

    @ApiModelProperty(value = "是否有子节点", example = "false")
    Boolean isLeaf;

    @ApiModelProperty(value = "子节点集合")
    List<BasicTreeVo> children;
}
