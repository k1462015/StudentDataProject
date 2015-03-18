package email;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
/**
 * JFrame to allow configuration of SMTP settings
 * @author TMH
 *
 */
public class EmailSettingsFrame extends JFrame {
	private String[] connecSecu;
	private String[] authenMeth;
	
	private File settingsFile;
	
	//The values these hold will be from an existing ini file
	private String serverPreLoaded;
	private Integer portPreLoaded;
	private String userPreLoaded;
	private String authPreLoaded;
	private JPanel main;
	
	private HelpFrame hf;
	private JTextField serverNameField;
	private JSpinner portSpinner;
	private JComboBox connectionBox;
	private JComboBox authenticationBox;
	private JTextField userField;
	private JButton ok;
	private JButton cancel;

	private static final long serialVersionUID = 1L;
	
	/**
	 * @param settings - Holds file for settings.ini within users directory
	 */
	public EmailSettingsFrame(File settings) {
		super("SMTP Server");
		this.settingsFile = settings;
		
		initUi();
		
		if (!(settingsFile == null) ){//Checks if file path is legitimate
			
			if (new Settings().checkSettings(settingsFile) == true){//Checks if file is in correct format
				
				loadSettings(settingsFile);
				setSettings();
			} else{
				System.out.println("File doesn't follow correct format");
			}
		} else {
			System.out.println("File doesn't exist");
		}
	}
	
	private void setString() {
		connecSecu = new String[1];
		connecSecu[0] = "StartTLS";

		authenMeth = new String[1];
		authenMeth[0] = "StartTLS";

	}
	
