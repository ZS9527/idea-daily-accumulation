package com.test.testidea.param.projectinfo;

import com.test.testidea.param.BasicPaginationParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * 分页查询项目
 *
 * @author zhangshuai
 * @date 2019/4/18 15:20
 */

@ApiModel
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProjectInfoPageParam extends BasicPaginationParam implements Serializable {

    private static final long serialVersionUID = -8864403550229075900L;

    @ApiModelProperty(value ="排序顺序(DESC,ASC)", example = "DESC")
    String order;
}
