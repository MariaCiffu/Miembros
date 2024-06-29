package com.tfm.Miembros;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MiembrosApplication {

	public static void main(String[] args) {

		String profile = System.getenv("PROFILE");
		System.setProperty("spring.profiles.active", profile != null ? profile : "default");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		SpringApplication.run(MiembrosApplication.class, args);
	}

}
