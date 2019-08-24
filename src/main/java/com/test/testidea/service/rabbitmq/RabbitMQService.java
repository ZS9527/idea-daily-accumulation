package com.test.testidea.service.rabbitmq;

/**
 * RabbitMQ
 *
 * @author zhangshuai
 * @date 2019/8/19 11:34
 */
public interface RabbitMQService {

    /**
     * 主题模式
     * @param key 主题队列名称
     * @param text 内容
     */
    public void topicQueue(String key, String text);

    /**
     * 订阅模式
     * @param key 队列名称
     * @param text 生产内容
     */
    public void fanoutQueue(String key, String text);

    /**
     * 手动确认是否消费
     * @param key
     * @param text
     */
    public void ackSend(String key, String text);

}
