package com.mic3.personservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


@SpringBootApplication
@Slf4j
@EnableCaching
public class PersonServiceApplication {
	public static void main(String[] args) {
		log.info("starting application");
		SpringApplication.run(PersonServiceApplication.class, args);
	}

}
