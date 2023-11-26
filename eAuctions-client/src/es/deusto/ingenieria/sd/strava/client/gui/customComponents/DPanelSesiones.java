package es.deusto.ingenieria.sd.strava.client.gui.customComponents;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import es.deusto.ingenieria.sd.strava.server.data.dto.RetoDTO;
import es.deusto.ingenieria.sd.strava.server.data.dto.SesionEntrenamientoDTO;
import es.deusto.ingenieria.sd.strava.server.data.dto.UsuarioDTO;
import es.deusto.ingenieria.sd.strava.client.controller.AutenticacionController;
import es.deusto.ingenieria.sd.strava.client.controller.StravaController;
import es.deusto.ingenieria.sd.strava.client.gui.AutenticacionWindow;
import es.deusto.ingenieria.sd.strava.client.gui.StravaWindow;

public class DPanelSesiones extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JPanel pGetSesiones;
	private JButton bGetSesiones;
	private JScrollPane spSesiones;
	private JList<String> lSesiones;
	private DefaultListModel<String> dlmSesiones;
	
	private JPanel pGetSesionesUsuario;
	private JButton bGetSesionesUsuario;
	private JScrollPane spSesionesUsuario;
	private JList<String> lSesionesUsuario;
	private DefaultListModel<String> dlmSesionesUsuario;
	
	// Nueva interfaz

	// Izquierda
	private static JPanel pSesiones;
	private static JComboBox<String> cbFiltro;

	private static JTable tSesiones;
	private static DefaultTableModel dtmSesiones;

	private static JPanel pBotones;
	private static JButton bCrearSesion;

	// Derecha
	private static JPanel pDetalle;

	private static JPanel pArriba;
	private static JLabel lNombreSesion;
	private static JLabel lFechaInicio;
	private static JLabel lHoraInicio;

	private static JPanel pAbajo;
	private static JLabel lDeporte;
	private static JLabel lDistancia;
	private static JLabel lDuracion;
	
	private static ImageIcon mapImg;
	private static JLabel lImg;
	private SimpleDateFormat sdf;
	private static int filaSeleccionada = -1;
	
	
	public DPanelSesiones() {
		
		this.setLayout(new GridLayout(1, 2, 5, 5));
		sdf  = new SimpleDateFormat("dd/MM/yyyy");
		// Panel Izquierdo
		
		pSesiones = new JPanel();
		pSesiones.setLayout(new BorderLayout(5, 5));
		pSesiones.setBorder(new TitledBorder("Panel sesiones"));
		
		String[] filtros = { "Propias", "Todas" };
		cbFiltro = new JComboBox<String>(filtros);
		pSesiones.add(cbFiltro, BorderLayout.NORTH);
		
		dtmSesiones = new DefaultTableModel(new Object[] { "Titulo", "Fecha Inicio", "Deporte" }, 0);
		cargarTabla();
		tSesiones = new JTable(dtmSesiones) {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
				return false;
			};
		};
		
		pSesiones.add(new JScrollPane(tSesiones), BorderLayout.CENTER);
		
		pBotones = new JPanel();
		bCrearSesion = new JButton("Crear Sesion");
		pBotones.add(bCrearSesion);
		
		pSesiones.add(pBotones, BorderLayout.SOUTH);
		
		this.add(pSesiones);
		
		// Panel Derecho
		
		pDetalle = new JPanel();
		pDetalle.setLayout(new GridLayout(3, 1, 5, 5));
		pDetalle.setBorder(new TitledBorder("Panel detalle"));
		
		pArriba = new JPanel(new GridLayout(1, 3, 5, 5));
		lNombreSesion = new JLabel();
		lFechaInicio = new JLabel();
		lHoraInicio = new JLabel();
		pArriba.add(lNombreSesion);
		pArriba.add(lFechaInicio);
		pArriba.add(lHoraInicio);
		pDetalle.add(pArriba);
		
		pAbajo = new JPanel(new GridLayout(1, 3, 5, 5));
		lDeporte = new JLabel();
		lDistancia = new JLabel();
		lDuracion = new JLabel();
		pAbajo.add(lDeporte);
		pAbajo.add(lDistancia);
		pAbajo.add(lDuracion);
		pDetalle.add(pAbajo);
		
		mapImg = new ImageIcon();
		lImg = new JLabel(mapImg);
		lImg.setSize(100, 100);
		pDetalle.add(lImg);
		
		this.add(pDetalle);
		
