package com.nothing.itsmyproject;

import io.lettuce.core.protocol.DemandAware.Sink;
import javax.annotation.processing.Processor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableCaching
@EnableJpaRepositories(basePackages = "com.nothing.itsmyproject.repository")
@EntityScan(basePackages = "com.nothing.itsmyproject.entity")
public class ItsmyprojectApplication {

  public static void main(String[] args) {
    SpringApplication.run(ItsmyprojectApplication.class, args);
  }

}
