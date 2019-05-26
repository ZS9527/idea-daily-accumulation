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
    String URL = "D:" + File.separator + File.separator + "finance" + File.separator;

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
     * String REMOTE_PATH = "http://192.168.1.245:8181/" + PATH_PREFIX;
     */
    String REMOTE_PATH = "https://zjtq.ifzm.cn/meeting/business/" + PATH_PREFIX;

    /***
     * 零点
     */
    Long ZERO = 1L;

    /**
     * 办公室
     */
    Long BUREAU = 2L;

    /**
     * 部门级
     */
    Long DEPARTMENT = 3L;

    /**
     * 区域级
     */
    Long TOWNSHIP = 4L;

    /**
     * 办公室
     */
    String BUREAUCN = "办公室";

    /**
     * 部门级
     */
    String DEPARTMENTCN = "部门";

    /**
     * 区域级
     */
    String TOWNSHIPCN = "区域";

    /**
     * 管理员
     */
    String ADMINCN = "超级管理员";

    /**
     * 同意
     */
    Integer AGREE = 1;

    /**
     * 不同意
     */
    Integer DISAGREE = 2;

    /**
     * 等待
     */
    Integer WAIT = 3;

}
