package com.test.testidea.vo.findproject.department;

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
 *  部门角色查看项目分页
 *
 * @author zhangshuai
 * @date 2019/4/22 10:13
 */

@ApiModel
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DepartmentProjectPageVo extends BasicPaginationVo implements Serializable {

    private static final long serialVersionUID = 2569603139529568654L;

    @ApiModelProperty(value ="主键", example = "1")
    List<DepartmentProjectPage> data;

}
