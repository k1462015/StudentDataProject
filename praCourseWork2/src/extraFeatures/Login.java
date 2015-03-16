package extraFeatures;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import praCourseWork2.StudentFrame;

public class Login extends JFrame{
	private static String currentDirectory = new File("").getAbsolutePath();
	private final long serialVersionUID = 1L;
	
	public Login(){
		super("Please login");
		initUi();
	}
	
	public void initUi(){
		JPanel main = new JPanel(new BorderLayout());
		JPanel combine = new JPanel();
		JPanel userPanel = new JPanel(); 
		JPanel passPanel = new JPanel();
		JPanel south = new JPanel(new FlowLayout());
		JLabel lblUser = new JLabel("username:");
		JLabel lblPass = new JLabel("password:");
		JTextField username = new JTextField(15);
		JPasswordField  password = new JPasswordField(15);
		JButton btnLogin = new JButton("Login");
		username.setText("admin");
		btnLogin.addActionListener(new ActionListener(){
			String userDetails = "";
			@Override
			public void actionPerformed(ActionEvent arg0) {
				BufferedReader br;
				try {
					//br = new BufferedReader(new FileReader("praCourseWork2/login.txt"));
					br = new BufferedReader(new FileReader(currentDirectory + "/login.txt"));

					StringBuilder sb = new StringBuilder();
			        String line = br.readLine();

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

				/*try {
		            String str = "admin,soap";
		            String filePath = new File("login.txt").getAbsolutePath();
		            File newTextFile = new File(filePath);

		            FileWriter fw = new FileWriter(newTextFile);
		            fw.write(str);
		            fw.close();

		        } catch (IOException iox) {
		            //do stuff with exception
		            iox.printStackTrace();
		        }*/
				
				if (username.getText().equals("") || password.getPassword().length == 0){
					JOptionPane.showMessageDialog(null,"please enter a useranme or password");
				} else {
					String passText = new String(password.getPassword());				
					char[] correctPassword = details.get(1).toCharArray();
					if ((username.getText().equals(details.get(0)) && passText.equals(details.get(1)))){
						dispose();
						JFrame frame = new StudentFrame();
					} else {
						JOptionPane.showMessageDialog(null,"useranme or password is wrong");
					}
				}
				
			}
			
		});
		userPanel.add(lblUser);
		userPanel.add(username);
		
		passPanel.add(lblPass);
		passPanel.add(password);
		
		combine.add(userPanel);
		combine.add(passPanel);
	    south.add(btnLogin);
		main.add(combine, BorderLayout.CENTER);
		main.add(south,BorderLayout.SOUTH);
		add(main);
		
		setSize(300,160);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		setVisible(true);
	}
}
