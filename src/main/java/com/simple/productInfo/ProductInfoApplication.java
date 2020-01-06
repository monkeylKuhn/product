package com.simple.productInfo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ProductInfoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductInfoApplication.class, args);
	}

}
