package com.test.testidea.domain.commodity;

import java.io.Serializable;
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
 * 商品实体类
 *
 * @author zhangshuai
 * @date 2019/4/28 21:25
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Comment("商品表")
@Table(name = "SYSTEM_PERMISSION")
public class Commodity implements Serializable {

    private static final long serialVersionUID = -6502072349600248357L;

    @Comment("主键")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Comment("名称")
    String name;

    @Comment("价钱")
    Integer price;

    @Comment("数量")
    Integer amount;
}
