package com.test.testidea.domain.guide;

import com.test.testidea.domain.dictionary.DictionaryType;
import com.test.testidea.domain.uploadfile.FileOperation;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotaions.Comment;

/**
 * 指南
 *
 * @author zhangshuai
 * @date 2019/4/16 10:21
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Comment("指南")
@Table(name = "bs_guide")
public class Guide implements Serializable {

    private static final long serialVersionUID = -6764640242481446193L;

    @Comment("主键")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value ="主键", example = "2")
    Long id;

    @Comment("指南名称")
    @ApiModelProperty(value ="指南名称", example = "2")
    String guideName;

    @Comment("专项资金类型")
    @ApiModelProperty(value ="专项资金类型", example = "2")
    @ManyToOne
    DictionaryType specialMoney;

    @Comment("指南创建时间")
    @ApiModelProperty(value ="指南创建时间", example = "2")
    LocalDateTime createTime;

    @Comment("指南修改时间")
    @ApiModelProperty(value ="指南修改时间", example = "2")
    LocalDateTime updateTime;

    @Comment("要求")
    @ApiModelProperty(value ="要求", example = "2")
    @Column(columnDefinition = "Text")
    String demand;

    @Comment("附件说明")
    @ApiModelProperty(value ="附件说明", example = "2")
    @Column(columnDefinition = "Text")
    String uploadText;

    @Builder.Default
    @Comment("附加文件集合")
    @ApiModelProperty(value ="附加文件集合", example = "2")
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    Set<FileOperation> uploadFiles = new HashSet<>();

    @Comment("状态")
    @ApiModelProperty(value ="状态", example = "2")
    Integer review;

}
