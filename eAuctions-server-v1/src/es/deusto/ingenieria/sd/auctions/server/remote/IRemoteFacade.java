package es.deusto.ingenieria.sd.auctions.server.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import es.deusto.ingenieria.sd.auctions.server.data.dto.RetoDTO;
import es.deusto.ingenieria.sd.auctions.server.data.dto.SesionEntrenamientoDTO;

//This interface defines the API of the Server. It represents the Remote Facade pattern
public interface IRemoteFacade extends Remote {

	// Métodos Login

	public long login(String email, String password) throws RemoteException;

	public void logout(long token) throws RemoteException;

	public long register() throws RemoteException; // TODO

	// Métodos SesionEntrenamiento

	public void crearSesionEntrenamiento() throws RemoteException; // TODO

	public List<SesionEntrenamientoDTO> getSesionesEntrenamiento() throws RemoteException; // TODO

	public void eliminarSesionEntrenamiento() throws RemoteException; // TODO

	// Métodos Reto

	public void crearReto() throws RemoteException; // TODO

	public List<RetoDTO> getRetos() throws RemoteException; // TODO

	public void apuntarseReto() throws RemoteException; // TODO

	public void desapuntarseReto() throws RemoteException; // TODO

	public void eliminarReto() throws RemoteException; // TODO
}