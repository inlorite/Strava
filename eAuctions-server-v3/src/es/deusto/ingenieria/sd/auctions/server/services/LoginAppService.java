package es.deusto.ingenieria.sd.auctions.server.services;

import es.deusto.ingenieria.sd.auctions.server.data.dao.UserDAO;
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
		User user = UserDAO.getInstance().find(email);
		
		if (user != null && user.checkPassword(password)) {
			return user;
		} else {
			return null;
		}
	}
}