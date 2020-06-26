package com.imooc.sell.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @author XuMingyue
 * @create 2020-06-24 22:32
 * redis分布式锁处理
 */
@Component
@Slf4j
public class RedisLock {
    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 加锁
     * @param key
     * @param value =当前时间+超时时间
     * @return
     */
    public  boolean lock(String key,String value){
        //判断是否可以成功设置
        if(redisTemplate.opsForValue().setIfAbsent(key,value)){
            //成功加锁
            return true;
        }
        String currentValue=redisTemplate.opsForValue().get(key);
        // 锁过期
        //内容不为空，且 小于当前时间
        if(!StringUtils.isEmpty(currentValue) &&
               Long.parseLong(currentValue)<System.currentTimeMillis()){
            //获取上一个锁时间|
            String oldValue = redisTemplate.opsForValue().getAndSet(key, value);
            //equals相等
            if(!StringUtils.isEmpty(oldValue)&& oldValue.equals(currentValue)){
                return  true;
            }
        }
        //加锁失败
        return  false;
    }

    /**
     * 解锁
     * @param key
     * @param value
     */
    public  void  unlock(String key,String value){
       try {
           String currentValue = redisTemplate.opsForValue().get(key);
           if(!StringUtils.isEmpty(currentValue) &&currentValue.equals(value)){
               //解锁
               redisTemplate.opsForValue().getOperations().delete(key);
           }

       }catch (Exception e){
            log.error("【redis分布式锁】 解锁异常{}",e);
       }

    }


}
