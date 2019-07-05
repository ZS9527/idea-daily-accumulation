package com.test.testidea.vo.guide;

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
 * TODO
 *
 * @author zhangshuai
 * @date 2019/4/23 15:08
 */

@ApiModel
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GuidePageVo  extends BasicPaginationVo implements Serializable {

    private static final long serialVersionUID = 8458894473437409860L;

    @ApiModelProperty(value ="数据")
    List<GuidePage> data;
}
