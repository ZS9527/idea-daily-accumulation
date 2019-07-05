package com.test.testidea.vo.guide;

import com.test.testidea.domain.dictionary.DictionaryType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * 返回指南详情
 *
 * @author zhangshuai
 * @date 2019/4/23 20:18
 */

@ApiModel
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GuideInfoVo implements Serializable {

    private static final long serialVersionUID = 109721821931387058L;

    @ApiModelProperty(value ="指南名称", example = "2")
    String guideName;

    @ApiModelProperty(value ="专项资金类型", example = "2")
    DictionaryType specialMoney;

    @ApiModelProperty(value ="要求", example = "2")
    String demand;

    @ApiModelProperty(value ="附件要求", example = "2")
    String uploadText;

    @ApiModelProperty(value ="附件", example = "2")
    List<FileInfo> fileInfos;

}
