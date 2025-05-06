package com.tfm.Miembros;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(
		properties = {
				"eureka.client.enabled=false",
				"spring.cloud.discovery.enabled=false"
		}
)
public class MiembrosApplicationTests {
	@Test
	public void contextLoads() {
		// Test vacío solo para verificar carga de contexto
	}
}