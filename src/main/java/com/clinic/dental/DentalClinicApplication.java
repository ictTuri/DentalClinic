package com.clinic.dental;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class DentalClinicApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(DentalClinicApplication.class, args);
	}
}



//(exclude = {SecurityAutoConfiguration.class})