package es.deusto.ingenieria.sd.auctions.server.services;

import es.deusto.ingenieria.sd.auctions.server.data.domain.User;

public class LoginAppService {
	
	//Instance for the Singleton Pattern
	private static LoginAppService instance;
	
	private LoginAppService() { }
	
	public static LoginAppService getInstance() {
		if (instance == null) {
			instance = new LoginAppService();
		}
		
		return instance;
	}

	public User login(String email, String password) {
		//TODO: Get User using DAO and check 		
		User user = new User();		
		user.setEmail("thomas.e2001@gmail.com");
		user.setNickname("Thomas");		
		//Generate the hash of the password
		String sha1 = org.apache.commons.codec.digest.DigestUtils.sha1Hex("$!9PhNz,");		
		user.setPassword(sha1);
		
		if (user.getEmail().equals(email) && user.checkPassword(password)) {		
			return user;
		} else {
			return null;
		}
	}
}