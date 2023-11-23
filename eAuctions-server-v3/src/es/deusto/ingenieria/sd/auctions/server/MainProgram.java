package es.deusto.ingenieria.sd.auctions.server;

import java.rmi.Naming;
import java.util.Calendar;

import es.deusto.ingenieria.sd.auctions.server.data.dao.ArticleDAO;
import es.deusto.ingenieria.sd.auctions.server.data.dao.CategoryDAO;
import es.deusto.ingenieria.sd.auctions.server.data.dao.UserDAO;
import es.deusto.ingenieria.sd.auctions.server.data.domain.Article;
import es.deusto.ingenieria.sd.auctions.server.data.domain.Category;
import es.deusto.ingenieria.sd.auctions.server.data.domain.User;
import es.deusto.ingenieria.sd.auctions.server.remote.IRemoteFacade;
import es.deusto.ingenieria.sd.auctions.server.remote.RemoteFacade;

public class MainProgram {

	@SuppressWarnings({ "removal", "deprecation" })
	public static void main(String[] args) {	
		//Activate Security Manager. It is needed for RMI.
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}

		//args[0] = RMIRegistry IP
		//args[1] = RMIRegistry Port
		//args[2] = Service Name
		String name = "//" + args[0] + ":" + args[1] + "/" + args[2];		
		
		//Initialize DB
		initDB();
		
		//Bind remote facade instance to a sirvice name using RMIRegistry
		try {
			IRemoteFacade remoteFacade = new RemoteFacade();			
			Naming.rebind(name, remoteFacade);
			System.out.println(" * eAuction server v3 '" + name + "' started!!");
		} catch (Exception ex) {
			System.err.println(" # eAuction Server Exception: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	public static void initDB() {
		try { 
			//Create Users
			User user0 = new User();
			user0.setEmail("thomas.e2001@gmail.com");
			user0.setNickname("Thomas");
			user0.setPassword("$!9PhNz,");			
							
			User user1 = new User();
			user1.setEmail("sample@gmail.com");
			user1.setNickname("buyer33");		
			user1.setPassword("hqc`}3Hb");
			
			User user2 = new User();
			user2.setEmail("troyaikman08@hotmail.com");
			user2.setNickname("troyaikman08");
			user2.setPassword("RR$dW69N");			
			
			//Store Users in the DB
			UserDAO.getInstance().store(user0);
			UserDAO.getInstance().store(user1);
			UserDAO.getInstance().store(user2);
			
			//Create Categories
			Category catIpad = new Category();
			catIpad.setName("iPad");
			Category catTablet = new Category();
			catTablet.setName("Tablets");
			Category catPhoto = new Category();
			catPhoto.setName("Photography");		
			Category catPhone = new Category();
			catPhone.setName("Cell Phones");
			
			//Store Categories in the DB
			CategoryDAO.getInstance().store(catIpad);
			CategoryDAO.getInstance().store(catTablet);
			CategoryDAO.getInstance().store(catPhoto);
			CategoryDAO.getInstance().store(catPhone);
			
			//Create Articles
			Article iPadCover = new Article();
			iPadCover.setNumber(1);
			iPadCover.setTitle("iPad Air Cover");		
			iPadCover.setInitialPrice(10.0f);
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_MONTH, 30);
			iPadCover.setAuctionEnd(calendar.getTime());
			
			catIpad.addArticle(iPadCover);
			iPadCover.setCategory(catIpad);
			user2.addArticle(iPadCover);
			iPadCover.setOwner(user2);
			
			Article iPadStylus = new Article();
			iPadStylus.setNumber(2);
			iPadStylus.setTitle("iPad Stylus");
			iPadStylus.setInitialPrice(15.50f);		
			calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_MONTH, 20);
			iPadStylus.setAuctionEnd(calendar.getTime());			
			
			catIpad.addArticle(iPadStylus);
			iPadStylus.setCategory(catIpad);
			user2.addArticle(iPadStylus);
			iPadStylus.setOwner(user2);
							
			Article fujiCam = new Article();
			fujiCam.setNumber(3);		
			fujiCam.setTitle("Fuji FinePix S4500 (White)");
			fujiCam.setInitialPrice(145.00f);
			calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_MONTH, 10);
			fujiCam.setAuctionEnd(calendar.getTime());

			catPhoto.addArticle(fujiCam);
			fujiCam.setCategory(catPhoto);
			user0.addArticle(fujiCam);
			fujiCam.setOwner(user0);
			
