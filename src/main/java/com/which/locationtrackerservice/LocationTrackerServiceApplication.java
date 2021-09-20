package com.which.locationtrackerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableConfigurationProperties
@ComponentScan("com.which")
public class LocationTrackerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LocationTrackerServiceApplication.class, args);
	}

}
