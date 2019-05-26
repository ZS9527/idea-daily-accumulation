package com.test.testidea.vo.dictionarytype;

import com.test.testidea.domain.dictionary.DictionaryType;
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
 * 子节点结构
 *
 * @author zhangshuai
 * @date 2019/4/18 9:14
 */

@ApiModel
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DictionaryTypeChildNodeVo extends BasicPaginationVo implements Serializable {

    private static final long serialVersionUID = -4655084345636791405L;

    @ApiModelProperty(value = "子结构")
    List<DictionaryType> data;

}
