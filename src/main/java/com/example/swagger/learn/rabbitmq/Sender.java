package com.example.swagger.learn.rabbitmq;

import com.example.swagger.learn.config.RabbitMQConfig;
import com.example.swagger.learn.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Sender {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void sendDirectQueue() {
        User user = new User();
        user.setId("xi");
        user.setName("chinese wedding");
        log.info("【sendDirectQueue已发送消息】");
        // 第一个参数是指要发送到哪个队列里面， 第二个参数是指要发送的内容
        this.amqpTemplate.convertAndSend(RabbitMQConfig.QUEUE, user);
    }

}
