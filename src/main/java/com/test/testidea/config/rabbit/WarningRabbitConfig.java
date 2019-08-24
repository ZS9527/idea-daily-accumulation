package com.test.testidea.config.rabbit;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 预警消息配置
 *
 * @author ifzm
 * @version 0.1
 * @date 2019/7/13 16:51
 */

@Configuration
public class WarningRabbitConfig {

    /**
     * 乡镇预警
     */
    public static final String TOWNSHIP = "warning.township";

    /**
     * 部门预警
     */
    public static final String DEPARTMENT = "warnin.department";

    /**
     * 领导小组预警
     */
    public static final String LEADER = "warning.leader";

    /**
     * 默认的相似度级别，分别对应红橙黄三个预警级别
     */
    public static final Double RED_MIN_VALUE = 0.9D;
    public static final Double ORANGE_MIN_VALUE = 0.8D;
    public static final Double YELLOW_MIN_VALUE = 0.7D;

    @Bean
    public Queue queueTownship() {
        return new Queue(TOWNSHIP);
    }

    @Bean
    public Queue queueDepartment() {
        return new Queue(DEPARTMENT);
    }

    @Bean
    public Queue queueLeader() {
        return new Queue(LEADER);
    }

    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange("fanoutExchange");
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange("exchange");
    }

    @Bean
    Binding bindingExchangeTownship(Queue queueTownship, FanoutExchange exchange) {
        return BindingBuilder.bind(queueTownship).to(exchange);
    }

    @Bean
    Binding bindingExchangeDepartment(Queue queueDepartment, TopicExchange exchange) {
        return BindingBuilder.bind(queueDepartment).to(exchange).with(DEPARTMENT);
    }

    @Bean
    Binding bindingExchangeLeader(Queue queueLeader, FanoutExchange exchange) {
        return BindingBuilder.bind(queueLeader).to(exchange);
    }

}
