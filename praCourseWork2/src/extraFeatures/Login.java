package extraFeatures;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import praCourseWork2.StudentFrame;

public class Login extends JFrame{
	private static final long serialVersionUID = 1L;
	private static String user = "admin";
	private static String pass = "soap";
	
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
		
		btnLogin.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (username.getText().equals("") || password.getPassword().length == 0){
					JOptionPane.showMessageDialog(null,"please enter a useranme or password");
				} else {
					verify(username.getText(),password.getText());
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
		setVisible(true);
		setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		setSize(300,160);
		
	}
	
	protected void verify(String username, String password) {
		if (user.equals(username) && pass.equals(password)){
			dispose();
			JFrame frame = new StudentFrame();
		} else {
			JOptionPane.showMessageDialog(null,"useranme or password is wrong");
		}
		
	}
}
