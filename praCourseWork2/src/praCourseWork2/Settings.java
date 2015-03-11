package praCourseWork2;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

public class Settings extends JFrame{
	
	private File loadedFile;
	
	private JLabel settingsLabel;
	private JLabel serverLabel;
	private JLabel portLabel;
	private JLabel authLabel;
	private JLabel starttlsLabel;
	private JLabel userLabel;
	
	private JPanel mainPanel;
	
	private JTextField serverField;
	private JSpinner portNum;
	private JCheckBox authBox;
	private JCheckBox starttlsBox;
	private JTextField userField;
	
	public Settings(){
		
		super("SMTP Server");
		
		//Creating a main panel and adding to the centre of the frame
		mainPanel = new JPanel();
//		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		
		
		//Creating the first panel for the Settings label, and then adding the label to 
		//the main panel. Added the label to the panel at BorderLayout.WEST
		settingsLabel = new JLabel("Settings ");
		JPanel settingsPanel = new JPanel(new BorderLayout());
		settingsPanel.add(settingsLabel, BorderLayout.WEST);
		mainPanel.add(settingsPanel);
		
		/*
		Created the server address panel, with the label and textfield. 
		Added the panel to the mainPanel, label at WEST and textField at CENTER
		*/
		serverLabel = new JLabel("Server Address:");
		serverField = new JTextField(20);
		/*Font serverFont = serverField.getFont().deriveFont(Font.PLAIN, 20f); 
		serverField.setFont(serverFont);*/
		
		JPanel serverPanel = new JPanel(new BorderLayout());
		serverPanel.add(serverLabel, BorderLayout.WEST);
		serverPanel.add(serverField, BorderLayout.CENTER);
//		mainPanel.add(serverPanel);
		
		/*Created the port panel, which includes the label and the spinner. 
		*/
		portLabel = new JLabel("Port:                      ");
		SpinnerNumberModel spinModel = 
				new SpinnerNumberModel(0,0,1000,1);
		portNum = new JSpinner(spinModel);
		JPanel spinPanel = new JPanel(new BorderLayout());
		spinPanel.add(portLabel, BorderLayout.WEST);
		spinPanel.add(portNum, BorderLayout.CENTER);
//		mainPanel.add(spinPanel);
		
		
		starttlsLabel = new JLabel("Starttls:");
		starttlsBox = new JCheckBox();
		JPanel starttlsPanel = new JPanel(new BorderLayout());
		starttlsPanel.add(starttlsLabel, BorderLayout.WEST);
		starttlsPanel.add(starttlsBox, BorderLayout.CENTER);
//		mainPanel.add(starttlsPanel);
		
		userLabel = new JLabel("Username:");
		userField = new JTextField(20);
		JPanel userPanel = new JPanel(new BorderLayout());
		userPanel.add(userLabel, BorderLayout.WEST);
		userPanel.add(userField, BorderLayout.CENTER);
//		mainPanel.add(userPanel);
		
		mainPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridheight = 3;
		gbc.fill = GridBagConstraints.VERTICAL;
		mainPanel.add(settingsLabel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridheight = 1;
		mainPanel.add(serverLabel, gbc);
		
		gbc.gridx = 2;
		gbc.gridy = 3;
		gbc.gridheight = 1;
		mainPanel.add(serverField, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.gridheight = 1;
		mainPanel.add(portLabel, gbc);
		
		gbc.gridx = 2;
		gbc.gridy = 4;
		gbc.gridwidth = 3;
		gbc.gridheight = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		mainPanel.add(portNum, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.gridheight = 1;
		mainPanel.add(starttlsLabel, gbc);
		
		gbc.gridx = 2;
		gbc.gridy = 5;
		gbc.gridheight = 1;
		mainPanel.add(starttlsBox, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 6;
		gbc.gridheight = 1;
		mainPanel.add(userLabel, gbc);
		
		gbc.gridx = 2;
		gbc.gridy = 6;
		gbc.gridheight = 1;
		mainPanel.add(userField, gbc);
		
		
		
		
		add(mainPanel);
		
		pack();
		
		setSize(600,500);
		setVisible(true);
		
		
		
	}

}
