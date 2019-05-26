package com.test.testidea.param.dictionarytype;

import com.test.testidea.param.BasicPaginationParam;
import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * 数据字典查询参数
 *
 * @author zhangshuai
 * @date 2019/4/17 14:42
 */

@ApiModel
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DictionaryTypeParam extends BasicPaginationParam implements Serializable {

    private static final long serialVersionUID = 7627081436946254578L;

    String name;

}
