package com.tfm.Miembros;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MiembrosApplication {

	private static final Logger logger = LoggerFactory.getLogger(MiembrosApplication.class);

	public static void main(String[] args) {
		String profile = System.getenv("PROFILE");
		String dbUrl = System.getenv("DB_URL");
		String dbUser = System.getenv("DB_USER");
		String dbPassword = System.getenv("DB_PASSWORD");
		String eurekaUrl = System.getenv("EUREKA_URL");
		String hostname = System.getenv("HOSTNAME");
		String port = System.getenv("PORT");

		System.setProperty("spring.profiles.active", profile != null ? profile : "default");

		// Log the environment variables
		logger.info("PROFILE: {}", profile);
		logger.info("DB_URL: {}", dbUrl);
		logger.info("DB_USER: {}", dbUser);
		logger.info("DB_PASSWORD: {}", dbPassword);
		logger.info("EUREKA_URL: {}", eurekaUrl);
		logger.info("HOSTNAME: {}", hostname);
		logger.info("PORT: {}", port);

		// Espera 3 segundos antes de iniciar la aplicación
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			logger.error("Error durante la espera", e);
		}

		SpringApplication.run(MiembrosApplication.class, args);

		logger.info("Aplicación iniciada.");
	}
}
