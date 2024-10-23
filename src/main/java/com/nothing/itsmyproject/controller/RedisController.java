package com.nothing.itsmyproject.controller;


import com.nothing.itsmyproject.service.BaseRedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/redis")
@RequiredArgsConstructor
public class RedisController {

  private final BaseRedisService baseRedisService;

  @RequestMapping("/set")
  public String setKey() {
    baseRedisService.setKey("key", "value");
    return "Key set successfully";
  }

  @PostMapping
  public String test() {
    return "Hello World";
  }

}
