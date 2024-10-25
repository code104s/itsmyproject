package com.nothing.itsmyproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/redis")
public class RedisController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @PostMapping("/set")
    public void setKey(@RequestParam String key, @RequestParam String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @GetMapping("/get")
    public String getKey(@RequestParam String key) {
        return redisTemplate.opsForValue().get(key);
    }
}