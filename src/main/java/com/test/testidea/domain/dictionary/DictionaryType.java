package com.test.testidea.domain.dictionary;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotaions.Comment;

/**
 * 部门类型
 *
 * @author zhangshuai
 * @date 2019/4/16 16:38
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Comment("字典")
@Table(name = "bs_dictionary_type", uniqueConstraints = {@UniqueConstraint(columnNames="treeNode")})
public class DictionaryType implements Serializable {

    private static final long serialVersionUID = -2820820485049524675L;

    @Comment("主键")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value ="字典主键（修改时使用）")
    Long id;

    @Comment("名称")
    @ApiModelProperty(value ="名称", example = "部门")
    String name;

    @Comment("描述")
    @ApiModelProperty(value ="描述", example = "1级节点")
    String description;

    @Comment("是否为叶子结点")
    @ApiModelProperty(value ="叶子结点", example = "false")
    Boolean isLeaf;

    @Comment("权限父级ID")
    @ApiModelProperty(value ="权限父级ID,上一个节点的id（根节点有1，办公室。2，部门。3，区域）", example = "0")
    Long pid;

    @Comment("树节点标识")
    @ApiModelProperty(value ="树节点标识 包括他本身在内的一系列节点)")
    String treeNode;

}
