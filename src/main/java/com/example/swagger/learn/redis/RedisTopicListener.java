package com.example.swagger.learn.redis;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

public class RedisTopicListener extends MessageListenerAdapter {

    @Override
    public void onMessage(Message message, byte[] pattern) {
        // 用户做自己的业务处理即可,注意message.toString()可以获取失效的key
        logger.info(message);
        logger.info(new String(pattern));
    }
}
