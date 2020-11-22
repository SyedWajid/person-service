package com.mic3.personservice;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@Slf4j
/**
 * Person Service
 * @author Syed Wajid
 */
public class PersonServiceApplication {
	public static void main(String[] args) {
		log.info("starting application");
		SpringApplication.run(PersonServiceApplication.class, args);
	}

	/**
	 * Using model mapper for conversion between dto and entity
	 * @return
	 */
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