//		pGetSesiones = new JPanel();
//		pGetSesiones.setLayout(new BorderLayout(5, 5));
//		pGetSesiones.setBorder(new TitledBorder("Obtener sesiones"));
//		lSesiones = new JList<>();
//		spSesiones = new JScrollPane(lSesiones);
//		spSesiones.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED );
//		spSesiones.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED );
//		bGetSesiones = new JButton("Obtener Sesiones");
//		pGetSesiones.add(spSesiones, BorderLayout.CENTER);
//		pGetSesiones.add(bGetSesiones, BorderLayout.SOUTH);
//		this.add(pGetSesiones);
//		
//		pGetSesionesUsuario = new JPanel();
//		pGetSesionesUsuario.setLayout(new BorderLayout(5, 5));
//		pGetSesionesUsuario.setBorder(new TitledBorder("Obtener sesiones usuario"));
//		lSesionesUsuario = new JList<>();
//		spSesionesUsuario = new JScrollPane(lSesionesUsuario);
//		spSesionesUsuario.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED );
//		spSesionesUsuario.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED );
//		pGetSesionesUsuario.add(spSesionesUsuario, BorderLayout.CENTER);
//		
//		JPanel pBotones = new JPanel(new GridLayout(1, 2, 5, 5));
//		bGetSesionesUsuario = new JButton("Obtener mis Sesiones");
//		bCrearSesion = new JButton("Crear sesion");
//		pBotones.add(bGetSesionesUsuario);
//		pBotones.add(bCrearSesion);
//		pGetSesionesUsuario.add(pBotones, BorderLayout.SOUTH);
//		
//		this.add(pGetSesionesUsuario);
		
