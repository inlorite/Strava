package es.deusto.ingenieria.sd.auctions.client.gui;

import es.deusto.ingenieria.sd.auctions.client.controller.LoginController;

public class LoginDialog {	
	private LoginController controller;	
	private String email = "thomas.e2001@gmail.com";
	private String password = "$!9PhNz,";

	public LoginDialog(LoginController controller) {
		this.controller = controller;
	}
	
	public boolean login() {		
		System.out.println(" - Login into the server: '" + this.email + "' - '" + this.password + "' ...");
		String sha1 = org.apache.commons.codec.digest.DigestUtils.sha1Hex(password);
		System.out.println("\t* Password hash: " + sha1);		
		boolean result = this.controller.login(email, sha1);
		System.out.println("\t* Login result: " + result);
		System.out.println("\t* Token: " + this.controller.getToken());

		return result;
	}
	
	public void logout() {
		System.out.println(" - Logout from the server...");		
		this.controller.logout();
		System.out.println("\t* Logout success!");		

	}
}