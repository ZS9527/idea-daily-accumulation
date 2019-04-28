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
    String TOKEN = JwtUtils.TOKEN_PREFIX + "eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE1ODU0NDgxMTQsInN1YiI6IjEzMzU1NTU4ODg4IiwiY3JlYXRlZCI6MTU1MzgyNTcxNDI0OH0.xkXVkrpdT8Yc35q2xxyt1hdbuM8ilyBbjpuyiTsnAfT1_hgfkzF1scQA8spNanlXXxU9sxYzH6omBFIgL6ahlQ";

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
    String MOBILE = "13355558888";

    /**
     * 验证码
     */
    String CODE = "123456";

    /**
     * 验证码
     */
    String EMAIL = "devfzm@gmail.com";

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
     * 在押人员编号
     */
    String CUSTODY_UUID = "9527";

    /**
     * RTC AppId
     */
    String RTC_APP_ID = "1400192692";

    /**
     * RTC 房间号
     */
    String RTC_ROOM_ID = "100000001";

    /**
     * RTC 用户编号
     */
    String RTC_USER_ID = "95f5bd103d6449158d0d245cc03d1ac5";

    /**
     * RTC 用户签名
     */
    String RTC_USER_SIG = "eJw1kN1SgzAQRt*Fa0eTkKXgnfxocUSKMNV6k0mTAJlOU6RRi47vLmK5PWe-2f3226keykvedVoybpnbS*faQc7FhNWp071ivLaqHzEGAILQbLVUxupaTy6AGrYSI1d6lAYYfIkkoSDESDAXcM4cdTMOZ8kmSou4zT5RvjXkcLeySeNRN-Lsi73lWK*vhsfnzeIJikoOy9Vbk7Y3qUle181Jm0gc*2hffYTl0se7ohV5DGVW5RDDIvwKi-t8XiZ3bGr2dztFCAfEC8hZWr1X-51cHzB4M*dCHN6NZXbo1PSKn1*pulZT";

    /**
     * Generate Mock Data
     * @param args args
     */
    static void main(String[] args) {
        System.out.println("UUID: " + Commons.uuid());
//
//        Long appId = 1400192692L;
//        String privateKey = "-----BEGIN PRIVATE KEY-----\n"
//            + "MIGHAgEAMBMGByqGSM49AgEGCCqGSM49AwEHBG0wawIBAQQgn/CSKRlSKFWhehWN\n"
//            + "MjQ3EdzaKUcpl3jNWbsyzMjgNeihRANCAASrwf9teJxxVHSullpkBYXSaik550Ub\n"
//            + "NlquZCSJlI5UYXHQHwreTCqeU6YTZgg9TW5A1VJTUAC4QGo3TgFJWtLP\n"
//            + "-----END PRIVATE KEY-----";
//        String uuid = Commons.uuid();
//        String rtcUserSig = tls_sigature.genSig(appId, uuid, privateKey).urlSig;
//        System.out.println(uuid + ":" + rtcUserSig);
    }
}
