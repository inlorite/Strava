package es.deusto.ingenieria.sd.strava.client.gui.customComponents;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
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

public class DPanelRetos extends JPanel {

	private static final long serialVersionUID = 1L;
	private JPanel pGetRetos;
	private JPanel pApDes;
	private JScrollPane spRetos;
	private JList<RetoDTO> lRetos;
	private DefaultListModel<RetoDTO> dlmRetos;

	private JPanel pGetRetosUsuario;
	private JPanel pCrearEliminar;
	private JScrollPane spRetosUsuario;
	private JList<RetoDTO> lRetosUsuario;
	private DefaultListModel<RetoDTO> dlmRetosUsuario;

	// Nueva interfaz

	// Izquierda
	private JPanel pRetos;
	private JComboBox<String> cbFiltro;

	private JTable tRetos;
	private DefaultTableModel dtmRetos;

	private JPanel pBotones;
	private JPanel pBotonesRetosCreados;
	private JButton bCrearReto;
	private JButton bEliminarReto;
	private JPanel pBotonesRetosApuntados;
	private JButton bDesapuntarse;
	private JPanel pBotonesRetosDesapuntados;
	private JButton bApuntarse;

	// Derecha
	private JPanel pDetalle;

	private JPanel pArriba;
	private JLabel lNombreReto;
	private JLabel lFechaInicio;
	private JLabel lFechaFin;

	private JPanel pAbajo;
	private JLabel lTipoReto;
	private JLabel lDistancia;
	private JLabel lTiempo;

	private JPanel pCreador;
	private ImageIcon imagenIcono;
	private JLabel lIcono;
	private JLabel lNombre;

	private JTable tParticipantes;
	private DefaultTableModel dtmParticipantes;

	public DPanelRetos() {

		this.setLayout(new GridLayout(1, 2, 5, 5));

		// Panel Izquierdo

		pRetos = new JPanel();
		pRetos.setLayout(new BorderLayout(5, 5));
		pRetos.setBorder(new TitledBorder("Panel retos"));

		String[] filtros = { "Creados", "Apuntados", "Desapuntados" };
		cbFiltro = new JComboBox<String>(filtros);
		pRetos.add(cbFiltro, BorderLayout.NORTH);

		dtmRetos = new DefaultTableModel(new Object[] { "Nombre", "Fecha Inicio", "Tipo" }, 0);
		cargarTabla(StravaController.getInstance().getRetos());
		tRetos = new JTable(dtmRetos) {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
				return false;
			};
		};
		pRetos.add(new JScrollPane(tRetos), BorderLayout.CENTER);

		pBotonesRetosCreados = new JPanel();
		bCrearReto = new JButton();
		bEliminarReto = new JButton();
		pBotonesRetosCreados.add(bCrearReto);
		pBotonesRetosCreados.add(bEliminarReto);

		pBotonesRetosDesapuntados = new JPanel();
		bApuntarse = new JButton();
		pBotonesRetosDesapuntados.add(bApuntarse);

		pBotonesRetosApuntados = new JPanel();
		bDesapuntarse = new JButton();
		pBotonesRetosApuntados.add(bDesapuntarse);

		pBotones = new JPanel();
		pRetos.add(pBotones);

		this.add(pRetos);

		// Panel Derecho

		pDetalle = new JPanel();
		pDetalle.setLayout(new GridLayout(4, 1, 5, 5));

		pArriba = new JPanel();
		lNombreReto = new JLabel();
		lFechaInicio = new JLabel();
		lFechaFin = new JLabel();
		pArriba.add(lNombreReto);
		pArriba.add(lFechaInicio);
		pArriba.add(lFechaFin);
		pDetalle.add(pArriba);

		pAbajo = new JPanel();
		lTipoReto = new JLabel();
		lDistancia = new JLabel();
		lTiempo = new JLabel();
		pAbajo.add(lTipoReto);
		pAbajo.add(lDistancia);
		pAbajo.add(lTiempo);
		pDetalle.add(pAbajo);

		imagenIcono = new ImageIcon();
		lIcono = new JLabel(imagenIcono);
		lNombre = new JLabel();
		pCreador.add(new JLabel("Creador del reto:"));
		pCreador.add(lIcono);
		pCreador.add(lNombre);
		pDetalle.add(pCreador);

