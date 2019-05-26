package com.test.testidea.vo.projectinfo;

import com.test.testidea.domain.projectinfo.ProjectInfo;
import com.test.testidea.vo.BasicPaginationVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * 项目
 *
 * @author zhangshuai
 * @date 2019/4/18 15:18
 */

@ApiModel
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProjectInfoVo extends BasicPaginationVo implements Serializable {

    private static final long serialVersionUID = -7213330600055842022L;

    @ApiModelProperty(value ="数据")
    List<ProjectInfo> data;
}
