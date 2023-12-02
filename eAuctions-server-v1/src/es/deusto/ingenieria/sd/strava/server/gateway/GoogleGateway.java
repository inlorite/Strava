package es.deusto.ingenieria.sd.strava.server.gateway;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class GoogleGateway implements IAuthGateway {

	private static GoogleGateway instance;
	
	@Autowired
	private RestTemplate restTemplate = new RestTemplate();
	private String serverURL = "http://localhost";
	private int serverPort = 8888;
	
	private GoogleGateway() {
	}
	
	public boolean conexionSpring(String email, String password, String operacion) {
		Boolean result = false;
		HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        List<MediaType> accepts = new ArrayList<>();
        accepts.add(MediaType.APPLICATION_JSON);
        headers.setAccept(accepts);
        
        String requestBody = String.format("{\"email\":\"%s\",\"contrasena\":\"%s\"}", email, password);
        
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        
        ResponseEntity<String> response = restTemplate.exchange(String.format("%s:%d/user/%s", serverURL, serverPort, operacion), HttpMethod.POST, requestEntity, String.class);
		System.out.println(response);
		
		if (response.getStatusCode().value() == 200) {
			result = true;
		}
		
		return result;
	}
	
	public static GoogleGateway getInstance() {
		if (instance == null) {
			instance = new GoogleGateway();
		}
		return instance;
	}

	@Override
	public boolean login(String email, String contrasena) {
		return conexionSpring(email, contrasena, "login");
	}

	@Override
	public boolean register(String email, String contrasena) {
		return conexionSpring(email, contrasena, "register");
	}
	
}
