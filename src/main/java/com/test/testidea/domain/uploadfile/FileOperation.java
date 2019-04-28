package com.test.testidea.domain.uploadfile;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotaions.Comment;

/**
 * TODO
 *
 * @author zhangshuai
 * @date 2019/4/17 9:40
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Comment("上传文件")
@Table(name = "bs_upload_file")
public class FileOperation implements Serializable {

    private static final long serialVersionUID = -5795933027434302205L;

    @Comment("主键")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value ="主键")
    Long id;

    @Comment("文件名字")
    @ApiModelProperty(value ="文件名字")
    String fileName;

    @Comment("本地uuid保存文件名")
    @ApiModelProperty(value ="本地uuid保存文件名")
    String localFileName;

    @Comment("文件大小")
    @ApiModelProperty(value ="文件大小")
    Long fileSize;

    @Comment("创建时间")
    @ApiModelProperty(value ="创建时间")
    LocalDateTime createTime;

    @Comment("保存路径")
    @ApiModelProperty(value ="保存路径")
    String saveUrl;

}
