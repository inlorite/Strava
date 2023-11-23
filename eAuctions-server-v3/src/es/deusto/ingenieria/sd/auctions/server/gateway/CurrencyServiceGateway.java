package es.deusto.ingenieria.sd.auctions.server.gateway;

import java.rmi.Naming;

import es.deusto.ingenieria.sd.auctions.currency.remote.ICurrencyExchange;

public class CurrencyServiceGateway {

	private static CurrencyServiceGateway instance;
	private ICurrencyExchange currencyConvService;
	
	private CurrencyServiceGateway() {
		try {		
			String URL = "//127.0.0.1:1099/CurrencyExchange";
			this.currencyConvService = (ICurrencyExchange) Naming.lookup(URL);
		} catch (Exception ex) {
			System.err.println("# Error locating remote fa�ade: " + ex);
		}
	}
	
	public static CurrencyServiceGateway getInstance() {
		if(instance == null) {
			instance = new CurrencyServiceGateway();
		}
		
		return instance;
	}
	
	public float getUSDRate() {
		System.out.println("   - Get USD rate from Currency Service Gateway");
		
		try {
			return this.currencyConvService.getUSDRate();
		} catch (Exception ex) {
			System.out.println("   $ Error getting USD rate: " + ex.getMessage());
			return -1f;
		}		
	}

	public float getGBPRate() {
		System.out.println("   - Get GBP rate from Currency Service Gateway");
		
		try {
			return this.currencyConvService.getGBPRate();
		} catch (Exception ex) {
			System.out.println("   $ Error getting GBP rate: " + ex.getMessage());
			return -1f;
		}
	}

}