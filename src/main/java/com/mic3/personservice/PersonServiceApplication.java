package com.mic3.personservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@Slf4j
public class PersonServiceApplication {
	public static void main(String[] args) {
		log.info("starting application");
		SpringApplication.run(PersonServiceApplication.class, args);
	}

}
