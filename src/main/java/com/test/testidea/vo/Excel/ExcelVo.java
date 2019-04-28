package com.test.testidea.vo.Excel;

import com.test.testidea.util.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * 导出Excel表实例
 *
 * @author zhangshuai
 * @date 2019/4/28 9:36
 */

@ApiModel
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ExcelVo {

    @Excel(title = "序号", width = 25)
    @ApiModelProperty(value ="主键", example = "1")
    Long id;

    @Excel(title = "名称", width = 25)
    @ApiModelProperty(value ="名称", example = "1")
    String name;

    @Excel(title = "状态", width = 25, format = "{ 3: \"未报到\", 1: \"已报到\" }")
    @ApiModelProperty(value ="状态(3-未报到,1-已报到)", example = "1")
    Integer review;

}
