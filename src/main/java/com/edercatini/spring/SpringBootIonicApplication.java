package com.edercatini.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class SpringBootIonicApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootIonicApplication.class, args);
	}
}