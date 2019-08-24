package com.test.testidea.config.rabbit;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * 确认后回调
 *
 * @author zhangshuai
 * @date 2019/8/19 17:26
 */
public class MsgSendConfirmCallBack implements RabbitTemplate.ConfirmCallback {
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (ack) {
            System.out.println("消息确认成功cause"+cause);
        } else {
            //处理丢失的消息
            System.out.println("消息确认失败:"+correlationData.getId()+"#cause"+cause);
        }
    }
}
