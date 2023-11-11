package es.deusto.ingenieria.sd.auctions.client.gui;

import java.awt.*;
import javax.swing.*;

import es.deusto.ingenieria.sd.auctions.client.controller.StravaController;
import es.deusto.ingenieria.sd.auctions.client.gui.customComponents.*;

//This clase simulates the GUI of the Strava use case
public class StravaWindow extends JFrame {

	private static final long serialVersionUID = 1L;

	public static final int GAP = 10;

	public static Container cp;
	public static JPanel mainPanel;
	public static CardLayout cl;
	
	public static DMenuBar menuBar;
	public static DPanelSesiones pSesiones;
	public static DPanelRetos pRetos;

	public static boolean networking = true;

	private StravaController controller;

	public StravaWindow(StravaController strava) {
		this.controller = strava;

		cp = this.getContentPane();
		cp.setLayout(new BorderLayout(GAP, GAP));

		this.setMinimumSize(new Dimension(1000, 800));

		mainPanel = new JPanel();
		cl = new CardLayout(GAP, GAP);
		mainPanel.setLayout(cl);
		
		menuBar = new DMenuBar();
		
		pSesiones = new DPanelSesiones();
		pRetos = new DPanelRetos();
		
		cp.add(menuBar, BorderLayout.NORTH);
		
		mainPanel.add(pSesiones, "SESIONES");
		mainPanel.add(pRetos, "RETOS");
		
		cl.show(mainPanel, "RETOS");
		
		cp.add(mainPanel, BorderLayout.CENTER);
//		cp.add(pBiblioteca, BorderLayout.CENTER);
//		cp.add(pTienda, BorderLayout.CENTER);
//		cp.add(pPerfil, BorderLayout.CENTER);
		
//		if (networking) {
//			vChat = new VChat();
//			client = new Client(VLogin.loggedUser, vChat.dlmChatbox);
//		}
			
		this.setTitle("Strava - Retos");
		this.setIconImage(new ImageIcon("data/icon.png").getImage());
		this.pack();
		this.setLocationRelativeTo(null); // para centrar la ventana al ejecutarla
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(true);
		this.setVisible(true);
		this.setSize(new Dimension(1000, 800));
	}
}