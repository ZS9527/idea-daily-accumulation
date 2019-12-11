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
 * 环保站点
 * @author ZengWei
 * @date 2019/11/04 20:40
 */

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EnvirStationExcel implements Serializable {

    private static final long serialVersionUID = 4664129718464597368L;

    String city;

    String name;

    String stationCode;

    String cityCode;

    String stationCodeTwo;

    String lon;

    String lat;

    String type;

    String lonStr;

    String latStr;
}
