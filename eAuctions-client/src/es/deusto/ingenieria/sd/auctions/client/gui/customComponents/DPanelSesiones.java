package es.deusto.ingenieria.sd.auctions.client.gui.customComponents;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class DPanelSesiones extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JPanel pGetSesiones;
	private JButton bGetSesiones;
	
	private JPanel pGetSesionesUsuario;
	private JPanel pCrearSesion;
	
	public DPanelSesiones() {
		this.setLayout(new GridLayout(1, 3, 5, 5));
		
		pGetSesiones = new JPanel();
		pGetSesiones.setLayout(new GridLayout(2, 1, 5, 5));
		pGetSesiones.setBorder(new TitledBorder("Obtener sesiones"));
		
		
	}

}
