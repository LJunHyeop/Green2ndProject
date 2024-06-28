package com.green.fefu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class FefuApplication {

	public static void main(String[] args) {
		SpringApplication.run(FefuApplication.class, args);
	}

}
