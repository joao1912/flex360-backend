package com.flex360.api_flex360;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ApiFlex360Application {

	public static void main(String[] args) {
		SpringApplication.run(ApiFlex360Application.class, args);
	}

}
