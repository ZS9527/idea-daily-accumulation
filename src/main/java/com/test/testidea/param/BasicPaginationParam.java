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
 * 分页查询参数公共父类
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
public class BasicPaginationParam implements Serializable {

    private static final long serialVersionUID = 6103761701912769946L;

    @ApiModelProperty(value = "分页条数", example = "10", required = true)
    @NotNull(message = "分页条数不能为空")
    Integer size;

    @ApiModelProperty(value = "当前页数", example = "0", required = true)
    @NotNull(message = "当前页数不能为空")
    Integer page;

    @ApiModelProperty(value = "排序字段集合", example = "[\"datetime\"]")
    String[] properties;

    @ApiModelProperty(value = "排序规则（desc,asc）", example = "desc")
    String direction;

}
