package es.deusto.ingenieria.sd.strava.client.gui.customComponents;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import es.deusto.ingenieria.sd.strava.client.gui.AutenticacionWindow;
import es.deusto.ingenieria.sd.strava.client.gui.StravaWindow;

public class DMenuBar extends JPanel {

	private static final long serialVersionUID = 1L;

	protected JButton bSesiones;
	protected JButton bRetos;

	public DMenuBar() {
		super();

		setup();
	}

	public void setup() {
		this.setLayout(new GridLayout(1, 2, StravaWindow.GAP, StravaWindow.GAP));
		this.setBorder(new EmptyBorder(StravaWindow.GAP, StravaWindow.GAP, 0, StravaWindow.GAP));

		bSesiones = new JButton("Sesiones");
		bRetos = new JButton("Retos");

		bRetos.setEnabled(false);

		bSesiones.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				bRetos.setEnabled(true);
				bSesiones.setEnabled(false);
				StravaWindow.cl.show(StravaWindow.mainPanel, "SESIONES");

				/*
				DPanelSesiones.loadDataModels();
				
				AutenticacionWindow.stravaWindow.setTitle("Strava - Sesiones");
				*/
			}
		});

		bRetos.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				bRetos.setEnabled(false);
				bSesiones.setEnabled(true);
				StravaWindow.cl.show(StravaWindow.mainPanel, "RETOS");
				/*
				DPanelRetos.lBanner.setIcon(null);
				DPanelRetos.lInfo.setText("");
				DPanelRetos.bComprar.setEnabled(false);

				DPanelRetos.loadTablaJuegos();
				
				AutenticacionWindow.stravaWindow.setTitle("Strava - Retos");
				*/
			}
		});

//		if (StravaWindow.networking) {
//			bChat.addActionListener(new ActionListener() {
//				
//				@Override
//				public void actionPerformed(ActionEvent e) {
//					StravaWindow.vChat.setVisible(true);
//				}
//			});
//		}

		this.add(bSesiones);
		this.add(bRetos);
	}

}
