package com.test.testidea.vo.findproject.department;

import com.test.testidea.domain.constructioncontent.ConstructionContent;
import com.test.testidea.domain.uploadfile.FileOperation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;
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
public class DepartmentProjectInfoVo implements Serializable {

    private static final long serialVersionUID = 1584104437866661656L;

    @ApiModelProperty(value ="指南名称", example = "指南")
    String guideName;

    @ApiModelProperty(value ="专项项目名称", example = "项目名称")
    String projectName;

    @ApiModelProperty(value ="申报年份", example = "2019")
    Integer year;

    @ApiModelProperty(value ="项目内容", example = "字很多")
    List<ConstructionContent> projectContent;

    @ApiModelProperty(value ="备注", example = "字很多")
    String remarks;

    @ApiModelProperty(value ="附件说明", example = "字很多")
    String uploadText;

    @ApiModelProperty(value ="附件")
    List<FileOperation> uploadFiles;

}
