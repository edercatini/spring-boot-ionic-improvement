package com.edercatini.spring.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static java.util.Collections.emptyList;
import static springfox.documentation.builders.RequestHandlerSelectors.basePackage;
import static springfox.documentation.spi.DocumentationType.SWAGGER_2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(SWAGGER_2)
            .select()
            .apis(basePackage("com.edercatini.spring.controller"))
            .paths(PathSelectors.any())
            .build()
            .apiInfo(this.apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
            "API - Spring Boot",
            "API - Spring Boot Improvement Training",
            "Version 1.0",
            "https://www.udemy.com/terms",
            new Contact("Eder Catini", "https://github.com/edercatini", "edergcatini@icloud.com"),
            "Eder Catini - GitHub",
            "https://www.udemy.com/terms",
            emptyList()
        );
    }
}