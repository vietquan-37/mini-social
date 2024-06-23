package com.example.social_web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SocialWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(SocialWebApplication.class, args);
	}

}
