package com.beholder.watch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BeholderApplication {

	public static void main(String[] args) {
		SpringApplication.run(BeholderApplication.class, args);
	}

}
