package es.deusto.ingenieria.sd.auctions.currency.remote;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CurrencyExchangeService extends UnicastRemoteObject implements ICurrencyExchange {
	private static final long serialVersionUID = 1L;

	protected static final String URL = "https://api.freecurrencyapi.com/v1/latest?apikey=fca_live_DgSapgilaF9fv0FzhAj6VwxXETqUbsIFAKMrGj2s&base_currency=EUR&currencies=USD,GBP";
	public static float USD_RATE = 1.05f;
	public static float GBP_RATE = 0.86f;
	
	//Attribute for the Singleton pattern
	public static CurrencyExchangeService instance;
			
	private CurrencyExchangeService() throws RemoteException {
		super();
		getConversionRates();
	}
	
	public static CurrencyExchangeService getInstance() {
		if (instance == null) {
			try {
				instance = new CurrencyExchangeService();
			} catch(Exception ex) {
				System.err.println("  # Error initializing service(): " + ex.getMessage());
			}
		}
		
		return instance;
	}

	private static final void getConversionRates() {
		System.out.println(" - Getting exchange rates from 'https://api.freecurrencyapi.com'....");
				
		String gbpLabel = "GBP\":";
		String usdLabel = "USD\":";
		int startIndex = 0;
		int endIndex = 0;
		
		try {			
			HttpURLConnection con = (HttpURLConnection) (new URL(URL).openConnection());			
			con.setRequestProperty("User-Agent", "Mozilla/5.0");
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			
			con.disconnect();

			inputLine = response.toString();
			
			startIndex = inputLine.indexOf(usdLabel) + (usdLabel.length());
			endIndex = startIndex + 10;
			USD_RATE = Float.parseFloat(inputLine.substring(startIndex, endIndex));
			
			startIndex = inputLine.indexOf(gbpLabel) + (gbpLabel.length());
			endIndex = startIndex + 10;
			GBP_RATE = Float.parseFloat(inputLine.substring(startIndex, endIndex));			
		} catch(Exception ex) {
			System.err.println("  # Error getting conversion rates(): " + ex.getMessage());
		}
		
		SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/YYYY - HH:mm");
		
		
		System.out.println(" - Exchange rates obtained (" + dateFormatter.format(Calendar.getInstance().getTime()) + ")!!");
		System.out.println("\t- EURO to USD rate: " + USD_RATE);
		System.out.println("\t- EURO to GBP rate: " + GBP_RATE);
	}
	
	public float getUSDRate() throws RemoteException {
		getConversionRates();
		return USD_RATE;
	}

	public float getGBPRate() throws RemoteException {
		getConversionRates();
		return GBP_RATE;
	}
}