		dtmParticipantes = new DefaultTableModel(new Object[] { "Icono", "Nombre" }, 0);
		tParticipantes = new JTable(dtmParticipantes) {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
				return false;
			};
		};
		pDetalle.add(new JScrollPane(tParticipantes));

		this.add(pDetalle);

		/*
		 * pGetRetos = new JPanel(); pGetRetos.setLayout(new BorderLayout(5, 5));
		 * pGetRetos.setBorder(new TitledBorder("Obtener retos")); lRetos = new
		 * JList<>(); dlmRetos = new DefaultListModel<>();
		 * dlmRetos.addAll(StravaController.getInstance().getRetos());
		 * lRetos.setModel(dlmRetos); spRetos = new JScrollPane(lRetos);
		 * spRetos.setHorizontalScrollBarPolicy(ScrollPaneConstants.
		 * HORIZONTAL_SCROLLBAR_AS_NEEDED);
		 * spRetos.setVerticalScrollBarPolicy(ScrollPaneConstants.
		 * VERTICAL_SCROLLBAR_AS_NEEDED); bApuntarse = new JButton("Apuntarse a reto");
		 * bDesapuntarse = new JButton("Desapuntarse del reto"); pApDes = new JPanel();
		 * pApDes.setLayout(new GridLayout(1, 2, 5, 5)); pApDes.add(bApuntarse);
		 * pApDes.add(bDesapuntarse); pGetRetos.add(spRetos, BorderLayout.CENTER);
		 * pGetRetos.add(pApDes, BorderLayout.SOUTH); this.add(pGetRetos);
		 * 
		 * pGetRetosUsuario = new JPanel(); pGetRetosUsuario.setLayout(new
		 * BorderLayout(5, 5)); pGetRetosUsuario.setBorder(new
		 * TitledBorder("Obtener retos usuario")); lRetosUsuario = new JList<>();
		 * dlmRetosUsuario = new DefaultListModel<>();
		 * dlmRetosUsuario.addAll(StravaController.getInstance().getRetos(
		 * AutenticacionController.getToken()));
		 * lRetosUsuario.setModel(dlmRetosUsuario); spRetosUsuario = new
		 * JScrollPane(lRetosUsuario);
		 * spRetosUsuario.setHorizontalScrollBarPolicy(ScrollPaneConstants.
		 * HORIZONTAL_SCROLLBAR_AS_NEEDED);
		 * spRetosUsuario.setVerticalScrollBarPolicy(ScrollPaneConstants.
		 * VERTICAL_SCROLLBAR_AS_NEEDED); bCrearReto = new JButton("Crear reto");
		 * bEliminarReto = new JButton("Eliminar reto"); pCrearEliminar = new JPanel();
		 * pCrearEliminar.setLayout(new GridLayout(1, 2, 5, 5));
		 * pCrearEliminar.add(bCrearReto); pCrearEliminar.add(bEliminarReto);
		 * pGetRetosUsuario.add(spRetosUsuario, BorderLayout.CENTER);
		 * pGetRetosUsuario.add(pCrearEliminar, BorderLayout.SOUTH);
		 * this.add(pGetRetosUsuario);
		 */

		/* ACTUALIZADO */
		bApuntarse.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (tRetos.getSelectedRow() != -1) {
					boolean resultado = StravaWindow.getInstance().apuntarseReto(
							(String) tRetos.getValueAt(tRetos.getSelectedRow(), 0), AutenticacionController.getToken());
					if (resultado) {
						System.out.println("Apuntado correctamente");
						cargarTabla(StravaController.getInstance().getRetos());
					} else {
						System.out.println("No se ha podido apuntar al reto");
					}

				}

			}
		});

		/* ACTUALIZADO */
		bDesapuntarse.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (tRetos.getSelectedRow() != -1) {
					boolean resultado = StravaWindow.getInstance().desapuntarseReto(
							(String) tRetos.getValueAt(tRetos.getSelectedRow(), 0), AutenticacionController.getToken());
					if (resultado) {
						System.out.println("Desapuntado correctamente");
						cargarTabla(StravaController.getInstance().getRetos());
					} else {
						System.out.println("No se ha podido desapuntarse al reto");
					}

				}

			}
		});

		/* ACTUALIZADO */
		bCrearReto.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				JTextField tfNombre = new JTextField();
				JDateChooser dcFechaInicio = new JDateChooser("yyyy/MM/dd", "####/##/##", '_');
				JDateChooser dcFechaFin = new JDateChooser("yyyy/MM/dd", "####/##/##", '_');
				JTextField tfDistancia = new JTextField();
				JTextField tfTiempo = new JTextField();
				String[] opciones = { "Distancia", "Tiempo" };
				JComboBox<String> cbTipo = new JComboBox<>(opciones);

				Object[] fields = { "Nombre", tfNombre, "Fecha inicio", dcFechaInicio, "Fecha fin", dcFechaFin,
						"Distancia", tfDistancia, "Duracion", tfTiempo, "Tipo", cbTipo };

				int result = JOptionPane.showConfirmDialog(null, fields, "CrearSesion", JOptionPane.OK_CANCEL_OPTION);

				if (result == JOptionPane.OK_OPTION) {
					try {
						String nombre = tfNombre.getText();
						float distancia = Float.parseFloat(tfDistancia.getText());
						Date fechaInicio = dcFechaInicio.getDate();
						Date fechaFin = dcFechaInicio.getDate();
						float tiempo = Float.parseFloat(tfTiempo.getText());
						String tipo = (String) cbTipo.getSelectedItem();

						boolean resultado = StravaWindow.getInstance().crearReto(nombre, fechaInicio, fechaFin,
								distancia, tiempo, AutenticacionController.getToken(), tipo);

						if (resultado) {
							System.out.println("reto creado");
							cargarTabla(StravaController.getInstance().getRetos());
						} else {
							System.out.println("reto no creado");
						}
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				} else {
					System.out.println("Cancelar");
				}
			}
		});

		/* ACTUALIZADO */
		bEliminarReto.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (tRetos.getSelectedRow() != -1) {
					boolean resultado = StravaWindow.getInstance().eliminarReto(
							(String) tRetos.getValueAt(tRetos.getSelectedRow(), 0), AutenticacionController.getToken());
					if (resultado) {
						System.out.println("Reto eliminado correctamente");
						cargarTabla(StravaController.getInstance().getRetos());
					} else {
						System.out.println("No se ha podido eliminar el reto");
					}

				}

			}
		});

		lRetos.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (lRetos.getSelectedValue() != null) {
					if (lRetos.getSelectedValue().getCreador()
							.equals(AutenticacionController.getInstance().getUsuario())) {
						bApuntarse.setEnabled(false);
						bDesapuntarse.setEnabled(false);
					} else {
						if (lRetos.getSelectedValue().getParticipantes()
								.contains(AutenticacionController.getInstance().getUsuario())) {
							bApuntarse.setEnabled(false);
							bDesapuntarse.setEnabled(true);
						} else {
							bApuntarse.setEnabled(true);
							bDesapuntarse.setEnabled(false);
						}
					}

				}
			}
		});

		/* JTABLE SELECTION CHANGED LISTENER */
		ListSelectionModel cellSelectionModel = tRetos.getSelectionModel();
		cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				String retoSeleccionado = null;

				if (tRetos.getSelectedRow() != -1) {
					retoSeleccionado = (String) tRetos.getValueAt(tRetos.getSelectedRow(), 0);

					updateDetalles();

				} else {
					System.out.println("Ningun reto seleccionado.");
					return;
				}

				System.out.println("Selected: " + retoSeleccionado);
			}

		});

		/* COMBOBOX LISTENER */
		cbFiltro.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				cargarTabla(StravaController.getInstance().getRetos());

				switch (cbFiltro.getSelectedIndex()) {
				case 0:
					// CREADOS
					pBotones = pBotonesRetosCreados;
					return;
				case 1:
					// APUNTADOS
					pBotones = pBotonesRetosApuntados;
					return;
				case 2:
					// DESAPUNTADOS
					pBotones = pBotonesRetosDesapuntados;
					return;
				default:
					return;
				}
			}
		});

	}

	/* FUNCION QUE CARGA EL MODELO DE JTABLE CORRESPONDIENTE */
	private void cargarTabla(List<RetoDTO> retos) {

		if (cbFiltro.getSelectedIndex() == -1) {
			System.out.println("No se ha podido cargar la tabla.");
			return;
		}

		if (cbFiltro.getSelectedIndex() == 0) {
			// CREADOS
			retos = StravaController.getInstance().getRetos(AutenticacionController.getToken());
		} else if (cbFiltro.getSelectedIndex() == 1) {
			// APUNTADOS
		} else {
			// DESAPUNTADOS
		}

		// cambiar el atributo List retos en el IF
		for (RetoDTO reto : retos) {
			dtmRetos.addRow(new Object[] { reto.getNombre(), reto.getFechaFin(), reto.getTipoReto() });
		}
	}

	/* FUNCION QUE ACTUALIZA LA VISTA DE DETALLE */
	private void updateDetalles() {

		if (tRetos.getSelectedRow() == -1)
			return;

		RetoDTO reto;

		for (RetoDTO reto_i : StravaController.getInstance().getRetos()) {
			if (reto.getNombre().equals(tRetos.getValueAt(tRetos.getSelectedRow(), 0))) {
				reto = reto_i;
				break;
			}
		}

		if (reto == null)
			return;

		// Faltan por cambiar el resto de elementos
		lNombreReto = new JLabel(reto.getNombre());
		lFechaInicio = new JLabel(reto.getFechaInicio().toString());
		lFechaFin = new JLabel(reto.getFechaFin().toString());

		lTipoReto = new JLabel(reto.getTipoReto());
		lDistancia = new JLabel(reto.getDistancia() + "");
		lTiempo = new JLabel(reto.getTiempo() + "");

		lNombre = new JLabel(reto.getCreador().getNombre());
		imagenIcono = new ImageIcon(); // RECUPERAR EL ICONO Y CONVERTIRLO A IMAGEN
		lIcono = new JLabel(imagenIcono);

		for (UsuarioDTO usuario : reto.getParticipantes()) {
			dtmRetos.addRow(new Object[] { usuario.getImg(), usuario.getNombre() });
		}
	}
}
