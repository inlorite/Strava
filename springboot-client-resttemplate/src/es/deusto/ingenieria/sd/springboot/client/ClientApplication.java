package es.deusto.ingenieria.sd.springboot.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import es.deusto.ingenieria.sd.springboot.client.controller.ClientController;
import es.deusto.ingenieria.sd.springboot.client.gui.ClientGUI;

@SpringBootApplication
public class ClientApplication implements CommandLineRunner {
	private static final Logger log = LoggerFactory.getLogger(ClientApplication.class);

	@Autowired
	private ClientController clientController;
	
	@Bean
	RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	public static void main(String args[]) {
		//By default, Spring Boot applications do not have a GUI (headless=true).
		System.getProperties().setProperty("java.awt.headless", "false");		
		SpringApplication.run(ClientApplication.class, args);
	}

	public void run(String... args) throws Exception {
		log.warn("Starting client GUI");
		new ClientGUI(clientController);
	}
}