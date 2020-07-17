package com.jackdyt.blog.cache;

import com.jackdyt.blog.utils.ApplicationContextUtils;
import org.apache.ibatis.cache.Cache;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

public class RedisCache implements Cache {

    private final String id;



    public RedisCache(String id){
        this.id = id;
    }

    private RedisTemplate defineRedis(){
        RedisTemplate redisTemplate = (RedisTemplate) ApplicationContextUtils.getBean("redisTemplate");
        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }
    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void putObject(Object key, Object val) {
        RedisTemplate redisTemplate = defineRedis();

        redisTemplate.opsForHash().put(id.toString(), key.toString(), val);

    }

    @Override
    public Object getObject(Object key) {
        RedisTemplate redisTemplate = defineRedis();
        return redisTemplate.opsForHash().get(id.toString(), key.toString());
    }

    @Override
    public Object removeObject(Object key) {
        System.out.println("remove method is called");
        return null;
    }

    @Override
    public void clear() {
//        System.out.println("call clear method in redis cache");
        RedisTemplate redisTemplate = defineRedis();
        redisTemplate.delete(id.toString());
    }

    @Override
    public int getSize() {
        RedisTemplate redisTemplate = defineRedis();
        return redisTemplate.opsForHash().size(id.toString()).intValue();
    }
}
