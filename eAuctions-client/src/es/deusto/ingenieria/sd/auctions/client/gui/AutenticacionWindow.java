package es.deusto.ingenieria.sd.auctions.client.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import es.deusto.ingenieria.sd.auctions.client.controller.AutenticacionController;


public class AutenticacionWindow extends JFrame{	

	private static final long serialVersionUID = 1L;
	private AutenticacionController controller;	
	private String email = "thomas.e2001@gmail.com";
	private String password = "$!9PhNz,";
	public static AutenticacionWindow autenticacionWindow;
	public static StravaWindow stravaWindow;
	
	JLabel lLogo;
	ImageIcon iiLogo;
	JPanel pData;
	JPanel pButton;
	JTextField tfUser;
	JPasswordField tfPassword;
	JButton bRegister;
	JButton bLogin;
	
	public AutenticacionWindow() {
		Container cp = this.getContentPane();
		cp.setLayout(new BorderLayout());
		
		// LOGO
		iiLogo = new ImageIcon("data/logo.png");
		lLogo = new JLabel(iiLogo);
		cp.add(lLogo, BorderLayout.NORTH);
		
		// FORMULARIO
		pData = new JPanel();
		pData.setLayout(new GridLayout(5, 1, 5, 10));
		pData.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));
		pData.setBackground(new Color(255, 255, 255));
		pButton = new JPanel();
		pButton.setLayout(new GridLayout(1, 2, 5, 10));
		
		pData.add(new JLabel("Email: "));
		tfUser = new JTextField();
		
		pData.add(tfUser);
		pData.add(new JLabel("Password: "));
		tfPassword = new JPasswordField();
		
		pData.add(tfPassword);
		bRegister = new JButton("Register");
		bLogin = new JButton("Login");
		pButton.add(bRegister);
		pButton.add(bLogin);
		pData.add(pButton);
		cp.add(pData);
		
		autenticacionWindow = this;
		
		bRegister.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		bLogin.addActionListener(new ActionListener() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {
				String email = tfUser.getText();
				String password = tfPassword.getText();
				
				boolean login = controller.login(email, password);
				
				if (login) {
					//stravaWindow = new StravaWindow();
				} else {
					System.out.println("datos incorrectos");
				}
				
			}
			
		});
	}

	public AutenticacionWindow(AutenticacionController controller) {
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