package es.deusto.ingenieria.sd.springboot.server.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import es.deusto.ingenieria.sd.springboot.server.dao.UserRepository;
import es.deusto.ingenieria.sd.springboot.server.model.User;

@Service
public class UserService {

	private UserRepository userRepository;
	
	public enum UserServiceResult {
		SUCCESS,
		FAIL;
	}

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public User getUserById(Long id) {
		Optional<User> result = userRepository.findById(id);
		
		return result.isEmpty() ? null : result.get();
	}

	public User getUserByEmail(String email) {
		Optional<User> result = userRepository.findByEmail(email);
		
		return result.isEmpty() ? null : result.get();
	}

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public UserServiceResult registerUser(User user) {
		Optional<User> result = userRepository.findByEmail(user.getEmail());
		
		if (result.isEmpty()) {
			User savedUser = userRepository.save(user);
			
			if (savedUser != null) {
				return UserServiceResult.SUCCESS;
			}
		}
		
		return UserServiceResult.FAIL;
	}

	public UserServiceResult loginUser(User user) {
		Optional<User> result = userRepository.findByEmail(user.getEmail());
		
		if (!result.isEmpty()) {
			if (user.getContrasena().equals(result.get().getContrasena())) {
				return UserServiceResult.SUCCESS;
			}
		}
		
		return UserServiceResult.FAIL;
	}

	public UserServiceResult updateUser(User user, Long id) {
		Optional<User> result = userRepository.findById(user.getId());
		
		if (!result.isEmpty()) {
			User updatedUser = result.get();
			
			updatedUser.setEmail(user.getEmail());
			updatedUser.setContrasena(user.getContrasena());

			userRepository.save(updatedUser);
			
			if (!userRepository.findById(id).isEmpty()) {
				return UserServiceResult.SUCCESS;
			}
		}
		
		return UserServiceResult.FAIL;
	}

	public UserServiceResult deleteUser(Long id) {
		Optional<User> result = userRepository.findById(id);
		
		if (!result.isEmpty()) {
			userRepository.delete(result.get());

			if (userRepository.findById(id).isEmpty()) {
				return UserServiceResult.SUCCESS;
			}
		}
		
		return UserServiceResult.FAIL;
	}

	public UserServiceResult deleteAllUsers() {
		UserServiceResult result = UserServiceResult.SUCCESS;
		
		for (User u : userRepository.findAll()) {
			userRepository.deleteById(u.getId());

			if (!userRepository.findById(u.getId()).isEmpty()) {
				result = UserServiceResult.FAIL;
			}
		}

		return result;
	}
}