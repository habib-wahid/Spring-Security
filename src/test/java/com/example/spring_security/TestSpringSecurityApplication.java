package com.example.spring_security;

import org.springframework.boot.SpringApplication;

public class TestSpringSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.from(SpringSecurityApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
