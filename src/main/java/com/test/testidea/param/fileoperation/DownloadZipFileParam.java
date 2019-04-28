package com.test.testidea.param.fileoperation;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * 下载文件参数
 *
 * @author zhangshuai
 * @date 2019/4/19 15:23
 */

@ApiModel
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DownloadZipFileParam implements Serializable {

    private static final long serialVersionUID = 6949915390509127768L;

    @ApiModelProperty(value ="数据id", example = "1", required = true)
    @NotNull(message = "要下载的数据id不为空")
    List<Long> fileId;
}
