package es.deusto.ingenieria.sd.strava.server.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import es.deusto.ingenieria.sd.strava.server.data.domain.SesionEntrenamiento;
import es.deusto.ingenieria.sd.strava.server.data.domain.Usuario;

//TODO: Implement Singleton Pattern
public class AutenticacionAppService {

	private static AutenticacionAppService instance;
	private List<Usuario> usuarios = new ArrayList<Usuario>();

	public List<Usuario> getUsuarios() {
		return usuarios;
	}
	
	private AutenticacionAppService() {
		//TODO: remove when DAO Pattern is implemented
		//this.initializeData();
	}

	private void initializeData() {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		
		//Inicializar usuario 1
		Usuario u1 = new Usuario();
		u1.setAltura(1.7f);
		u1.setContrasena(org.apache.commons.codec.digest.DigestUtils.sha1Hex("12345"));
		u1.setEmail("inigo@gmail.com");
		
		try {
			String myDate = "2001/01/29";
			Date date;
			date = sdf.parse(myDate);
			u1.setFechaNacimiento(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		u1.setFrecuenciaCardiacaMax(120);
		u1.setFrecuenciaCardiacaReposo(60);
		u1.setNombre("Inigo");
		u1.setPeso(80);
		u1.setSesionesEntrenamiento(new ArrayList<SesionEntrenamiento>());
		this.register(u1);
		
		//Inicializar usuario 2
		Usuario u2 = new Usuario();
		u2.setAltura(1.8f);
		u2.setContrasena("12345");
		u2.setEmail("adrian@gmail.com");
		
		try {
			String myDate = "2001/03/14";
			Date date;
			date = sdf.parse(myDate);
			u2.setFechaNacimiento(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		u2.setFrecuenciaCardiacaMax(110);
		u2.setFrecuenciaCardiacaReposo(50);
		u2.setNombre("Adrian");
		u2.setPeso(60);
		u2.setSesionesEntrenamiento(new ArrayList<SesionEntrenamiento>());
		
		this.register(u2);
		
		
		//Inicializar usuario 3
		Usuario u3 = new Usuario();
		u3.setAltura(1.75f);
		u3.setContrasena("12345");
		u3.setEmail("yeray@gmail.com");
		
		try {
			String myDate = "2001/02/21";
			Date date;
			date = sdf.parse(myDate);
			u3.setFechaNacimiento(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		u3.setFrecuenciaCardiacaMax(105);
		u3.setFrecuenciaCardiacaReposo(70);
		u3.setNombre("Yeray");
		u3.setPeso(65);
		u3.setSesionesEntrenamiento(new ArrayList<SesionEntrenamiento>());
		
		this.register(u3);
	}
	
	public Usuario getUsuario(String usuario) {
		for (Usuario u : usuarios) {
			if (u.getNombre().equals(usuario)) {
				return u;
			}
		}
		return null;
	}

	public Usuario login(String email, String password) {
		for (Usuario u : usuarios) {
			if (u.getEmail().equals(email)) {
				Boolean result = false;
				
				switch (u.getTipoServicio()) {
				case "FACEBOOK":
					result = conexionSocket(email, password, "LOGIN");
					break;
				case "GOOGLE":
					break;
				default:
					break;
				}
				
				if (result) {
					return u;
				} else {
					return null;
				}
			}
		}
		return null;
	}

	public boolean register(Usuario usuario) {
		if (!usuarios.contains(usuario)) {			
			Boolean result = false;
			
			switch (usuario.getTipoServicio()) {
			case "FACEBOOK":
				result = conexionSocket(usuario.getEmail(), usuario.getContrasena(), "REGISTER");
				break;
			case "GOOGLE":
				break;
			default:
				break;
			}
			
			System.out.println(usuario);
			if (result) {
				usuarios.add(usuario);
				return true;
			}
		}
		return false;
	}
	
	public String enviarMensaje(BufferedReader reader, PrintWriter writer, String mensaje) {		
		String respuesta = null;
		
		try {
			writer.println(mensaje);
			
			while ((respuesta = reader.readLine()) != null) {
				System.out.println(respuesta);
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return respuesta;
	}
	
	public boolean conexionSocket(String email, String password, String operacion) {
		boolean result = false;
		
		try {
			Socket socket = new Socket("localhost", 4321);
			
			InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream), true);
            
            String respuesta = null;
            if (enviarMensaje(reader, writer, operacion).equals("OK")) {
            	if (enviarMensaje(reader, writer, email).equals("OK")) {
            		if (enviarMensaje(reader, writer, password).equals("OK")) {
            			while ((respuesta = reader.readLine()) != null) {
							result = Boolean.parseBoolean(respuesta);
							break;
						}
                    }
                }
            }
            
            reader.close();
            writer.close();
            socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public static AutenticacionAppService getInstance() {
		if (instance == null) {
			instance = new AutenticacionAppService();
		}

		return instance;
	}
}