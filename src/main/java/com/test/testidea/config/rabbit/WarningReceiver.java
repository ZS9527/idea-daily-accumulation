package com.test.testidea.config.rabbit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * RabbitMQ
 *
 * @author ifzm
 * @version 0.1
 * @date 2019/7/13 17:00
 */

@Slf4j
@Component
@Transactional(rollbackFor = Exception.class)
public class WarningReceiver {

    /**
     * 乡镇层级项目预警检查
     * @param text 项目ID
     */
    @RabbitHandler
    @RabbitListener(queues = WarningRabbitConfig.TOWNSHIP)
    public void township(String text) {
        System.out.println("township:" + text);
    }

    /**
     * 部门层级项目预警检查
     * @param text 项目ID
     */
    @RabbitHandler
    @RabbitListener(queues = WarningRabbitConfig.DEPARTMENT)
    public void department(String text) {
        System.out.println("department:" + text);
    }

    /**
     * 领导小组层级项目预警检查
     * @param text 项目ID
     */
    @RabbitHandler
    @RabbitListener(queues = WarningRabbitConfig.LEADER)
    public void leader(String text) {
        System.out.println("leader:" + text);
    }

}
