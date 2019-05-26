package com.test.testidea.vo.findproject.department;

import com.test.testidea.util.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * 部门角色查看项目分页
 *
 * @author zhangshuai
 * @date 2019/4/22 16:17
 */
@ApiModel
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DepartmentProjectPage {

    @Excel(title = "序号", width = 25)
    @ApiModelProperty(value ="主键", example = "1")
    Long id;

    @Excel(title = "项目名称", width = 25)
    @ApiModelProperty(value ="专项项目名称", example = "项目名称")
    String projectName;

    @Excel(title = "创建时间", width = 25)
    @ApiModelProperty(value ="创建时间", example = "2019-04-22 08:00:00")
    LocalDateTime createTime;
}
