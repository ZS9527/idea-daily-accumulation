package com.test.testidea.constant;

import java.time.format.DateTimeFormatter;

/**
 * 初始化一下常用的日期格式化规则
 *
 * @author ifzm
 * @version 0.1
 * @date 2019/3/22 14:08
 */

public interface DateFormatter {

    /**
     * DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
     */
    DateTimeFormatter DATE_TIME = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
     */
    DateTimeFormatter DATE = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * DateTimeFormatter.ofPattern("HH:mm:ss")
     */
    DateTimeFormatter TIME = DateTimeFormatter.ofPattern("HH:mm:ss");

}
