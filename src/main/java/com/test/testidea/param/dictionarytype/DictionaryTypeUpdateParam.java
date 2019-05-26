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
 * 更新字典类
 *
 * @author zhangshuai
 * @date 2019/4/19 14:23
 */

@ApiModel
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DictionaryTypeUpdateParam implements Serializable {

    private static final long serialVersionUID = -7606962747863031799L;

    @ApiModelProperty(value ="修改数据id不能为空", example = "1", required = true)
    @NotNull(message = "要修改数据id不能为空")
    Long id;

    @ApiModelProperty(value ="描述（修改时没做改动记得把之前的传给我）", example = "部门")
    String description;

    @ApiModelProperty(value ="名称不能为空", example = "部门", required = true)
    @NotNull(message = "名称")
    String name;
}
