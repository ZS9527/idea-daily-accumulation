package com.test.testidea.vo.user.role;

import com.test.testidea.constant.Mock;
import com.test.testidea.domain.role.Role;
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
 * 角色分页查看
 *
 * @author zhangshuai
 * @date 2019/4/26 16:02
 */

@ApiModel
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RolePageVo extends BasicPaginationVo implements Serializable {

    private static final long serialVersionUID = 8297299702867636717L;

    /**
     * 数据
     */
    @ApiModelProperty(value = "数据", example = Mock.USER_NAME)
    List<Role> data;
}
