package es.deusto.ingenieria.sd.strava.client.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;
import java.util.logging.Level;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JDateChooser;

import es.deusto.ingenieria.sd.strava.client.controller.AutenticacionController;

public class AutenticacionWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private static AutenticacionWindow instance;

	JTextField tfEmail;
	JPasswordField tfPassword;
	JButton bRegister;
	JButton bLogin;
	JButton bLoginG;
	JButton bLoginM;

	private AutenticacionWindow() {
		Container cp = this.getContentPane();
		cp.setLayout(new BorderLayout());

		// LOGO
		ImageIcon iiLogo = new ImageIcon("src\\es\\deusto\\ingenieria\\sd\\strava\\client\\gui\\assets\\logo.jpg");
		JLabel lLogo = new JLabel(iiLogo);
		cp.add(lLogo, BorderLayout.NORTH);

		// FORMULARIO
		JPanel pData = new JPanel();
		pData.setLayout(new GridLayout(7, 1, 5, 10));
		pData.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));
		
		JPanel pButton = new JPanel();
		pButton.setLayout(new GridLayout(1, 2, 5, 10));

		pData.add(new JLabel("Email: "));
		tfEmail = new JTextField();
		pData.add(tfEmail);
		
		pData.add(new JLabel("Password: "));
		tfPassword = new JPasswordField();
		pData.add(tfPassword);
		
		
		bLoginG = new JButton("Login Meta");
		bLoginM = new JButton("Login Google");
		pData.add(bLoginG);
		pData.add(bLoginM);
		
		bRegister = new JButton("Register");
		pButton.add(bRegister);
		bLogin = new JButton("Login");
		pButton.add(bLogin);

		pData.add(pButton);
		
		cp.add(pData);

		bRegister.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JTextField tfNombre = new JTextField();
				JTextField tfEmail = new JTextField();
				JDateChooser dcNacimiento = new JDateChooser("yyyy/MM/dd", "####/##/##", '_');
				JTextField tfPeso = new JTextField();
				JTextField tfAltura = new JTextField();
				JTextField tfFrecCardiMax = new JTextField();
				JTextField tfFrecCardiRep = new JTextField();
				JPasswordField pfContrasena = new JPasswordField();
				String[] options = {"Meta","Google"};
				JComboBox<String> cbTipo = new JComboBox<String>(options);
				
				Object[] fields = {
						"Nombre", tfNombre,
						"Email", tfEmail,
						"Fecha nacimiento", dcNacimiento,
						"Peso", tfPeso,
						"Altura", tfAltura,
						"Frecuencia cardiaca maxima", tfFrecCardiMax,
						"Frecuencia cardiaca reposo", tfFrecCardiRep,
						"Contrasena", pfContrasena,
						"Servicio", cbTipo
				};
				
				int result = JOptionPane.showConfirmDialog(null, fields, "Registro", JOptionPane.OK_CANCEL_OPTION);
				
				if (result == JOptionPane.OK_OPTION) {
					try {
						String nombre = tfNombre.getText();
						String email = tfEmail.getText();
						Date fechaNacimiento = dcNacimiento.getDate();
						float peso = Float.parseFloat(tfPeso.getText());
						float altura = Float.parseFloat(tfAltura.getText());
						int frecuenciaCardiacaMax = Integer.parseInt(tfFrecCardiMax.getText());
						int frecuenciaCardiacaReposo = Integer.parseInt(tfFrecCardiRep.getText());
						@SuppressWarnings("deprecation")
						String contrasena = pfContrasena.getText();
						String tipo = cbTipo.getSelectedItem().toString();
						
						/*
						String nombre = "test";
						String email = "test@gmail.com";
						Date fechaNacimiento = new Date(100);
						float peso = 12f;
						float altura = 12f;
						int frecuenciaCardiacaMax = 12;
						int frecuenciaCardiacaReposo = 12;
						String contrasena = "test";
						*/
						
						boolean register = register(nombre, email, fechaNacimiento, peso, altura, frecuenciaCardiacaMax, frecuenciaCardiacaReposo, contrasena, tipo);
						
						if (register) {
							System.out.println("te has registrado");
							AutenticacionWindow.getInstance().setVisible(false);
							StravaWindow.getInstance().setVisible(true);
						} else {
							System.out.println("registro incorrecto");
						}
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				} else {
					System.out.println("Cancelar");
				}
			}
		});

//		bLogin.addActionListener(new ActionListener() {
//
//			@SuppressWarnings("deprecation")
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				String email = tfEmail.getText();
//				String password = tfPassword.getText();
//
//				boolean login = login(email, password);
//
//				if (login) {
//					System.out.println("hola te has logueado");
//					AutenticacionWindow.getInstance().setVisible(false);
//					StravaWindow.getInstance().setVisible(true);
//				} else {
//					System.out.println("datos incorrectos");
//				}
//
//			}
//
//		});
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				AutenticacionController.getInstance().logout();
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
	
	public boolean register(String nombre, String email, Date fechaNacimiento, float peso, float altura, int frecuenciaCardiacaMax, int frecuenciaCardiacaReposo, String contrasena, String tipo) {
		String sha1 = org.apache.commons.codec.digest.DigestUtils.sha1Hex(contrasena);
		
		return AutenticacionController.getInstance().register(nombre, email, fechaNacimiento, peso, altura, frecuenciaCardiacaMax, frecuenciaCardiacaReposo, sha1, tipo);
	}
	
	public static AutenticacionWindow getInstance() {
		if (instance == null) {
			instance = new AutenticacionWindow();
		}
		
		return instance;
	}
	
}