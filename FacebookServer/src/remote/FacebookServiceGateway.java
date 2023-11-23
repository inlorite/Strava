package remote;

import java.util.HashMap;
import java.util.Map;

public class FacebookServiceGateway implements IFacebookServiceGateway {

	private static FacebookServiceGateway instance;
	private Map<String, String> usuarios = new HashMap<>();

	private FacebookServiceGateway() {
	}
	
	public Map<String, String> getUsuarios() {
		return usuarios;
	}

	@Override
	public boolean login(String email, String password) {
		for (String usuario : this.usuarios.keySet()) {
			if (usuario.equals(email)) {
				if (this.usuarios.get(email).equals(password)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean register(String email, String password) {
		if (!this.usuarios.keySet().contains(email)) {
			this.usuarios.put(email, password);
			return true;
		}
		return false;
	}

	public static FacebookServiceGateway getInstance() {
		if (instance == null) {
			instance = new FacebookServiceGateway();
		}
		return instance;
	}

}
