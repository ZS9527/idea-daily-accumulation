package com.test.testidea.util;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;

/**
 * 公共工具类
 *
 * @author ifzm
 * @version 0.1
 * @date 2019/3/5 20:29
 */

public class Commons {

    /**
     * 代理 IP 请求头
     */
    private static final String[] HEADERS_TO_TRY = {
        "X-Forwarded-For",
        "Proxy-Client-IP",
        "WL-Proxy-Client-IP",
        "HTTP_X_FORWARDED_FOR",
        "HTTP_X_FORWARDED",
        "HTTP_X_CLUSTER_CLIENT_IP",
        "HTTP_CLIENT_IP",
        "HTTP_FORWARDED_FOR",
        "HTTP_FORWARDED",
        "HTTP_VIA",
        "REMOTE_ADDR",
        "X-Real-IP"
    };

    private Commons() {
        throw new IllegalStateException("Utility class");
    }

    /***
     * 获取客户端ip地址(可以穿透代理)
     * @param request HttpServletRequest
     * @return IP
     */
    public static String getClientIpAddress(HttpServletRequest request) {
        for (String header : HEADERS_TO_TRY) {
            String ip = request.getHeader(header);
            if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
                return ip;
            }
        }

        return request.getRemoteAddr();
    }

    /**
     * 随机生成一个UUID
     * @return UUID 字符串
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 判断集合是否为空
     * @param collection 集合
     * @return 是否为空
     */
    public static boolean isNullOrEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * 判断Map是否为空
     * @param map Map
     * @return 是否为空
     */
    public static boolean isNullOrEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    /**
     * 判断Array是否为空
     * @param array Object[]
     * @return 是否为空
     */
    public static boolean isNullOrEmpty(Object[] array) {
        return array == null || array.length == 0;
    }

}