			Article canonCam = new Article();
			canonCam.setNumber(4);
			canonCam.setTitle("Canon PowerShot ELPH 310 HS (Purple)");						
			canonCam.setInitialPrice(99.99f);
			calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_MONTH, 5);
			canonCam.setAuctionEnd(calendar.getTime());
			
			catPhoto.addArticle(canonCam);
			canonCam.setCategory(catPhoto);
			user0.addArticle(canonCam);
			canonCam.setOwner(user0);

			Article sonyCam = new Article();
			sonyCam.setNumber(5);		
			sonyCam.setTitle("Sony Cyber-shot DSC-W650 16MP (Black)");
			sonyCam.setInitialPrice(64.99f);
			calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_MONTH, 3);
			sonyCam.setAuctionEnd(calendar.getTime());
			
			catPhoto.addArticle(sonyCam);
			sonyCam.setCategory(catPhoto);
			user0.addArticle(sonyCam);
			sonyCam.setOwner(user0);
									
			Article tablet = new Article();
			tablet.setNumber(6);		
			tablet.setTitle("10.1' Android Tablet 16GB");
			tablet.setInitialPrice(75.98f);
			calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_MONTH, 7);
			tablet.setAuctionEnd(calendar.getTime());			
			
			tablet.setCategory(catTablet);		
			catTablet.addArticle(tablet);			
			user1.addArticle(tablet);
			tablet.setOwner(user1);
			
			Article ipad = new Article();
			ipad.setNumber(7);
			ipad.setTitle("Apple iPad 8th gen 64GB + 4G");
			ipad.setInitialPrice(99.00f);
			calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_MONTH, 14);
			ipad.setAuctionEnd(calendar.getTime());			
					
			ipad.setCategory(catIpad);		
			catIpad.addArticle(ipad);
			user1.addArticle(ipad);
			ipad.setOwner(user1);			

			Article kindle = new Article();
			kindle.setNumber(8);
			kindle.setTitle("Amazon Kindle 128GB");
			kindle.setInitialPrice(55.50f);
			calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_MONTH, 28);
			kindle.setAuctionEnd(calendar.getTime());			
			
			catTablet.addArticle(kindle);
			kindle.setCategory(catTablet);
			user1.addArticle(kindle);
			kindle.setOwner(user1);
					
			Article galaxy = new Article();
			galaxy.setNumber(9);
			galaxy.setTitle("Samsung Galaxy S20 128GB");
			galaxy.setInitialPrice(149.99f);
			calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_MONTH, 60);
			galaxy.setAuctionEnd(calendar.getTime());
			
			catPhone.addArticle(galaxy);
			galaxy.setCategory(catPhone);
			user2.addArticle(galaxy);
			galaxy.setOwner(user2);
			
			Article iphone = new Article();
			iphone.setNumber(10);
			iphone.setTitle("Apple iPhone 12 64GB");
			iphone.setInitialPrice(216.00f);
			calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_MONTH, 30);
			iphone.setAuctionEnd(calendar.getTime());			
			
			catPhone.addArticle(iphone);
			iphone.setCategory(catPhone);
			user2.addArticle(iphone);
			iphone.setOwner(user2);
							
			Article xiaomi = new Article();
			xiaomi.setNumber(11);	
			xiaomi.setTitle("Xiaomi Mi 10");
			xiaomi.setInitialPrice(99.40f);
			calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_MONTH, 11);
			xiaomi.setAuctionEnd(calendar.getTime());			
					
			catPhone.addArticle(xiaomi);		
			xiaomi.setCategory(catPhone);
			user2.addArticle(xiaomi);
			xiaomi.setOwner(user2);
						
			//Store Articles in the DB
			ArticleDAO.getInstance().store(iPadCover);
			ArticleDAO.getInstance().store(iPadStylus);
			ArticleDAO.getInstance().store(fujiCam);
			ArticleDAO.getInstance().store(canonCam);
			ArticleDAO.getInstance().store(sonyCam);
			ArticleDAO.getInstance().store(tablet);
			ArticleDAO.getInstance().store(ipad);
			ArticleDAO.getInstance().store(kindle);
			ArticleDAO.getInstance().store(galaxy);
			ArticleDAO.getInstance().store(iphone);
			ArticleDAO.getInstance().store(xiaomi);						
		} catch (Exception ex) {
			System.out.println("\t$ Error storing data:" + ex.getMessage());
		}			
	}
}
