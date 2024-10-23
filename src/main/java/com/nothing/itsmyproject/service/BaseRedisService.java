package com.nothing.itsmyproject.service;

public interface BaseRedisService {

  void setKey(String key, String value);

  void setKeyWithExpireTime(String key, long timeout);

  void hashSet(String key, String field, String value);

  boolean hashExist(String key, String field);

  void deleteKey(String key);

  void deleteHashKey(String key, String field);

  void deleteHashKeys(String key, String... fields);
}
