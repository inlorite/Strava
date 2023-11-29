package es.deusto.ingenieria.sd.springboot.server.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import es.deusto.ingenieria.sd.springboot.server.model.User;
import es.deusto.ingenieria.sd.springboot.server.service.UserService;

@RestController
public class UserServiceController {
	private static final Logger log= LoggerFactory.getLogger(UserServiceController.class);	

    private UserService userService;
    
    public UserServiceController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/all")
    public List<User> getUsers() {
    	log.warn("Getting all users ...");
        return userService.getAllUsers();
    }

    @GetMapping("/user/details/{id}")
    public User getUser(@PathVariable Long id) {
    	log.warn(String.format("Getting user by id {%d} ...", id));
        return userService.getUserById(id);
    }
    
    @GetMapping("/user/email/{email}")
    public User getUserByEmail(@PathVariable String email) {
    	log.warn(String.format("Getting user by email {%s} ...", email));
        return userService.getUserByEmail(email);
    }
    
	@PostMapping("/user/register")
    public ResponseEntity<Object> registerUser(@RequestBody User user) {
    	log.warn("Creating a new User ...");
    	
       	switch (userService.registerUser(user)) {
       		case FAIL:
       			return ResponseEntity.unprocessableEntity().body(String.format("User with email {%s} creation fails.", user.getEmail()));
    	    default:
      	    	return ResponseEntity.ok(String.format("A new User (%s) has been created.", user.getEmail()));
    	}
    }

	@PostMapping("/user/login")
    public ResponseEntity<Object> loginUser(@RequestBody User user) {
    	log.warn("User logging in ...");
    	
       	switch (userService.loginUser(user)) {
       		case FAIL:
       			return ResponseEntity.unprocessableEntity().body(String.format("User with email {%s} creation fails.", user.getEmail()));
    	    default:
      	    	return ResponseEntity.ok(String.format("The user (%s) has logged in.", user.getEmail()));
    	}
    }

    @PutMapping("/user/update/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable Long id, @RequestBody User user) {
    	log.warn(String.format("Updating user with id {%d} ...", id));
    	
    	switch (userService.updateUser(user, id)) {
   			case FAIL:
   				return ResponseEntity.unprocessableEntity().body(String.format("User with email {%s} cannot be updated", user.getEmail()));
   			default:
   				return ResponseEntity.ok(String.format("User with email {%s} has been updated.", user.getEmail()));
    	}
    }
   
    @DeleteMapping("user/delete/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long id) {
    	log.warn(String.format("Deleting user with id {%d} ...", id));
    	
    	switch (userService.deleteUser(id)) {    	
			case FAIL:
   				return ResponseEntity.unprocessableEntity().body(String.format("User with id {%s} cannot be deleted", id.toString()));
   			default:
   				return ResponseEntity.ok(String.format("User with email {%s} has been deleted.", id.toString()));
    	}
    }
    
    @DeleteMapping("/user/delete/all")
    public ResponseEntity<Object> deleteUsers() {
    	log.warn("Deleting ALL users ...");
    	
    	switch (userService.deleteAllUsers()) {
			case FAIL:
   				return ResponseEntity.unprocessableEntity().body("Deletion of all users fails");
   			default:
   				return ResponseEntity.ok("All users has been deleted.");
    	}   
    }
}