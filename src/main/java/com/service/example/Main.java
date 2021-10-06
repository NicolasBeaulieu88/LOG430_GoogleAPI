package com.service.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Main {

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
		SpringApplication.run(GoogleMapsService.class, args);
	}

	@GetMapping("/ping")
	public String hello() {
		return "Hello, I am alive!";
	}

}
