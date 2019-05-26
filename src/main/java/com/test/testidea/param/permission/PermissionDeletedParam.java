package com.test.testidea.param.permission;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * 删除权限接口请求参数
 *
 * @author ifzm
 * @version 0.1
 * @date 2019/4/28 10:04
 */

@ApiModel
@Valid
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PermissionDeletedParam implements Serializable {

    private static final long serialVersionUID = 2489552703596754497L;

    @ApiModelProperty(value = "主键", example = "1", required = true)
    Long id;

}
