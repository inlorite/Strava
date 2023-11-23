package es.deusto.ingenieria.sd.strava.client.gui.customComponents;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.toedter.calendar.JDateChooser;

import es.deusto.ingenieria.sd.strava.server.data.dto.RetoDTO;
import es.deusto.ingenieria.sd.strava.server.data.dto.SesionEntrenamientoDTO;
import es.deusto.ingenieria.sd.strava.client.controller.AutenticacionController;
import es.deusto.ingenieria.sd.strava.client.controller.StravaController;
import es.deusto.ingenieria.sd.strava.client.gui.AutenticacionWindow;
import es.deusto.ingenieria.sd.strava.client.gui.StravaWindow;

public class DPanelRetos extends JPanel {

	private static final long serialVersionUID = 1L;
	private JPanel pGetRetos;
	private JPanel pApDes;
	private JButton bApuntarse;
	private JButton bDesapuntarse;
	private JScrollPane spRetos;
	private JList<RetoDTO> lRetos;
	private DefaultListModel<RetoDTO> dlmRetos;

	private JPanel pGetRetosUsuario;
	private JPanel pCrearEliminar;
	private JButton bCrearReto;
	private JButton bEliminarReto;
	private JScrollPane spRetosUsuario;
	private JList<RetoDTO> lRetosUsuario;
	private DefaultListModel<RetoDTO> dlmRetosUsuario;

	public DPanelRetos() {

		this.setLayout(new GridLayout(1, 2, 5, 5));

		pGetRetos = new JPanel();
		pGetRetos.setLayout(new BorderLayout(5, 5));
		pGetRetos.setBorder(new TitledBorder("Obtener retos"));
		lRetos = new JList<>();
		dlmRetos = new DefaultListModel<>();
		dlmRetos.addAll(StravaController.getInstance().getRetos());
		lRetos.setModel(dlmRetos);
		spRetos = new JScrollPane(lRetos);
		spRetos.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		spRetos.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		bApuntarse = new JButton("Apuntarse a reto");
		bDesapuntarse = new JButton("Desapuntarse del reto");
		pApDes = new JPanel();
		pApDes.setLayout(new GridLayout(1, 2, 5, 5));
		pApDes.add(bApuntarse);
		pApDes.add(bDesapuntarse);
		pGetRetos.add(spRetos, BorderLayout.CENTER);
		pGetRetos.add(pApDes, BorderLayout.SOUTH);
		this.add(pGetRetos);

		pGetRetosUsuario = new JPanel();
		pGetRetosUsuario.setLayout(new BorderLayout(5, 5));
		pGetRetosUsuario.setBorder(new TitledBorder("Obtener retos usuario"));
		lRetosUsuario = new JList<>();
		dlmRetosUsuario = new DefaultListModel<>();
		dlmRetosUsuario.addAll(StravaController.getInstance().getRetos(AutenticacionController.getToken()));
		lRetosUsuario.setModel(dlmRetosUsuario);
		spRetosUsuario = new JScrollPane(lRetosUsuario);
		spRetosUsuario.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		spRetosUsuario.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		bCrearReto = new JButton("Crear reto");
		bEliminarReto = new JButton("Eliminar reto");
		pCrearEliminar = new JPanel();
		pCrearEliminar.setLayout(new GridLayout(1, 2, 5, 5));
		pCrearEliminar.add(bCrearReto);
		pCrearEliminar.add(bEliminarReto);
		pGetRetosUsuario.add(spRetosUsuario, BorderLayout.CENTER);
		pGetRetosUsuario.add(pCrearEliminar, BorderLayout.SOUTH);
		this.add(pGetRetosUsuario);

		bApuntarse.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (lRetos.getSelectedValue() != null) {
					boolean resultado = StravaWindow.getInstance().apuntarseReto(lRetos.getSelectedValue().getNombre(),
							AutenticacionController.getToken());
					if (resultado) {
						System.out.println("Apuntado correctamente");
						dlmRetos = new DefaultListModel<>();
						dlmRetos.addAll(StravaController.getInstance().getRetos());
						lRetos.setModel(dlmRetos);
					} else {
						System.out.println("No se ha podido apuntar al reto");
					}

				}

			}
		});

		bDesapuntarse.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (lRetos.getSelectedValue() != null) {
					boolean resultado = StravaWindow.getInstance().desapuntarseReto(
							lRetos.getSelectedValue().getNombre(), AutenticacionController.getToken());
					if (resultado) {
						System.out.println("Desapuntado correctamente");
						dlmRetos = new DefaultListModel<>();
						dlmRetos.addAll(StravaController.getInstance().getRetos());
						lRetos.setModel(dlmRetos);
					} else {
						System.out.println("No se ha podido desapuntar del reto");
					}

				}

			}
		});

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
							dlmRetos = new DefaultListModel<>();
							dlmRetos.addAll(StravaController.getInstance().getRetos());
							lRetos.setModel(dlmRetos);
							dlmRetosUsuario = new DefaultListModel<>();
							dlmRetosUsuario.addAll(
									StravaController.getInstance().getRetos(AutenticacionController.getToken()));
							lRetosUsuario.setModel(dlmRetosUsuario);
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

		bEliminarReto.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (lRetosUsuario.getSelectedValue() != null) {
					boolean resultado = StravaWindow.getInstance().eliminarReto(lRetosUsuario.getSelectedValue().getNombre(),
							AutenticacionController.getToken());
					if (resultado) {
						System.out.println("Reto eliminado correctamente");
						dlmRetos = new DefaultListModel<>();
						dlmRetos.addAll(StravaController.getInstance().getRetos());
						lRetos.setModel(dlmRetos);
						dlmRetosUsuario = new DefaultListModel<>();
						dlmRetosUsuario.addAll(
						StravaController.getInstance().getRetos(AutenticacionController.getToken()));
						lRetosUsuario.setModel(dlmRetosUsuario);
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
		
		
	}
}