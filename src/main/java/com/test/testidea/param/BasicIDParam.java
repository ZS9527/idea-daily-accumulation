package com.test.testidea.param;

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
 * 公用id参数
 *
 * @author zhangshuai
 * @date 2019/3/25 20:19
 */

@ApiModel
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BasicIDParam implements Serializable {

    private static final long serialVersionUID = -8791536233715673359L;

    @ApiModelProperty(value = "数据id", example = "0", required = true)
    @NotNull(message = "数据id不能为空")
    Long id;
}
