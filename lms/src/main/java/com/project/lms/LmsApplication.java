package com.project.lms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableAutoConfiguration
@EnableMongoRepositories(basePackages="com.project.lms.repository.mongo") //to activate MongoDB repositories
@EnableJpaRepositories(basePackages="com.project.lms.repository.sql")
public class LmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(LmsApplication.class, args);
	}

	
}
