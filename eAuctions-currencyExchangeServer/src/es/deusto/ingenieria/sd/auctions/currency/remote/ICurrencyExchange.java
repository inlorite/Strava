package es.deusto.ingenieria.sd.auctions.currency.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ICurrencyExchange extends Remote {
	public float getUSDRate() throws RemoteException;
	public float getGBPRate() throws RemoteException;
}
