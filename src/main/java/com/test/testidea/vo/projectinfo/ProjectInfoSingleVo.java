package com.test.testidea.vo.projectinfo;

import com.test.testidea.domain.projectinfo.ProjectInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * 项目
 *
 * @author zhangshuai
 * @date 2019/4/18 15:30
 */

@ApiModel
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProjectInfoSingleVo  implements Serializable {

    private static final long serialVersionUID = 3525114108576429732L;

    @ApiModelProperty(value ="数据")
    ProjectInfo data;
}
