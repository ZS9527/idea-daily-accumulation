package com.test.testidea.vo.user;

import com.test.testidea.vo.BasicPaginationVo;
import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * 用户的所有信息
 *
 * @author ifzm
 * @version 0.1
 * @date 2019/3/22 15:53
 */

@ApiModel
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserFindDataVo extends BasicPaginationVo implements Serializable {

    private static final long serialVersionUID = -2516463620470328829L;

    List<UserFindData> data;
}
