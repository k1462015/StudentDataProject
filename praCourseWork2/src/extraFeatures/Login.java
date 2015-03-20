package extraFeatures;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import main.MainFrame;
/**
 * Login frame - prevents unauthorized access to system
 * @author TMH
 *
 */
public class Login extends JFrame{
	private final long serialVersionUID = 1L;
	/**
	 * Constructs login frame
	 */
	public Login(){
		super("Please login");
		initUi();
	}
	
	
	private void initUi(){
		Font font = new Font("Calibri",Font.BOLD,20);
		
		//Initialises required JPanels
		JPanel main = new JPanel(new BorderLayout());
		JPanel combine = new JPanel();
		JPanel userPanel = new JPanel(); 
		JPanel passPanel = new JPanel();
		
		//Creates JLabel for username and password 
		JLabel lblUser = new JLabel("Username:");
		lblUser.setFont(font);
		JLabel lblPass = new JLabel("Password:");
		lblPass.setFont(font);
		JTextField username = new JTextField(15);
		//Creates password field for password entry
		JPasswordField  password = new JPasswordField(15);
		
		//Login button
		JButton btnLogin = new JButton("Login");
		username.setText("admin");
		//Checks if username and password are correct
		btnLogin.addActionListener(new ActionListener(){
			String userDetails = "";
			@Override
			public void actionPerformed(ActionEvent arg0) {
				BufferedReader br;
				try {
					//Get the login file 
					InputStreamReader ir = new InputStreamReader(getClass().getClassLoader().getResourceAsStream("login.txt"));
					br = new BufferedReader(ir);

					StringBuilder sb = new StringBuilder();
			        String line = br.readLine();
			        //Store the contents of the file as a string
			        while (line != null) {
			            sb.append(line);
			            sb.append(System.lineSeparator());
			            line = br.readLine();
			        }
			        userDetails = sb.toString();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
      
				  String str = userDetails;

			      ArrayList<String> details = null;
			      details = new ArrayList(Arrays.asList(str.trim().split("\\s*,\\s*")));
			      //Check if something has been entered for username and password fields
				if (username.getText().equals("") || password.getPassword().length == 0){
					JOptionPane.showMessageDialog(null,"Please enter a useranme or password");
				} else {
					String passText = new String(password.getPassword());				
					char[] correctPassword = details.get(1).toCharArray();
					// check if the login details are correct
					if ((username.getText().equals(details.get(0)) && passText.equals(details.get(1)))){
						dispose();
						JFrame frame = new MainFrame();
					} else {
						JOptionPane.showMessageDialog(null,"Your useranme or password is wrong");
					}
				}
				
			}
			
		});
		//Adds all components to corresponding panels
		userPanel.add(lblUser);
		userPanel.add(username);
		
		passPanel.add(lblPass);
		passPanel.add(password);
		
		combine.add(userPanel);
		combine.add(passPanel);
		main.add(combine, BorderLayout.CENTER);
		main.add(btnLogin,BorderLayout.SOUTH);
		add(main);
		
		//Allows enter key to press login button
		username.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				btnLogin.doClick();
				
			}
			
		});
		password.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				btnLogin.doClick();
				
			}
			
		});
		
		//Adds lock image
		try {
			URL imgURL = getClass().getResource("/Lock-Icon.png");
		    ImageIcon ii = new ImageIcon(imgURL);
		    Image newimg = ii.getImage().getScaledInstance(90, 90,java.awt.Image.SCALE_SMOOTH);
			add(new JLabel(new ImageIcon(newimg)),BorderLayout.NORTH);
		} catch (Exception e) {
			System.out.println("Error reading file");
		}
		
		setSize(300,250);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
}
