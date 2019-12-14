package com.test.testidea.domain.emi;

import java.io.Serializable;
import javax.persistence.Entity;
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
 * 用户表
 *
 * @author zhangshuai
 * @date 2019/12/11 8:53
 */

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Comment("字典")
@Table(name = "dic_national_air_station")
public class EasyExcelUser implements Serializable {
    String city;

    String cityCode;

    String address;

    String stationCode;

    String area;

    String cityTypeCode;

    String province;

    String lat;

    String lon;

}
