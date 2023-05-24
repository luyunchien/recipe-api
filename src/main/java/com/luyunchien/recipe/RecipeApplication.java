package com.luyunchien.recipe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;

// Required for 404 handling
// https://github.com/zalando/problem-spring-web/tree/main/problem-spring-web#configuration
@SpringBootApplication(exclude = ErrorMvcAutoConfiguration.class)
public class RecipeApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecipeApplication.class, args);
	}

}
