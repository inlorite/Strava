package es.deusto.ingenieria.sd.auctions.currency.server;

import java.rmi.Naming;

import es.deusto.ingenieria.sd.auctions.currency.remote.CurrencyExchangeService;
import es.deusto.ingenieria.sd.auctions.currency.remote.ICurrencyExchange;

public class CurrecyExchangeServer {

	@SuppressWarnings({ "removal", "deprecation" })
	public static void main(String[] args) {
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}

		String name = "//" + args[0] + ":" + args[1] + "/" + args[2];		
		
		try {
			ICurrencyExchange remoteObject = CurrencyExchangeService.getInstance();			
			Naming.rebind(name, remoteObject);
			System.out.println(" * Currency Exchange Server '" + name + "' started!!");
		} catch (Exception ex) {
			System.out.println(" # Currency Exchange Server: " + ex.getMessage());
			ex.printStackTrace();
		}

	}
}