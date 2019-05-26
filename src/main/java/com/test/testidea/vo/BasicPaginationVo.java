package com.test.testidea.vo;

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
 * 分页查询响应公共父类
 *
 * @author ifzm
 * @version 0.1
 * @date 2019/3/10 16:29
 */

@ApiModel
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BasicPaginationVo implements Serializable {

    private static final long serialVersionUID = 6103761701912769946L;

    @ApiModelProperty(value = "数据总数", example = "100")
    Long total;

}
