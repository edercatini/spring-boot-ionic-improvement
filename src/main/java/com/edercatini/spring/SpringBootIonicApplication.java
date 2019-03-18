package com.edercatini.spring;

import com.edercatini.spring.domain.*;
import com.edercatini.spring.repository.*;
import com.edercatini.spring.service.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.text.SimpleDateFormat;

import static com.edercatini.spring.enums.CustomerTypes.PHYSICAL_PERSON;
import static com.edercatini.spring.enums.PaymentStatus.PAID;
import static com.edercatini.spring.enums.PaymentStatus.PENDING;
import static java.util.Arrays.asList;

@SpringBootApplication
@EnableSwagger2
public class SpringBootIonicApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootIonicApplication.class, args);
	}
}