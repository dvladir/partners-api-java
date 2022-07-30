package com.dvladir.partners;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.dvladir.partners", "com.dvladir.common"})
public class PartnersApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PartnersApiApplication.class, args);
	}

}
