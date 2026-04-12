package com.springsecurity.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.springsecurity.webapp.jpa.UserRepository;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
public class WebappApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebappApplication.class, args);
	}

}