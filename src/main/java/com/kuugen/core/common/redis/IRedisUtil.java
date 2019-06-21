package com.kuugen.core.common.redis;


import org.springframework.data.redis.core.ValueOperations;

public interface IRedisUtil {

     ValueOperations<String,Object> setObjRedis(String k, Object v);

     Object getObjRedis(String key);
}
