package com.nothing.itsmyproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ItsmyprojectApplication {

	public static void main(String[] args) {
		SpringApplication.run(ItsmyprojectApplication.class, args);
	}

}
