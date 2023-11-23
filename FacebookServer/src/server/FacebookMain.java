package server;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import remote.FacebookServiceGateway;

public class FacebookMain {
	
	private static final int PORT = 4321;
	private ServerSocket serverSocket;
	
	public FacebookMain() {
		try {
			this.serverSocket = new ServerSocket(PORT);
			startServer();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void startServer() {
		while (!serverSocket.isClosed()) {
			try {
				System.out.println("Waiting for connection...");
				Socket socket = serverSocket.accept(); // the program is halted here until someone (a new client) joins
				System.out.println("New connection accepted.");
				
	            InputStream inputStream = socket.getInputStream();
	            OutputStream outputStream = socket.getOutputStream();

	            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
	            PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream), true);

				String userEmail = reader.readLine();
				String userPassword = reader.readLine();
				
				writer.println(FacebookServiceGateway.getInstance().login(userEmail, userPassword)+"");
				
			} catch (IOException e) {
				e.printStackTrace();
				closeServer();
			}
		}
	}
	
	public void closeServer() {
		try {
			if (serverSocket != null) {
				serverSocket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new FacebookMain();
	}
	
}