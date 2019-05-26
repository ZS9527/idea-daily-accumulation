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
 * 父id查询
 *
 * @author zhangshuai
 * @date 2019/4/17 17:41
 */
@ApiModel
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DictionaryTypePidParam implements Serializable {

    private static final long serialVersionUID = 6356291824205851471L;

    @ApiModelProperty(value = "父级id(总根节点为1)", example = "2", required = true)
    @NotNull(message = "pid不能为空")
    Long pid;
}
