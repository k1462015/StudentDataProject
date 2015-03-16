package extraFeatures;

import java.awt.BorderLayout;
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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import praCourseWork2.StudentFrame;

public class EditLogin extends JFrame {
	private static final long serialVersionUID = 1L;
	private static String currentDirectory = new File("").getAbsolutePath();


	public EditLogin(){
		super("Edit Login details");
		initUi();
	}
	
	public void initUi(){
		JPanel main = new JPanel(new BorderLayout());
		JPanel center = new JPanel();
		JPanel south = new JPanel(new BorderLayout());
		JPanel buttons = new JPanel();
		JPanel row1 = new JPanel();
		JPanel row2 = new JPanel();
		JPanel row3 = new JPanel();
		JPanel row4 = new JPanel();
		JPanel row5 = new JPanel();
		
		JButton save = new JButton("Save");
		
		JButton cancel = new JButton("Cancel");
		
		JLabel lblCUser = new JLabel("current username:");
		JLabel lblCPass = new JLabel("current password:");
		JTextField Cusername = new JTextField(15);
		JPasswordField  Cpassword = new JPasswordField(15);
		
		JLabel lblNUser = new JLabel("new username:");
		JLabel lblNPass = new JLabel("new password:");
		JLabel lblRPass = new JLabel("retype new password:");
		JTextField Nusername = new JTextField(15);
		JPasswordField Npassword = new JPasswordField(15);
		JPasswordField Rpassword = new JPasswordField(15);
		
		save.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0){
				edit(Cusername,Cpassword,Nusername,Npassword,Rpassword);
			}
			
		});
		row1.add(lblCUser);
		row1.add(Cusername);
		
		row2.add(lblCPass);
		row2.add(Cpassword);
		
		row3.add(lblNUser);
		row3.add(Nusername);
		
		row4.add(lblNPass);
		row4.add(Npassword);
		
		row5.add(lblRPass);
		row5.add(Rpassword);
		
		center.add(row1);
		center.add(row2);
		center.add(row3);
		center.add(row4);
		center.add(row5);
		
		buttons.add(cancel);
		buttons.add(save);
		
		south.add(buttons, BorderLayout.EAST);
		main.add(south,BorderLayout.SOUTH);
		main.add(center,BorderLayout.CENTER);
		add(main);
		
		setVisible(true);
		setSize(500,300);
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
