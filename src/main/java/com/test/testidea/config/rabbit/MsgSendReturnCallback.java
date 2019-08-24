package com.test.testidea.config.rabbit;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * 失败后return回调
 *
 * @author zhangshuai
 * @date 2019/8/19 17:25
 */
public class MsgSendReturnCallback implements RabbitTemplate.ReturnCallback {
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText,
        String exchange, String routingKey) {
        System.out.println("确认后回调return--message:"+new String(message.getBody())+",replyCode:"+replyCode+",replyText:"
            +replyText+",exchange:"+exchange+",routingKey:"+routingKey);
    }
}