//		bGetSesiones.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				dlmSesiones = new DefaultListModel<>();
//				List<SesionEntrenamientoDTO> lista = StravaWindow.getInstance().getSesionesEntrenamiento();
//				System.out.println("Hola "+lista.get(0).getTitulo());
//				for (SesionEntrenamientoDTO sesionEntrenamientoDTO : lista) {
//					dlmSesiones.addElement(sesionEntrenamientoDTO.toString());
//				}
//				lSesiones.setModel(dlmSesiones);
//				pGetSesiones.updateUI();
//				
//			}
//		});
//		
//		bGetSesionesUsuario.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				dlmSesionesUsuario = new DefaultListModel<>();
//				List<SesionEntrenamientoDTO> lista = StravaWindow.getInstance().getSesionesEntrenamiento(AutenticacionController.getToken());
//				System.out.println("Hola "+lista.get(0).getTitulo());
//				for (SesionEntrenamientoDTO sesionEntrenamientoDTO : lista) {
//					dlmSesionesUsuario.addElement(sesionEntrenamientoDTO.toString());
//				}
//				lSesionesUsuario.setModel(dlmSesionesUsuario);
//				pGetSesionesUsuario.updateUI();
//				
//			}
//		});
		
		bCrearSesion.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				JTextField tfTitulo = new JTextField();
				JTextField tfDistancia = new JTextField();
				JDateChooser dcFechaInicio = new JDateChooser("yyyy/MM/dd", "####/##/##", '_');
				JTextField tfHoraInicio = new JTextField();
				JTextField tfDuracion = new JTextField();
				String[] opciones = {"Ciclismo", "Running"};
				JComboBox<String> cbDeporte = new JComboBox<>(opciones);
				
				Object[] fields = {
						"Titulo", tfTitulo,
						"Distancia (km)", tfDistancia,
						"Fecha inicio", dcFechaInicio,
						"Hora inicio", tfHoraInicio,
						"Duracion (minutos)", tfDuracion,
						"Deporte", cbDeporte
				};
				
				int result = JOptionPane.showConfirmDialog(null, fields, "CrearSesion", JOptionPane.OK_CANCEL_OPTION);
				
				if (result == JOptionPane.OK_OPTION) {
					try {
						String titulo = tfTitulo.getText();
						float distancia = Float.parseFloat(tfDistancia.getText());
						Date fechaInicio = dcFechaInicio.getDate();
						long horaInicio = Long.parseLong(tfHoraInicio.getText());
						float duracion = Float.parseFloat(tfDuracion.getText());
						String deporte = (String) cbDeporte.getSelectedItem();
						
						boolean resultado  = StravaWindow.getInstance().crearSesionEntrenamiento(AutenticacionController.getToken(),titulo, distancia, fechaInicio, horaInicio,  duracion, deporte);
						
						if (resultado) {
							System.out.println("sesion creada");
							cargarTabla();
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
		
		ListSelectionModel cellSelectionModel = tSesiones.getSelectionModel();
		cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				String sesionSeleccionada = null;
				
				if (tSesiones.getSelectedRow() != -1 && filaSeleccionada!=tSesiones.getSelectedRow()) {
					sesionSeleccionada = (String) tSesiones.getValueAt(tSesiones.getSelectedRow(), 0);
					filaSeleccionada = tSesiones.getSelectedRow();
					updateDetalles();
					pDetalle.updateUI();

				} else {
					System.out.println("Ninguna sesion seleccionada.");
					return;
				}

				System.out.println("Selected: " + sesionSeleccionada);
			}

		});
		
		cbFiltro.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				cargarTabla();
				pSesiones.updateUI();
			}
		});
		
		
		
	}
	
	private void cargarTabla() {
		
		if (cbFiltro.getSelectedIndex() == -1) {
			System.out.println("No se ha podido cargar la tabla.");
			return;
		}
		List<SesionEntrenamientoDTO> sesiones = new ArrayList<>();
		dtmSesiones.setRowCount(0);
		if (cbFiltro.getSelectedIndex() == 0) {
			// CREADAS
			sesiones = StravaController.getInstance().getSesionesEntrenamiento(AutenticacionController.getToken());
			
		} else {
			// TODAS
			sesiones = StravaController.getInstance().getSesionesEntrenamiento();
		}

		// cambiar el atributo List retos en el IF
		for (SesionEntrenamientoDTO sesion : sesiones) {
			dtmSesiones.addRow(new Object[] { sesion.getTitulo(), sesion.getFechaInicio(), sesion.getDeporte() });
		}
	}
	
	/* FUNCION QUE ACTUALIZA LA VISTA DE DETALLE */
	private void updateDetalles() {

		if (tSesiones.getSelectedRow() == -1)
			return;

		SesionEntrenamientoDTO sesion = null;

		for (SesionEntrenamientoDTO sesion_i : StravaController.getInstance().getSesionesEntrenamiento()) {
			if (sesion_i.getTitulo().equals(tSesiones.getValueAt(tSesiones.getSelectedRow(), 0))) {
				sesion = sesion_i;
				break;
			}
		}
		
		if (sesion == null)
			return;

		System.out.println("Sesion seleccionada "+ sesion.toString());
		this.remove(pDetalle);
		pDetalle = new JPanel();
		pDetalle.setLayout(new GridLayout(3, 1, 5, 5));
		pDetalle.setBorder(new TitledBorder("Panel detalle"));
		lNombreSesion = new JLabel("Nombre: " +sesion.getTitulo());
		
		lFechaInicio = new JLabel("Fecha: " + sdf.format(sesion.getFechaInicio()));
		lHoraInicio = new JLabel("Hora: " + sesion.getHoraInicio() + ""); // TODO texto

		pArriba = new JPanel();
		pArriba.setLayout(new GridLayout(1, 3, 5, 5));
		pArriba.add(lNombreSesion);
		pArriba.add(lFechaInicio);
		pArriba.add(lHoraInicio);
		pDetalle.add(pArriba);
		lDeporte = new JLabel("Deporte: " + sesion.getDeporte());
		lDistancia = new JLabel("Distancia: " + sesion.getDistancia() + "km");
		if(sesion.getDuracion()<60) {
			lDuracion = new JLabel("Duracion: " + sesion.getDuracion() + "minutos");
		}else {
			int horas = (int) (sesion.getDuracion()/60);
			int minutos = (int) (sesion.getDuracion()%60);
			lDuracion = new JLabel("Duracion: " + horas + "h y "+ minutos +"min");
		}
		
		
		pAbajo = new JPanel();
		pAbajo.setLayout(new GridLayout(1, 3, 5, 5));
		pAbajo.add(lDeporte);
		pAbajo.add(lDistancia);
		pAbajo.add(lDuracion);
		pDetalle.add(pAbajo);
		
		String[] urls = { 
				"src\\es\\deusto\\ingenieria\\sd\\strava\\client\\gui\\assets\\ses1.png",
				"src\\es\\deusto\\ingenieria\\sd\\strava\\client\\gui\\assets\\ses2.png",
				"src\\es\\deusto\\ingenieria\\sd\\strava\\client\\gui\\assets\\ses3.png",
				"src\\es\\deusto\\ingenieria\\sd\\strava\\client\\gui\\assets\\ses4.png",
				"src\\es\\deusto\\ingenieria\\sd\\strava\\client\\gui\\assets\\ses5.png",
				"src\\es\\deusto\\ingenieria\\sd\\strava\\client\\gui\\assets\\ses6.png" 
		};
		
		int random = (int) (Math.random() * 6);
		System.out.println(urls[0]);
		System.out.println(random + "pon algo cabron");
		mapImg = new ImageIcon(urls[random]);		
		lImg = new JLabel();
		lImg.setSize(250, 150);
		Image imagen = mapImg.getImage();
		Image imagenEscalada = imagen.getScaledInstance(lImg.getWidth(), lImg.getHeight(),
				Image.SCALE_SMOOTH);
		lImg.setIcon(new ImageIcon(imagenEscalada));
		pDetalle.add(lImg);
		
		
		this.add(pDetalle);
	}
	
}
