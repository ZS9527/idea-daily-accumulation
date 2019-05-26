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
 * 数据字典返回
 *
 * @author zhangshuai
 * @date 2019/4/17 14:37
 */

@ApiModel
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DictionaryTypeVo extends BasicPaginationVo implements Serializable{

    private static final long serialVersionUID = 1177701873405491873L;

    @ApiModelProperty(value = "数据")
    List<DictionaryType> data;
}
