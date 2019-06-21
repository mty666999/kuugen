package com.kuugen.core.common.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
public class RedisUtil implements IRedisUtil{

    @Resource
    public RedisTemplate redisTemplate;

    @Override
    public ValueOperations<String,Object> setObjRedis(String key, Object value)  {
        ValueOperations<String,Object> cache=  redisTemplate.opsForValue();
        cache.set(key,value);
        return cache;
    }
    @Override
    public  Object getObjRedis(String key)  {
        return redisTemplate.opsForValue().get(key);
    }


    public Object delObjRedis(String key){
        return redisTemplate.opsForValue().getOperations().delete(key);
    }

}
