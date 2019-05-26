package com.test.testidea.vo.dictionarytype;

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
 * 树状结构
 *
 * @author zhangshuai
 * @date 2019/4/17 17:49
 */

@ApiModel
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TypeTree implements Serializable {

    private static final long serialVersionUID = -4006999977458409355L;

    @ApiModelProperty(value = "名字")
    String title;

    @ApiModelProperty(value = "key")
    Long key;

    @ApiModelProperty(value = "描述")
    String description;

    @ApiModelProperty(value = "是否有子节点")
    Boolean isLeaf;

    @ApiModelProperty(value = "子节点")
    List<TypeTree> children;

}
