package es.deusto.ingenieria.sd.springboot.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import es.deusto.ingenieria.sd.springboot.server.dao.UserRepository;
import es.deusto.ingenieria.sd.springboot.server.model.User;

@SpringBootApplication
public class ServerApplication {

	private static final Logger log = LoggerFactory.getLogger(ServerApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}

	@Bean
	CommandLineRunner demo(UserRepository repository) {
		return (args) -> {
			//Create some users
			repository.save(new User("xabi@gmail.com", "xabi"));
						
			log.warn("USER Service available and waiting ...");
		};
	}
}