package com.example.angelneria_final_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class AngelneriaFinalProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(AngelneriaFinalProjectApplication.class, args);
	}

}
