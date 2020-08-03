package com.example.swagger.learn.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    public Object get(String key){
        return redisTemplate.opsForValue().get(key);
    }

    public boolean set(final String key, String value) {
        boolean result = false;
        try {
            redisTemplate.opsForValue().set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public void publishMsg(){
        redisTemplate.convertAndSend("topic","new message: hello word");
    }

    public boolean expire(String key, long time, TimeUnit unit){
        try{
            return redisTemplate.expire(key,time,unit);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

}
