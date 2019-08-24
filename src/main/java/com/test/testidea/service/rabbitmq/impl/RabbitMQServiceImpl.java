package com.test.testidea.service.rabbitmq.impl;

import com.test.testidea.service.rabbitmq.RabbitMQService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * RabbitMQ
 *
 * @author zhangshuai
 * @date 2019/8/19 17:31
 */
@Service
@Slf4j
public class RabbitMQServiceImpl implements RabbitMQService {

    @Autowired
    AmqpTemplate amqpTemplate;

    @Override
    public void topicQueue(String key, String text) {
        amqpTemplate.convertAndSend(key, text);
    }

    @Override
    public void fanoutQueue(String key, String text) {
        amqpTemplate.convertAndSend("fanoutExchange","", text);
    }

    @Override
    public void ackSend(String key, String text) {

    }
}