	private void initUi() {
		//Adds profile menu
		addProfileMenu();
		
		//Sets all required String[]
		setString();
		main = new JPanel(new BorderLayout());
		//Create a box Panel - To store everything
		JPanel box = new JPanel();
		box.setLayout(new BoxLayout(box, BoxLayout.PAGE_AXIS));
		main.add(box, BorderLayout.CENTER);
		
		// Create a font for all labels
		// Try different fonts if you wish
		//All of them will change if you change this one
		Font font = new Font("Arial", Font.PLAIN, 20);
		
		//Settings Label
		JLabel settings = new JLabel("Settings");
		settings.setFont(new Font("Arial", Font.BOLD, 30));
		settings.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
		JPanel settingsPanel = new JPanel(new BorderLayout());
		settingsPanel.add(settings, BorderLayout.WEST);
		box.add(settingsPanel);

		//Settings Options
		//////Server Name
		JPanel serverGrid = new JPanel(new GridLayout(1, 0));
		JLabel serverNameLabel = new JLabel("Server Name:");
		serverNameLabel.setBorder(BorderFactory.createEmptyBorder(0, 40, 0, 0));
		serverNameLabel.setFont(font);
		serverNameField = new JTextField();

		serverGrid.add(serverNameLabel);
		serverGrid.add(serverNameField);
		box.add(serverGrid);
		
		/////Port
		JPanel portGrid = new JPanel(new GridLayout(1, 0));
		JPanel portGrid2 = new JPanel(new GridLayout(1,0));
		JLabel portLabel = new JLabel("Port:");
		portLabel.setBorder(BorderFactory.createEmptyBorder(0, 40, 0, 0));
		portLabel.setFont(font);
		// Spinner Model
		Integer value = new Integer(0);
		Integer min = new Integer(0);
		Integer max = new Integer(1000);
		Integer step = new Integer(1);
		SpinnerModel model = new SpinnerNumberModel(value, min, max, step);
	    portSpinner = new JSpinner(model);
	    
	    JLabel defaultPort = new JLabel("Default:  587");
		defaultPort.setBorder(BorderFactory.createEmptyBorder(0, 587, 0, 0));
	    
	    portGrid2.add(portSpinner);
	    portGrid2.add(defaultPort);
		
		portGrid.add(portLabel);
		portGrid.add(portGrid2);
		box.add(portGrid);

		//Security and Authentication
		JLabel security = new JLabel("Security and Authentication");
		security.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
		security.setFont(new Font("Arial", Font.BOLD, 30));
		JPanel securityPanel = new JPanel(new BorderLayout());
		securityPanel.add(security, BorderLayout.WEST);
		box.add(securityPanel);

		/////Connection security
		JPanel connectionGrid = new JPanel(new GridLayout(1, 0));
		JLabel connectionLabel = new JLabel("Connection Security:");
		connectionLabel.setBorder(BorderFactory.createEmptyBorder(0, 40, 0, 0));
		connectionLabel.setFont(font);
		connectionBox = new JComboBox(connecSecu);

		//connectionGrid.add(connectionLabel);
		//connectionGrid.add(connectionBox);
		box.add(connectionGrid);

		//////Authentication Method
		JPanel authenticationGrid = new JPanel(new GridLayout(1, 0));
		JLabel authenticationLabel = new JLabel("Authentication Method:");
		authenticationLabel.setBorder(BorderFactory.createEmptyBorder(0, 40, 0, 0));
		authenticationLabel.setFont(font);
		authenticationBox = new JComboBox(authenMeth);

		authenticationGrid.add(authenticationLabel);
		authenticationGrid.add(authenticationBox);
		box.add(authenticationGrid);

		/////User Name fields
		JPanel userGrid = new JPanel(new GridLayout(1, 0));
		JLabel userLabel = new JLabel("User Name:");
		userLabel.setBorder(BorderFactory.createEmptyBorder(0, 40, 0, 0));
		userLabel.setFont(font);
		userField = new JTextField();

		userGrid.add(userLabel);
		userGrid.add(userField);
		box.add(userGrid);

		// Bottom buttons
		cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				
			}
			
		});
		ok = new JButton("OK");
		ok.addActionListener(new okListener());
		
		JButton helpBtn = new JButton("Help");
		hf = new HelpFrame();
		helpBtn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(hf.isVisible()){
					hf.setVisible(false);
				}else{
					hf.setVisible(true);
				}
				
			}
			
		});
		JPanel buttonHolder = new JPanel(new BorderLayout());
		//buttonHolder.setAlignmentX(RIGHT_ALIGNMENT);
		JPanel buttonEast = new JPanel(new FlowLayout());
		JPanel buttonWest = new JPanel(new FlowLayout());
		buttonEast.add(cancel);
		buttonEast.add(ok);
		buttonHolder.add(buttonEast,BorderLayout.EAST);
		buttonWest.add(helpBtn);
		buttonHolder.add(buttonWest,BorderLayout.WEST);
		main.add(buttonHolder, BorderLayout.SOUTH);

		add(main, BorderLayout.CENTER);
		
		// Default JFrame settings
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(600, 300);
		setLocationRelativeTo(null);
		setVisible(true);
		
		//Sets help frame
		hf.setLocation(1600, 550);
		hf.setVisible(false);

	}
	
	/**
	 * Adds profile menu to Menubar
	 */
	private void addProfileMenu(){
		//Adds profile options //EXTRA FEATURE
		JMenuBar menubar = new JMenuBar();
				
		JMenu profiles = new JMenu("Profiles");
		JRadioButton outlook = new JRadioButton("Outlook");
		JRadioButton windowsLive = new JRadioButton("Windows Live");
		JRadioButton gmail = new JRadioButton("Gmail");
		JRadioButton yahoo = new JRadioButton("Yahoo");
		outlook.addActionListener(new ActionListener(){

		@Override
		public void actionPerformed(ActionEvent e) {
		windowsLive.setSelected(false);
		gmail.setSelected(false);
		yahoo.setSelected(false);
		serverNameField.setText("outlook.office365.com");
		portSpinner.setValue(587);
			
		}
					
				});
				yahoo.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent e) {
						windowsLive.setSelected(false);
						outlook.setSelected(false);
						gmail.setSelected(false);
						serverNameField.setText("smtp.yahoo.com");
						portSpinner.setValue(587);
						
					}
					
				});
				gmail.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent e) {
						windowsLive.setSelected(false);
						outlook.setSelected(false);
						yahoo.setSelected(false);
						serverNameField.setText("smtp.gmail.com");
						portSpinner.setValue(465);
						
					}
					
				});
				windowsLive.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent e) {
						outlook.setSelected(false);
						gmail.setSelected(false);
						yahoo.setSelected(false);
						serverNameField.setText("smtp.live.com");
						portSpinner.setValue(587);
						
					}
					
				});
				
				profiles.add(windowsLive);
				profiles.add(outlook);
				profiles.add(gmail);
				profiles.add(yahoo);
				menubar.add(profiles);
				
				setJMenuBar(menubar);
	}
	
	private class okListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
		//if settings file exists 
	if (!(settingsFile == null)){
		//Write to settingsFile
		try {
			System.out.println("File exists");
			PrintWriter writer = new PrintWriter(settingsFile);
			writer.println(serverNameField.getText()+","+portSpinner.getValue()+","+userField.getText()+","+true);
			writer.close();
			
			System.out.println("File writed.");
			
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			System.out.println("File not found");
		} finally {
			dispose();
		}
	} else {//if it doesn't exist
		
		
		//Create new settings file, save it in user's documents directory and write settings to 
		//that file
		new Settings().writeToFile(serverNameField.getText(), userField.getText(),(Integer) portSpinner.getValue());
		
		
	}
			
		}
		
	}
	
	
	
	
	/**
	 * Loads settings from file
	 * @param settings - Current settings file
	 * @return A string array of the values from file
	 */
	public String[] loadSettings(File settings){
		
		String[] settingsArray = {};
		try {
			BufferedReader br = new BufferedReader(new FileReader(settings));
			String s = br.readLine();
			System.out.println(s);
			
			settingsArray = s.split(",");
			
			
			this.serverPreLoaded = settingsArray[0];
			this.portPreLoaded =  Integer.parseInt(settingsArray[1]);
			this.userPreLoaded = settingsArray[2];
			this.authPreLoaded = settingsArray[3];
			
			/*for (String p: settingsArray){
				System.out.println(p);
			}*/
			
			return settingsArray;
		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ArrayIndexOutOfBoundsException e){
			e.printStackTrace();
		}
		return settingsArray;
	}
	
	/**
	 * Inputs settings into corresponding widgets
	 */
	public void setSettings(){
		serverNameField.setText(serverPreLoaded);
		portSpinner.setValue(portPreLoaded);
		userField.setText(userPreLoaded);
		
		String temp = (String) connectionBox.getSelectedItem();
		
		if (temp.equals(authPreLoaded)){
			connectionBox.setSelectedItem("StartTSL");
		}
		
	}

}
