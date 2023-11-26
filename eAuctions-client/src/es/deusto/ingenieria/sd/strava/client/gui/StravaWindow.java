package es.deusto.ingenieria.sd.strava.client.gui;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.*;

import es.deusto.ingenieria.sd.strava.server.data.dto.RetoDTO;
import es.deusto.ingenieria.sd.strava.server.data.dto.SesionEntrenamientoDTO;
import es.deusto.ingenieria.sd.strava.client.controller.AutenticacionController;
import es.deusto.ingenieria.sd.strava.client.controller.StravaController;
import es.deusto.ingenieria.sd.strava.client.gui.customComponents.*;
import es.deusto.ingenieria.sd.strava.client.remote.ServiceLocator;

//This clase simulates the GUI of the Strava use case
public class StravaWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private static StravaWindow instance;

	public static final int GAP = 10;

	public static Container cp;
	public static JPanel mainPanel;
	public static CardLayout cl;

	public static DMenuBar menuBar;

	public static DPanelSesiones pSesiones;
	public static DPanelRetos pRetos;

	public static boolean networking = true;

	private static JPanel carruselPanel;
	private static JLabel imagenLabel;
	private Timer timer;
	private static int indiceImagen;

	private static JPanel header;

	private String[] nombresImagenes = {
			"src\\es\\deusto\\ingenieria\\sd\\strava\\client\\gui\\assets\\adidas.png",
			"src\\es\\deusto\\ingenieria\\sd\\strava\\client\\gui\\assets\\newbalance.png",
			"src\\es\\deusto\\ingenieria\\sd\\strava\\client\\gui\\assets\\nike.png" };

	public StravaWindow() {

		cp = this.getContentPane();
		cp.setLayout(new BorderLayout(GAP, GAP));
		this.setMinimumSize(new Dimension(1000, 800));

		mainPanel = new JPanel();
		cl = new CardLayout(GAP, GAP);
		mainPanel.setLayout(cl);
		header = new JPanel(new BorderLayout());

		carruselPanel = new JPanel(new BorderLayout());
		menuBar = new DMenuBar();
		
		ImageIcon iiLogo = new ImageIcon("src\\es\\deusto\\ingenieria\\sd\\strava\\client\\gui\\assets\\logo.png");
		JLabel lLogo = new JLabel(iiLogo);
		lLogo.setAlignmentX(CENTER_ALIGNMENT);
		header.add(lLogo, BorderLayout.NORTH);
		
		header.add(carruselPanel, BorderLayout.CENTER);
		header.add(menuBar,BorderLayout.SOUTH);
		header.setMinimumSize(new Dimension(400,300));
		pSesiones = new DPanelSesiones();
		pRetos = new DPanelRetos();

		cp.add(header, BorderLayout.NORTH);

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

		// CARRUSEL

		imagenLabel = new JLabel();
		imagenLabel.setSize(1000, 150);
		carruselPanel.add(imagenLabel, BorderLayout.CENTER);

		// Configurar el temporizador para cambiar las imágenes cada 2000 milisegundos
		// (4 segundos)
		timer = new Timer(4000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mostrarSiguienteImagen();
			}
		});

		// Mostrar la primera imagen al iniciar
		this.mostrarSiguienteImagen();

		// Iniciar el temporizador
		timer.start();

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				AutenticacionController.getInstance().logout();
			}
		});

		this.setTitle("Strava - Retos");
		this.setIconImage(new ImageIcon("data/icon.png").getImage());
		this.pack();
		this.setLocationRelativeTo(null); // para centrar la ventana al ejecutarla
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(true);
		this.setVisible(true);
		this.setSize(new Dimension(1000, 800));
	}

	public static StravaWindow getInstance() {
		if (instance == null) {
			instance = new StravaWindow();
		}

		return instance;
	}

	// Metodos sesiones

	public boolean crearSesionEntrenamiento(long token, String titulo, float distancia, Date fechaInicio,
			long horaInicio, float duracion, String deporte) {

		return StravaController.getInstance().crearSesionEntrenamiento(token, titulo, distancia, fechaInicio,
				horaInicio, duracion, deporte);
	}

	public List<SesionEntrenamientoDTO> getSesionesEntrenamiento(long token) {

		return StravaController.getInstance().getSesionesEntrenamiento(token);
	}

	public List<SesionEntrenamientoDTO> getSesionesEntrenamiento() {
		System.out.println("PASO1");
		return StravaController.getInstance().getSesionesEntrenamiento();
	}

	// Métodos Reto

	public boolean crearReto(String nombre, Date fechaInicio, Date fechaFin, float distancia, float tiempo, long token,
			String tipoReto) {

		return StravaController.getInstance().crearReto(nombre, fechaInicio, fechaFin, distancia, tiempo, token,
				tipoReto);
	}

	public List<RetoDTO> getRetos(long token) {

		return StravaController.getInstance().getRetos(token);
	}

	public List<RetoDTO> getRetosApuntados(long token) {

		return StravaController.getInstance().getRetosApuntados(token);
	}
	
	public List<RetoDTO> getRetosDesapuntados(long token) {

		return StravaController.getInstance().getRetosDesapuntados(token);
	}

	public boolean apuntarseReto(String reto, long token) {

		return StravaController.getInstance().apuntarseReto(reto, token);
	}

	public boolean desapuntarseReto(String reto, long token) {

		return StravaController.getInstance().desapuntarseReto(reto, token);
	}

	public boolean eliminarReto(String reto, long token) {

		return StravaController.getInstance().eliminarReto(reto, token);
	}

	private void mostrarSiguienteImagen() {
		// Obtener la URL de la imagen
		// Cambiar la imagen en el JLabel y escalarla al tamaño del JLabel
		ImageIcon imagenIcono = new ImageIcon(nombresImagenes[indiceImagen]);
		Image imagen = imagenIcono.getImage();
		Image imagenEscalada = imagen.getScaledInstance(imagenLabel.getWidth(), imagenLabel.getHeight(),
				Image.SCALE_SMOOTH);
		
		imagenLabel.setIcon(new ImageIcon(imagenEscalada));
	

		// Incrementar el índice para mostrar la siguiente imagen en la próxima llamada
		indiceImagen = (indiceImagen + 1) % nombresImagenes.length;
	}

}