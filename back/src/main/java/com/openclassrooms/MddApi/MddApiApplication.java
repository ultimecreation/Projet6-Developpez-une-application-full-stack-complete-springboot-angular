package com.openclassrooms.MddApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class MddApiApplication {

	public static void main(String[] args) {
		Dotenv.configure()
				.load();

		Dotenv.configure()
				.systemProperties()
				.load();
		SpringApplication.run(MddApiApplication.class, args);
	}

}
