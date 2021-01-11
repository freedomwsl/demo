package com.example.demo.controller;


import com.example.demo.entity.UserEntity;
import com.example.demo.util.RedisUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.*;

/**
 * @program: springbootdemo
 * @Date: 2019/1/25 15:03
 * @Author: Mr.Zheng
 * @Description:
 */

@RequestMapping("/redis")
@RestController
public class RedisController {

    private static int ExpireTime = 60;
    // redis中存储的过期时间60s
    @Autowired
    private RedisTemplate redisTemplate;
    @Resource
    private RedisUtil redisUtil;

    @RequestMapping("set")
    public boolean redisset(String key, String value){
        UserEntity userEntity =new UserEntity();
        userEntity.setId(Long.valueOf(1));
        userEntity.setGuid(String.valueOf(1));
        userEntity.setName("zhangsan");
        userEntity.setAge(String.valueOf(20));
        userEntity.setCreateTime(new Date());
        return redisUtil.set(key,userEntity,ExpireTime);

//        return redisUtil.set(key,value);
    }
    @RequestMapping("hashKey")
    public boolean hashkey(String key){
        return redisUtil.hasKey(key);
    }

    public boolean testHash(){
        return redisTemplate.opsForZSet().add("wangyueyu","18",20);
    }


    @RequestMapping("get")
    public List redisget(String key){
        ArrayList<Object> list = new ArrayList<>();

        list.add("1");
        return redisTemplate.opsForHash().multiGet("hmall:CaChe:{cart}Cart:2", list);
    }

    @RequestMapping("expire")
    public boolean expire(String key){
        return redisUtil.expire(key,ExpireTime);
    }

}