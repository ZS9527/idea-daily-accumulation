package com.test.testidea.param.projectinfo;

import com.test.testidea.constant.Mock;
import com.test.testidea.param.constructioncontent.ConstructionContentInsertParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * 项目
 *
 * @author zhangshuai
 * @date 2019/4/18 14:22
 */

@ApiModel
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SaveProjectInfoParam implements Serializable {

    private static final long serialVersionUID = -3951708218561597368L;

    @ApiModelProperty(value = "数据id修改时传入，新建时候不要传", example = "1")
    Long id;

    @ApiModelProperty(value ="项目名称可以为空", example = "项目名称", required = true)
    @NotNull(message = "项目名称不能为空")
    String projectName;

    @ApiModelProperty(value ="id不能为空(就是登录返回给你的组织id)", example = Mock.TOWNSHIPID, required = true)
    @NotNull(message = "id数据不能为空")
    Long departmentId;

    @ApiModelProperty(value ="指南字典id不能为空", example = Mock.GUIDEID, required = true)
    @NotNull(message = "指南字典数据不能为空")
    Long guideId;

    @ApiModelProperty(value ="年份不能为空", example = "2019", required = true)
    @NotNull(message = "年份数据不能为空")
    Integer year;

    @ApiModelProperty(value ="项目内容", required = true)
    @NotNull(message = "项目内容")
    List<ConstructionContentInsertParam> projectContent;

    @ApiModelProperty(value ="附件说明", example = "这里是附件说明")
    String uploadText;

    @ApiModelProperty(value ="备注", example = "这里是备注")
    String remarks;

    @ApiModelProperty(value ="附件id", example = "2")
    ArrayList<Long> uploadFilesIds;

}
