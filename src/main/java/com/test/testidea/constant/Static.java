package com.test.testidea.constant;

import java.io.File;

/**
 * 静态映射常量
 *
 * @author ifzm
 * @version 0.1
 * @date 2019/3/29 9:02
 */

public interface Static {

    /**
     * 保存路径源头
     */
    String URL = "D:" + File.separator + File.separator + "meeting" + File.separator;

    /**
     * 本地路径
     */
    String LOCAL_PATH = "D://meeting/";

    /**
     * 映射前缀
     */
    String PATH_PREFIX = "static";

    /**
     * 映射路径
     */
//    String REMOTE_PATH = "http://192.168.1.245:8181/" + PATH_PREFIX;
    String REMOTE_PATH = "https://zjtq.ifzm.cn/meeting/business/" + PATH_PREFIX;

}
