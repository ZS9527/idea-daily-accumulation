package com.test.testidea.vo.dictionarytype;

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
 * 字典树状图集合
 *
 * @author zhangshuai
 * @date 2019/4/17 17:44
 */
@ApiModel
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DictionaryTypeOrganizationTreeVo extends BasicPaginationVo implements Serializable {

    private static final long serialVersionUID = -7733281594261366370L;

    @ApiModelProperty(value = "树状结构")
    List<TypeTree> data;

}
