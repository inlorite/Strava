package es.deusto.ingenieria.sd.auctions.server.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import es.deusto.ingenieria.sd.auctions.server.data.dto.RetoDTO;
import es.deusto.ingenieria.sd.auctions.server.data.dto.SesionEntrenamientoDTO;
import es.deusto.ingenieria.sd.auctions.server.data.dto.UsuarioRegisterDTO;

//This interface defines the API of the Server. It represents the Remote Facade pattern
public interface IRemoteFacade extends Remote {

	// Métodos Autenticación

	public long login(String email, String password) throws RemoteException;

	public void logout(long token) throws RemoteException;

	public long register(UsuarioRegisterDTO usuarioRegisterDTO) throws RemoteException;

	// Métodos SesionEntrenamiento

	public void crearSesionEntrenamiento(SesionEntrenamientoDTO sesionEntrenamientoDTO, long token) throws RemoteException;

	public List<SesionEntrenamientoDTO> getSesionesEntrenamiento(long token) throws RemoteException;
	
	public List<SesionEntrenamientoDTO> getSesionesEntrenamiento() throws RemoteException;

	public void eliminarSesionEntrenamiento(SesionEntrenamientoDTO sesionEntrenamientoDTO, long token) throws RemoteException;

	// Métodos Reto

	public void crearReto(RetoDTO retoDTO, long token) throws RemoteException;
	
	public List<RetoDTO> getRetos(long token) throws RemoteException;

	public List<RetoDTO> getRetos() throws RemoteException;

	public void apuntarseReto(RetoDTO retoDTO, long token) throws RemoteException;

	public void desapuntarseReto(RetoDTO retoDTO, long token) throws RemoteException;

	public void eliminarReto(RetoDTO retoDTO, long token) throws RemoteException;
}