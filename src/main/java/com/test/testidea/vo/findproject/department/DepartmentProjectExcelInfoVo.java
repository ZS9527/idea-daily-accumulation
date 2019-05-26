package com.test.testidea.vo.findproject.department;

import com.test.testidea.util.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 *  部门角色查看项目详情
 *
 * @author zhangshuai
 * @date 2019/4/22 10:13
 */

@ApiModel
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DepartmentProjectExcelInfoVo implements Serializable {

    private static final long serialVersionUID = 1584104437866661656L;

    @Excel(title = "序号", width = 25)
    @ApiModelProperty(value ="主键")
    Long id;

    @Excel(title = "创建时间", width = 25)
    @ApiModelProperty(value ="创建时间")
    LocalDateTime createTime;

    @Excel(title = "部门名称", width = 25)
    @ApiModelProperty(value ="部门名称", example = "业务部")
    String departmentName;

    @Excel(title = "项目名称", width = 25)
    @ApiModelProperty(value ="专项项目名称", example = "智能办公")
    String projectName;

    @Excel(title = "指南名称", width = 25)
    @ApiModelProperty(value ="指南名称", example = "指南")
    String guideName;

    @Excel(title = "申报年份", width = 25)
    @ApiModelProperty(value ="申报年份", example = "2019")
    Integer year;

    @Excel(title = "项目内容", width = 25)
    @ApiModelProperty(value ="项目内容", example = "字很多")
    String projectContent;

    @Excel(title = "备注", width = 25)
    @ApiModelProperty(value ="备注", example = "字很多")
    String remarks;

}
