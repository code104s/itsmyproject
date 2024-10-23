package com.nothing.itsmyproject.service.impl;

import com.nothing.itsmyproject.service.BaseRedisService;
import java.util.Arrays;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class BaseRedisServiceImpl implements BaseRedisService {

  private final RedisTemplate<String, Object> redisTemplate;
  private final HashOperations<String, String, Object> hashOperations;

  public BaseRedisServiceImpl(RedisTemplate<String, Object> redisTemplate) {
    this.redisTemplate = redisTemplate;
    this.hashOperations = redisTemplate.opsForHash();
  }

  @Override
  public void setKey(String key, String value) {
    redisTemplate.opsForValue().set(key, value);
    System.out.println("Setting key..." + key + " with value..." + value);
  }

  @Override
  public void setKeyWithExpireTime(String key, long timeout) {
    redisTemplate.expire(key, timeout, java.util.concurrent.TimeUnit.SECONDS);
    System.out.println("Setting key..." + key + " with timeout..." + timeout);
  }

  @Override
  public void hashSet(String key, String field, String value) {
    hashOperations.put(key, field, value);
    System.out.println("Setting hash key..." + key + " with field..." + field + " and value..." + value);
  }

  @Override
  public boolean hashExist(String key, String field) {
    return hashOperations.hasKey(key, field);
  }

  @Override
  public void deleteKey(String key) {
    System.out.println("Deleting key..." + key);
    redisTemplate.delete(key);
  }

  @Override
  public void deleteHashKey(String key, String field) {
    System.out.println("Deleting hash key..." + key + " with field..." + field);
    hashOperations.delete(key, field);
  }

  @Override
  public void deleteHashKeys(String key, String... fields) {
    System.out.println("Deleting hash keys..." + key + " with fields..." + Arrays.toString(fields));
    hashOperations.delete(key, (Object) fields);
  }

}
