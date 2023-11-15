package es.deusto.ingenieria.sd.auctions.client.gui.customComponents;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import com.toedter.calendar.JDateChooser;

import es.deusto.ingenieria.sd.auctions.client.controller.AutenticacionController;
import es.deusto.ingenieria.sd.auctions.client.gui.AutenticacionWindow;
import es.deusto.ingenieria.sd.auctions.client.gui.StravaWindow;
import es.deusto.ingenieria.sd.auctions.server.data.dto.SesionEntrenamientoDTO;

public class DPanelSesiones extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JPanel pGetSesiones;
	private JButton bGetSesiones;
	private JList<String> lSesiones;
	private DefaultListModel<String> modelo;
	
	private JPanel pGetSesionesUsuario;
	private JButton bGetSesionesUsuario;
	private JList<SesionEntrenamientoDTO> lSesionesUsuario;
	private DefaultListModel<SesionEntrenamientoDTO> modeloUsuario;
	
	private JPanel pCrearSesion;
	private JButton bCrearSesion;
	
	
	public DPanelSesiones() {
		
		this.setLayout(new GridLayout(1, 3, 5, 5));
		
		pGetSesiones = new JPanel();
		pGetSesiones.setLayout(new GridLayout(2, 1, 5, 5));
		pGetSesiones.setBorder(new TitledBorder("Obtener sesiones"));
		lSesiones = new JList<String>();
		modelo = new DefaultListModel<String>();
		lSesiones.setModel(modelo);
		bGetSesiones = new JButton("Obtener Sesiones");
		pGetSesiones.add(lSesiones);
		pGetSesiones.add(bGetSesiones);
		this.add(pGetSesiones);
		
		pGetSesionesUsuario = new JPanel();
		pGetSesionesUsuario.setLayout(new GridLayout(2, 1, 5, 5));
		pGetSesionesUsuario.setBorder(new TitledBorder("Obtener sesiones usuario"));
		lSesionesUsuario = new JList<SesionEntrenamientoDTO>();	
		modeloUsuario = new DefaultListModel<SesionEntrenamientoDTO>();
		lSesionesUsuario.setModel(modeloUsuario);
		bGetSesionesUsuario = new JButton("Obtener mis Sesiones");
		pGetSesionesUsuario.add(lSesionesUsuario);
		pGetSesionesUsuario.add(bGetSesionesUsuario);
		this.add(pGetSesionesUsuario);
		
		pCrearSesion = new JPanel();
		pCrearSesion.setLayout(new GridLayout(1, 1, 5, 5));
		pCrearSesion.setBorder(new TitledBorder("Crear Sesion"));
		bCrearSesion = new JButton("Crear sesion");
		pCrearSesion.add(bCrearSesion);
		this.add(pCrearSesion);
		
		
		
		bGetSesiones.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				List<SesionEntrenamientoDTO> lista = new ArrayList<>();
				lista = StravaWindow.getInstance().getSesionesEntrenamiento();
				for (SesionEntrenamientoDTO sesionEntrenamientoDTO : lista) {
					System.out.println("Hola "+sesionEntrenamientoDTO.getTitulo());
					modelo.add(UNDEFINED_CONDITION, sesionEntrenamientoDTO.getTitulo());
				}
				lSesiones.setModel(modelo);
				pGetSesiones.updateUI();				
				
			}
		});
		
		bCrearSesion.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				JTextField tfTitulo = new JTextField();
				JTextField tfDistancia = new JTextField();
				JDateChooser dcFechaInicio = new JDateChooser("yyyy/MM/dd", "####/##/##", '_');
				JTextField tfHoraInicio = new JTextField();
				JTextField tfDuracion = new JTextField();
				
				Object[] fields = {
						"Titulo", tfTitulo,
						"Distancia", tfDistancia,
						"Fecha inicio", dcFechaInicio,
						"Hora inicio", tfHoraInicio,
						"Duracion", tfDuracion
				};
				
				int result = JOptionPane.showConfirmDialog(null, fields, "CrearSesion", JOptionPane.OK_CANCEL_OPTION);
				
				if (result == JOptionPane.OK_OPTION) {
					try {
						String titulo = tfTitulo.getText();
						float distancia = Float.parseFloat(tfDistancia.getText());
						Date fechaInicio = dcFechaInicio.getDate();
						long horaInicio = Long.parseLong(tfHoraInicio.getText());
						float duracion = Float.parseFloat(tfDuracion.getText());
						
						boolean resultado  = StravaWindow.getInstance().crearSesionEntrenamiento(AutenticacionController.getToken(),titulo, distancia, fechaInicio, horaInicio,  duracion);
						
						if (resultado) {
							System.out.println("sesion creada");
							AutenticacionWindow.getInstance().setVisible(false);
							StravaWindow.getInstance().setVisible(true);
						} else {
							System.out.println("sesion incorrecta");
						}
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				} else {
					System.out.println("Cancelar");
				}
			}
				
			
		});
		
	}
	
	

}
