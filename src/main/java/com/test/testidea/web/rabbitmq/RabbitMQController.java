package com.test.testidea.web.rabbitmq;

import com.test.testidea.service.rabbitmq.RabbitMQService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试mq队列应用
 *
 * @author zhangshuai
 * @date 2019/8/19 11:26
 */

@RestController
@RequestMapping("/rabbitmq/")
public class RabbitMQController {

    @Autowired
    RabbitMQService rabbitMQService;

    /**
     * 主题模式
     * @param text token
     * @return 查询结果
     */
    @ApiOperation("主题模式")
    @GetMapping("topicQueue")
    public void topicQueue(String key, String text) {
        rabbitMQService.topicQueue(key, text);
    }

    /**
     * 订阅模式
     * @param text token
     * @return 查询结果
     */
    @ApiOperation("订阅模式")
    @GetMapping("fanoutQueue")
    public void fanoutQueue(String key, String text) {
        rabbitMQService.fanoutQueue(key, text);
    }

    /**
     * 手动确认是否消费
     * @param text token
     * @return 查询结果
     */
    @ApiOperation("手动确认是否消费")
    @GetMapping("ackSend")
    public void ackSend(String key, String text) {
        rabbitMQService.ackSend(key, text);
    }
}
