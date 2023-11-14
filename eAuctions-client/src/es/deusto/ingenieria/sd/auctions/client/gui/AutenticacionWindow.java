package es.deusto.ingenieria.sd.auctions.client.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
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

public class AutenticacionWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private static AutenticacionWindow instance;

	JTextField tfEmail;
	JPasswordField tfPassword;
	JButton bRegister;
	JButton bLogin;

	private AutenticacionWindow() {
		Container cp = this.getContentPane();
		cp.setLayout(new BorderLayout());

		// LOGO
		ImageIcon iiLogo = new ImageIcon("src\\es\\deusto\\ingenieria\\sd\\auctions\\client\\gui\\assets\\logo.png");
		JLabel lLogo = new JLabel(iiLogo);
		cp.add(lLogo, BorderLayout.NORTH);

		// FORMULARIO
		JPanel pData = new JPanel();
		pData.setLayout(new GridLayout(5, 1, 5, 10));
		pData.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));
		
		JPanel pButton = new JPanel();
		pButton.setLayout(new GridLayout(1, 2, 5, 10));

		pData.add(new JLabel("Email: "));
		tfEmail = new JTextField();
		pData.add(tfEmail);
		
		pData.add(new JLabel("Password: "));
		tfPassword = new JPasswordField();
		pData.add(tfPassword);
		
		bRegister = new JButton("Register");
		pButton.add(bRegister);
		bLogin = new JButton("Login");
		pButton.add(bLogin);
		pData.add(pButton);
		
		cp.add(pData);

		bRegister.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String nombre = "test";
				String email = "test@gmail.com";
				Date fechaNacimiento = new Date();
				float peso = 48.5f;
				float altura = 160.2f;
				int frecuenciaCardiacaMax = 5;
				int frecuenciaCardiacaReposo = 12;
				String contrasena = "test123";
				
				boolean r = register(nombre, email, fechaNacimiento, peso, altura, frecuenciaCardiacaMax, frecuenciaCardiacaReposo, contrasena);
				
				if (r) {
					System.out.println("te has registrado correctamente");
				} else {
					System.out.println("error register");
				}
			}
		});

		bLogin.addActionListener(new ActionListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {
				String email = tfEmail.getText();
				String password = tfPassword.getText();

				boolean login = login(email, password);

				if (login) {
					// stravaWindow = new StravaWindow();
					System.out.println("hola te has logueado");
				} else {
					System.out.println("datos incorrectos");
				}

			}

		});

		this.setTitle("Strava Autenticacion");
		this.pack();
		this.setLocationRelativeTo(null); // para centrar la ventana al ejecutarla
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
	}

	public boolean login(String email, String password) {
		String sha1 = org.apache.commons.codec.digest.DigestUtils.sha1Hex(password);

		return AutenticacionController.getInstance().login(email, sha1);
	}

	public boolean logout() {
		return true;
	}
	
	public boolean register(String nombre, String email, Date fechaNacimiento, float peso, float altura, int frecuenciaCardiacaMax, int frecuenciaCardiacaReposo, String contrasena) {
		String sha1 = org.apache.commons.codec.digest.DigestUtils.sha1Hex(contrasena);
		
		return AutenticacionController.getInstance().register(nombre, email, fechaNacimiento, peso, altura, frecuenciaCardiacaMax, frecuenciaCardiacaReposo, sha1);
	}
	
	public static AutenticacionWindow getInstance() {
		if (instance == null) {
			instance = new AutenticacionWindow();
		}
		
		return instance;
	}
	
}