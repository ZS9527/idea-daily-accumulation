package com.test.testidea.param.dictionarytype;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * 新建字典表
 *
 * @author zhangshuai
 * @date 2019/4/19 14:13
 */

@ApiModel
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DictionaryTypeSaveParam implements Serializable {

    private static final long serialVersionUID = 7823268155075742424L;

    @ApiModelProperty(value ="权限父级ID,上一个节点的id（总根节点为1）", example = "1", required = true)
    @NotNull(message = "父级id结点")
    Long pid;

    @ApiModelProperty(value ="描述", example = "建设内容一级目录")
    String description;

    @ApiModelProperty(value ="名称", example = "基础设施", required = true)
    @NotNull(message = "名称不能为空")
    String name;
}
