package com.test.testidea.domain.constructioncontent;

import com.test.testidea.domain.dictionary.DictionaryType;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotaions.Comment;

/**
 * 项目内容
 *
 * @author zhangshuai
 * @date 2019/5/5 19:54
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Comment("项目内容")
@Table(name = "bs_construction_content")
public class ConstructionContent implements Serializable {

    private static final long serialVersionUID = 5875548459251456663L;

    @Comment("主键")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value ="项目内容主键（修改时使用）", example = "2")
    Long id;

    @Comment("数量")
    @ApiModelProperty(value ="数量", example = "1")
    Double num;

    @Comment("项目内容字典")
    @ApiModelProperty(value ="项目内容字典", example = "1")
    @ManyToOne
    DictionaryType dictionaryType;

}
