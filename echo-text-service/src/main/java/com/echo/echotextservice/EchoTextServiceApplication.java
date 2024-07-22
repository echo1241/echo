package com.echo.echotextservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class EchoTextServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EchoTextServiceApplication.class, args);
	}

}
