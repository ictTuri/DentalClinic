package com.clinic.dental;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;


@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class DentalClinicApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(DentalClinicApplication.class, args);
	}
}
