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

/**
 * Edit login frame
 * Allows user to change user details
 * @author TMH
 *
 */
public class EditLogin extends JFrame {
	private static final long serialVersionUID = 1L;

	public EditLogin(){
		super("Edit Login details");
		initUi();
	}
	

	private void initUi(){
		Font font = new Font("Calibri",Font.BOLD,20);
		// initialise JPanels
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
		//create buttons, labels and textfields
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
		//checks to see if current password and username are correct then changes to new ones
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
	

	private void edit(JTextField Cname, JPasswordField  Cpass, JTextField Nname, JPasswordField Npass,JPasswordField Rpass ){
		BufferedReader br;
		String userDetails ="";
		try {
			//get the login file as it has the username and password stored inside it
			String url = this.getClass().getResource("/login.txt").getPath();
			br = new BufferedReader(new FileReader(url));

			StringBuilder sb = new StringBuilder();
	        String line = br.readLine();
	        // store the contents of the file as a string
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
	      //first make sure every field has data in it
	      if (Cname.getText().equals("") || Cpass.getPassword().length == 0 || Nname.getText().equals("")|| newPass1.equals("") || newPass2.equals("")){
	    	  JOptionPane.showMessageDialog(null,"please enter a useranme or password");
	      } else {
	    	  // check if the current username and paswword are correct
	    	  if (!(Cname.getText().equals(details.get(0)) && passText.equals(details.get(1)))){
	    			  JOptionPane.showMessageDialog(null,"username or password is wrong");
	    	  } else {
	    		  //check if the new password and retype new passwords fields are the same 
	    		  if(newPass1.equals(newPass2)){
		    		  PrintWriter writer;
		    		  try {
		    			  //clear the contents of the file
		    			  String url = this.getClass().getResource("/login.txt").getPath();
		    			  br = new BufferedReader(new FileReader(url));
		    			  writer = new PrintWriter(url);
						  writer.print("");
						  //add the new user details to the file
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
