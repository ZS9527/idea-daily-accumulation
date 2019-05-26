package com.test.testidea.domain.projectinfo;

import com.test.testidea.domain.constructioncontent.ConstructionContent;
import com.test.testidea.domain.dictionary.DictionaryType;
import com.test.testidea.domain.guide.Guide;
import com.test.testidea.domain.uploadfile.FileOperation;
import com.test.testidea.domain.weightsort.WeightSort;
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
 * 项目
 *
 * @author zhangshuai
 * @date 2019/4/16 17:31
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Comment("项目")
@Table(name = "bs_project_info")
public class ProjectInfo implements Serializable {

    private static final long serialVersionUID = -2079824786038520972L;

    @Comment("主键")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Comment("区域项目名称")
    @ApiModelProperty(value ="区域项目名称", example = "2", required = true)
    String projectName;

    @Comment("指南名称")
    @ApiModelProperty(value ="指南名称", example = "2", required = true)
    @ManyToOne
    Guide guide;

    @Comment("部门")
    @ApiModelProperty(value ="部门", example = "2", required = true)
    @ManyToOne
    DictionaryType department;

    @Comment("申报年份")
    @ApiModelProperty(value ="申报年份", example = "2", required = true)
    Integer year;

    @Comment("创建时间")
    @ApiModelProperty(value ="创建时间", example = "2", required = true)
    LocalDateTime createTime;

    @Builder.Default
    @Comment("项目内容")
    @ApiModelProperty(value ="项目内容", example = "2", required = true)
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    Set<ConstructionContent> projectContent = new HashSet<>();

    @Builder.Default
    @Comment("附加文件集合")
    @ApiModelProperty(value ="附加文件集合", example = "2", required = true)
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    Set<FileOperation> uploadFiles = new HashSet<>();

    @Comment("备注")
    @ApiModelProperty(value ="备注", example = "2", required = true)
    @Column(columnDefinition = "Text")
    String remarks;

    @Builder.Default
    @Comment("权重")
    @ApiModelProperty(value ="权重", required = true)
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    Set<WeightSort> weightSorts = new HashSet<>();
}
