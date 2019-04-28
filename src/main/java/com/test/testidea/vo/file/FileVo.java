package com.test.testidea.vo.file;

import com.test.testidea.domain.uploadfile.FileOperation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * 文件返回
 *
 * @author zhangshuai
 * @date 2019/4/17 14:31
 */

@ApiModel
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FileVo implements Serializable {

    private static final long serialVersionUID = -3369796671010875160L;

    @ApiModelProperty(value = "文件")
    FileOperation data;
}
