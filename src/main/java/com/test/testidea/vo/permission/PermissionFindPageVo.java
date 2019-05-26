package com.test.testidea.vo.permission;

import com.test.testidea.vo.BasicPaginationVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * 权限查询输出
 *
 * @author ifzm
 * @version 0.1
 * @date 2019/4/28 10:44
 */

@ApiModel("权限表格查询输出")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PermissionFindPageVo extends BasicPaginationVo {

    private static final long serialVersionUID = 8962707588924205561L;

    @ApiModelProperty(value = "权限集合", example = "[]")
    List<PermissionFindVo> data;

}
