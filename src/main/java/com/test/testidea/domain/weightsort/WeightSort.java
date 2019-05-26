package com.test.testidea.domain.weightsort;

import com.test.testidea.domain.user.User;
import io.swagger.annotations.ApiModelProperty;
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
 *
 *  权重表
 * @author zhangshuai
 * @date 2019/5/17 15:50
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Comment("权重表")
@Table(name = "bs_weight_sort")
public class WeightSort {
    private static final long serialVersionUID = 1009267440536346667L;

    @Comment("主键")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value ="主键", example = "2")
    Long id;

    @Comment("绑定用户")
    @ApiModelProperty(value ="绑定用户")
    @ManyToOne
    User user;

    @Comment("权重")
    @ApiModelProperty(value ="权重", example = "2")
    Long weight;
}
