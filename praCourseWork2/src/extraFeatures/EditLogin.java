package extraFeatures;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


public class EditLogin extends JFrame {
	private static final long serialVersionUID = 1L;
	private static String currentDirectory = new File("").getAbsolutePath();


	public EditLogin(){
		super("Edit Login details");
		initUi();
	}
	
	public void initUi(){
		Font font = new Font("Calibri",Font.BOLD,20);
		
		JPanel center = new JPanel();
		center.setLayout(new BoxLayout(center,BoxLayout.PAGE_AXIS));
		center.add(Box.createVerticalGlue());
		JPanel south = new JPanel(new BorderLayout());
		JPanel buttons = new JPanel();
		JPanel row1 = new JPanel(new BorderLayout());
		JPanel row2 = new JPanel(new BorderLayout());
		JPanel row3 = new JPanel(new BorderLayout());
		JPanel row4 = new JPanel(new BorderLayout());
		JPanel row5 = new JPanel(new BorderLayout());
		
		JButton save = new JButton("Save");
		save.setFont(font);
		JButton cancel = new JButton("Cancel");
		cancel.setFont(font);
		JLabel lblCUser = new JLabel("Current Username:", SwingConstants.LEFT);
		lblCUser.setFont(font);
		JLabel lblCPass = new JLabel("Current password:", SwingConstants.LEFT);
		lblCPass.setFont(font);
		JTextField Cusername = new JTextField(20);
		JPasswordField  Cpassword = new JPasswordField(20);
		
		JLabel lblNUser = new JLabel("New Username:", SwingConstants.LEFT);
		lblNUser.setFont(font);
		JLabel lblNPass = new JLabel("New Password:", SwingConstants.LEFT);
		lblNPass.setFont(font);
		JLabel lblRPass = new JLabel("Retype New password:");
		lblRPass.setFont(font);
		JTextField Nusername = new JTextField(20);
		JPasswordField Npassword = new JPasswordField(20);
		JPasswordField Rpassword = new JPasswordField(20);
		
		save.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0){
				edit(Cusername,Cpassword,Nusername,Npassword,Rpassword);
			}
			
		});
		row1.add(lblCUser,BorderLayout.WEST);
		row1.add(Cusername,BorderLayout.EAST);
		
		row2.add(lblCPass,BorderLayout.WEST);
		row2.add(Cpassword,BorderLayout.EAST);
		
		row3.add(lblNUser,BorderLayout.WEST);
		row3.add(Nusername,BorderLayout.EAST);
		
		row4.add(lblNPass,BorderLayout.WEST);
		row4.add(Npassword,BorderLayout.EAST);
		
		row5.add(lblRPass,BorderLayout.WEST);
		row5.add(Rpassword,BorderLayout.EAST);
		
		center.add(row1);
		center.add(row2);
		center.add(row3);
		center.add(row4);
		center.add(row5);
		
		buttons.add(cancel);
		buttons.add(save);
		
		south.add(buttons, BorderLayout.EAST);

		add(center,BorderLayout.CENTER);
		add(south,BorderLayout.SOUTH);
		
		pack();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public void edit(JTextField Cname, JPasswordField  Cpass, JTextField Nname, JPasswordField Npass,JPasswordField Rpass ){
		BufferedReader br;
		String userDetails ="";
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
	      String passText = new String(Cpass.getPassword());
    	  String newPass1 = new String(Npass.getPassword());
    	  String newPass2 = new String(Rpass.getPassword());
	      details = new ArrayList(Arrays.asList(str.trim().split("\\s*,\\s*")));
	      if (Cname.getText().equals("") || Cpass.getPassword().length == 0 || Nname.getText().equals("")|| newPass1.equals("") || newPass2.equals("")){
	    	  JOptionPane.showMessageDialog(null,"please enter a useranme or password");
	      } else {
	    	  if (!(Cname.getText().equals(details.get(0)) && passText.equals(details.get(1)))){
	    			  JOptionPane.showMessageDialog(null,"useranme or password is wrong");
	    	  } else {
	    		  if(newPass1.equals(newPass2)){
		    		  PrintWriter writer;
		    		  try {
		    			  writer = new PrintWriter(currentDirectory + "/login.txt");
						  writer.print("");
						  writer.print(Nname.getText() +","+newPass1);
						  writer.close();
						  JOptionPane.showMessageDialog(null,"New user details have been saved");
						  dispose();
		    		  } catch (FileNotFoundException e) {
		    			  e.printStackTrace();
		    		  }
	    		  } else {
	    			  JOptionPane.showMessageDialog(null,"passwords do not match");
	    		  }
	    	  }
	      }
	}

}
