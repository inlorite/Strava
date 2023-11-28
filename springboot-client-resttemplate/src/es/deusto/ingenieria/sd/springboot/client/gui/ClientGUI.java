package es.deusto.ingenieria.sd.springboot.client.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import es.deusto.ingenieria.sd.springboot.client.controller.ClientController;

public class ClientGUI extends JFrame {
	private static final long serialVersionUID = 1L;

	public ClientGUI(ClientController controller) {						
		//Obtain the methods of the controller using reflection
		List<Method> methods = Arrays.asList(ClientController.class.getMethods());

		Vector<String> methodNames = new Vector<>();
		methods.forEach(method -> {
			if (method.getName().contains("User")) {
				methodNames.add(method.getName());
			}
		});

		Collections.sort(methodNames);

		JList<String> endpointsJList = new JList<>(methodNames);
		endpointsJList.setBorder(new TitledBorder("Endpoints"));		
		endpointsJList.setSelectedIndex(-1);
		endpointsJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		endpointsJList.setPreferredSize(new Dimension(200, 200));
		
		JTextPane endpointResults = new JTextPane();
		endpointResults.setBackground(new Color(0, 40, 51));
		endpointResults.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(endpointResults);
		scrollPane.setBorder(new TitledBorder("Server responses"));
		
		SimpleAttributeSet orangeText = new SimpleAttributeSet();
        StyleConstants.setForeground(orangeText, Color.ORANGE);

		SimpleAttributeSet greenText = new SimpleAttributeSet();
        StyleConstants.setForeground(greenText, new Color(133, 153, 1));

		SimpleAttributeSet grayText = new SimpleAttributeSet();
        StyleConstants.setForeground(grayText, new Color(131, 148, 149));
        StyleConstants.setBold(grayText, true);
		
		// Define the lister for the JList
		endpointsJList.addListSelectionListener(e -> {
			String selectedValue = endpointsJList.getSelectedValue();
			
			StyledDocument resultsDoc = endpointResults.getStyledDocument();			
			
			if (selectedValue != null && e.getValueIsAdjusting()) {
				try {
					resultsDoc.insertString(resultsDoc.getLength(), selectedValue + "\n", grayText);
					
					Class<? extends ClientController> clientControllerClass = controller.getClass();
					//Getting method by name
					Method method = clientControllerClass.getMethod(selectedValue);
					//Method invocation using reflection
					Object result = method.invoke(controller);
					
					if (result != null) {
						resultsDoc.insertString(resultsDoc.getLength(), result.toString() + "\n\n", greenText);
					}
					
				} catch (Exception ex1) {
					try {
						resultsDoc.insertString(resultsDoc.getLength(), 
												" - The invocation throws an exception: " + ex1.getMessage() + "\n\n", orangeText);
					} catch (Exception ex2) {}
				}			
			}
		});
		
		this.setTitle("SpringBoot Client Application GUI");
		this.setLayout(new BorderLayout(5, 5));		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(endpointsJList, BorderLayout.WEST);
		this.add(scrollPane, BorderLayout.CENTER);

		this.setSize(1024, 600);
		this.setLocationRelativeTo(null);		
		this.setVisible(true);
	}
}