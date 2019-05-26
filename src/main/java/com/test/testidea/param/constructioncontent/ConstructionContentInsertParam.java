package com.test.testidea.param.constructioncontent;

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
 * 新建项目内容
 *
 * @author zhangshuai
 * @date 2019/5/6 16:07
 */
@ApiModel
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConstructionContentInsertParam implements Serializable {

    @ApiModelProperty(value ="数量", example = "100", required = true)
    @NotNull(message = "数量不为空")
    Double num;

    @ApiModelProperty(value ="字典ID", example = "66", required = true)
    @NotNull(message = "字典ID不为空")
    Long dictionaryTypeId;
}
