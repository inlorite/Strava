package es.deusto.ingenieria.sd.springboot.client.controller;

import java.util.Arrays;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ClientController {
	private static final Logger log = LoggerFactory.getLogger(ClientController.class);
	
	@Autowired
	private RestTemplate restTemplate;
	
	//Host and port are defined in application.properties
	@Value("${spring.server.url}")
	private String serverURL;
	@Value("${server.port}")
	private int serverPort;	

	public record User(long id, String firstName, String lastName, String email) {		
	};
	
	public String getAllUsers() {
		log.warn(String.format("Getting all users: %s:%d/user/all ...", serverURL, serverPort));

		User[] users = restTemplate.getForObject(String.format("%s:%d/user/all", serverURL, serverPort), 
												 User[].class);

		StringBuffer buffer = new StringBuffer();

		if (users != null && users.length > 0) {
			Arrays.asList(users).forEach(user -> buffer.append(" - " + user.toString() + "\n"));
		} else {
			buffer.append(" - No users found");
		}
		
		return buffer.toString();
	}

	public String getUserById() {
		log.warn(String.format("Getting user details by id(1): %s:%d/user/details/{id} ...", serverURL, serverPort));
		
		User user = restTemplate.getForObject(String.format("%s:%d/user/details/{id}", serverURL, serverPort), 
				User.class, Map.of("id", "1"));
				
		return " - " + user.toString();
	}

	public String getUserByEmail() {
		log.warn(String.format("Getting user details by email(steve.jobs@example.com): %s:%d/user/email/{email} ...", serverURL, serverPort));
		
		User user = restTemplate.getForObject(String.format("%s:%d/user/email/{email}", serverURL, serverPort), 
				User.class, Map.of("email", "steve.jobs@example.com"));
		
		return " - " + user.toString();
	}

	public String addUser() {
		log.warn(String.format("Creating new user: %s:%d/user/create ...", serverURL, serverPort));
		
		User user = new User(20l, "Marissa", "Mayer", "marissa.mayer@example.com");

		String response = restTemplate.postForObject(String.format("%s:%d/user/create", serverURL, serverPort), user, String.class);
		
		return " - " + response;
	}

	public String updateUser() {
		log.warn(String.format("Getting user details by id(3): %s:%d/user/details/{id} ...", serverURL, serverPort));
		
		User user = restTemplate.getForObject(String.format("%s:%d/user/details/{id}", serverURL, serverPort), 
				User.class, Map.of("id", "3"));

		log.warn(String.format("Updating a user id(3): %s:%d/user/update/{id} ...", serverURL, serverPort));
		
		User newUser = new User(user.id(), user.firstName(), user.lastName(), "alan.turing@gmail.com");

		restTemplate.put(String.format("%s:%d/user/update/{id}", serverURL, serverPort), newUser, Map.of("id", "1"));

		return String.format(" - Old : %s", user.toString()) + String.format("\n - New: %s", newUser.toString());
	}

	public String deleteUser() {
		log.warn(String.format("Deleting a user (id=3): %s:%d/user/delete/{id} ...", serverURL, serverPort));
		
		restTemplate.delete(String.format("%s:%d/user/delete/{id}", serverURL, serverPort), Map.of("id", "3"));

		return " - Delete user by Id has no return";
	}

	public String deleteUserAll() {
		log.warn(String.format("Deleting all users: %s:%d/user/delete/all ...", serverURL, serverPort));
		
		restTemplate.delete(String.format("%s:%d/user/delete/all", serverURL, serverPort)); 

		return " - Delete all users has no return";
	}
}