package com.test.testidea.domain.emi;

import java.io.Serializable;
import javax.persistence.Entity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

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
public class EasyExcelUser implements Serializable {
    String city;

    String name;

    String stationCode;

    String cityCode;

}
