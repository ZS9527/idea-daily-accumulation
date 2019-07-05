package com.test.testidea.vo.guide;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * 文件返回参数
 *
 * @author zhangshuai
 * @date 2019/4/23 20:25
 */
@ApiModel
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FileInfo {

    @ApiModelProperty(value ="文件id", example = "2")
    Long uid;

    @ApiModelProperty(value ="文件名", example = "2")
    String name;
}
