package remote;

public interface IFacebookServiceGateway {
	public boolean login(String email, String password);
	public boolean register(String email, String password);
}
