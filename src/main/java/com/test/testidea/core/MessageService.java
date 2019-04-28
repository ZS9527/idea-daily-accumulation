package com.test.testidea.core;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.test.testidea.basic.Result;
import com.test.testidea.constant.RedisKey;
import com.test.testidea.util.Springs;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * 短信服务
 *
 * @author fangzhimin
 * @date 2018/9/4 15:39
 */

@Slf4j
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
@ConfigurationProperties(prefix = "config.sms")
public class MessageService {

    @Autowired
    Redis redis;

    /**
     * 默认验证码有效期
     */
    Long expired;
    String scOk;
    String product;
    String domain;
    String accessKeyId;
    String accessKeySecret;
    String signName;
    String templateCode;

    /**
     * 发送手机短信验证码
     *
     * @param phone 手机号
     * @return 公共的接口响应内容
     */
    public Result sendSmsCode(String phone) {
        try {
            String environment = "dev";
            if (environment.equals(Springs.getActiveProfile())) {
                String number = "123456";
                // 将短信验证码存入Redis
                boolean saved = redis.hset(RedisKey.SMS_VERIFICATION_CODE, phone, number, expired);
                if (saved) {
                    return Result.ok("OK", number);
                }

                return Result.no(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            }

            // 随机数验证码
            String number = (int) ((Math.random() * 9 + 1) * 100000) + "";
            // 超时时间
            System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
            System.setProperty("sun.net.client.defaultReadTimeout", "10000");

            // 初始化
            IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
            DefaultProfile.addEndpoint("cn-hangzhou", product, domain);
            IAcsClient acsClient = new DefaultAcsClient(profile);

            // 组装请求对象
            SendSmsRequest request = new SendSmsRequest();
            request.setSysMethod(MethodType.POST);
            request.setPhoneNumbers(phone);
            request.setSignName(signName);
            request.setTemplateCode(templateCode);
            request.setTemplateParam("{\"smsCode\":\"" + number + "\"}");

            // 发送请求，失败会抛出异常
            SendSmsResponse res = acsClient.getAcsResponse(request);
            if(res.getCode() != null && scOk.equals(res.getCode())) {
                // 将短信验证码存入Redis
                boolean saved = redis.hset(RedisKey.SMS_VERIFICATION_CODE, phone, number, expired);
                if (saved) {
                    return Result.ok(res.getMessage(), null);
                }

                return Result.no(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            }

            return Result.no(res.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Result.no();
        }
    }

}
