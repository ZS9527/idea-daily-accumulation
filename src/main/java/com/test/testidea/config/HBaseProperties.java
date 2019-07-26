package com.test.testidea.config;

import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * hbase配置文件
 *
 * @author zhangshuai
 * @date 2019/7/11 10:57
 */
@ConfigurationProperties(prefix = "hbase")
public class HBaseProperties {

    private Map<String, String> config;

    public Map<String, String> getConfig() {
        return config;
    }

    public void setConfig(Map<String, String> config) {
        this.config = config;
    }


}