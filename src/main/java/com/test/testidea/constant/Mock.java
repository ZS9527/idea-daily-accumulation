package com.test.testidea.constant;


import com.test.testidea.secruity.JwtUtils;
import com.test.testidea.util.Commons;

/**
 * Mock Data
 *
 * @author ifzm
 * @version 0.1
 * @date 2019/3/22 16:03
 */

public interface Mock {

    /**
     * UUID
     */
    String UUID = "ba42a50fcde140ce971b949a70c75c78";

    /**
     * Token
     */
    String TOKEN = JwtUtils.TOKEN_PREFIX + "eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE1NjEwODU0MzAsInN1YiI6ImFkbWluIiwiY3JlYXRlZCI6MTU1ODQwNzAzMDk4Mn0.a4C9VeVSkS89U72FOSt1jcbP63bnpsHqphOM5bUNu2COPAJHKkXlXtLk-DEm-bK30nRP0LN3VeMWnDQ_bKi_5w";

    /**
     * 用户真实姓名
     */
    String REAL_NAME = "管理员";

    /**
     * 用户名
     */
    String USER_NAME = "admin";

    /**
     * 密码
     */
    String PASSWORD = "123456";
    /**
     * 手机号
     */
    String MOBILE = "13355552222";

    /**
     * 验证码
     */
    String CODE = "123456";

    /**
     * 验证码
     */
    String EMAIL = "13355552222@gmail.com";

    /**
     * 时间
     */
    String DATETIME = "2019-01-01 00:00:00";

    /**
     * IP
     */
    String IP = "192.168.1.1";


    /**
     * 图片地址
     */
    String IMAGE = "https://avatars2.githubusercontent.com/u/12740295?s=40&v=4";

    /**
     * true string
     */
    String TRUE = "true";

    /**
     * false string
     */
    String FALSE = "false";

    /**
     * 区域字典的id
     */
    String TOWNSHIPID = "26";

    /**
     * 部门字典的id
     */
    String DEPARTMENTID = "20";

    /**
     * 资金类型字典id
     */
    String SPECIALMONEYID = "103";

    /**
     * 专项项目类型字典id
     */
    String SPECIALNAMEID = "98";

    /**
     * 专项项目名称字典id
     */
    String SPECIALPROJECTNAMEID = "28";

    /**
     * 项目状态
     */
    String REVIEW = "3";

    /**
     * 指南id
     */
    String GUIDEID = "1";

    /**
     * 查询用用户名
     */
    String USERNAME = "16";


    /**
     * Generate Mock Data
     * @param args args
     */
    static void main(String[] args) {
        System.out.println("UUID: " + Commons.uuid());
    }
}
