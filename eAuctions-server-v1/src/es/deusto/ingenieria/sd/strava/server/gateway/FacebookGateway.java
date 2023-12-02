package es.deusto.ingenieria.sd.strava.server.gateway;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class FacebookGateway implements IAuthGateway {
	
	private static FacebookGateway instance;
	
	private FacebookGateway() {
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
	
	public static FacebookGateway getInstance() {
		if (instance == null) {
			instance = new FacebookGateway();
		}
		return instance;
	}

	@Override
	public boolean login(String email, String contrasena) {
		return conexionSocket(email, contrasena, "LOGIN");
	}

	@Override
	public boolean register(String email, String contrasena) {
		return conexionSocket(email, contrasena, "REGISTER");
	}

}
