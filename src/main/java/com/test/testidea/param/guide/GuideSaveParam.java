package com.test.testidea.param.guide;

import com.test.testidea.constant.Mock;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * 保存指南
 *
 * @author zhangshuai
 * @date 2019/4/18 10:29
 */

@ApiModel
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GuideSaveParam implements Serializable {

    private static final long serialVersionUID = 1994606436614774092L;

    @ApiModelProperty(value ="修改时才需要填写要修改的指南id", example = "1")
    Long id;

    @ApiModelProperty(value ="指南名称不能为空", example = "进行科技项目申报的指南", required = true)
    @NotNull(message = "数据不能为空")
    String guideName;

    @ApiModelProperty(value ="专项资金类型Id不能为空", example = Mock.SPECIALMONEYID, required = true)
    @NotNull(message = "专项资金类型不能为空")
    Long specialMoneyId;

    @ApiModelProperty(value ="要求不能为空", example = "五彩斑斓的黑", required = true)
    @NotNull(message = "要求不能为空")
    String demand;

    @ApiModelProperty(value ="附件说明", example = "这里是一堆附件")
    String uploadText;

    @ApiModelProperty(value ="附件id", example = "2")
    ArrayList<Long> uploadFilesIds;

}
