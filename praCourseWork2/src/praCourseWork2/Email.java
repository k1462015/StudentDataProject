package praCourseWork2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Vector;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel; 


public class Email extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel main;
	private JPanel mainNext;
	private JPanel userDetails;
	private JTable table;
	private JPanel west;
	private JPanel center;
	private JPanel buttons;
	private JPanel south;
	private JPanel listPanel;
	private ArrayList<Student> student;
	private ArrayList<Student> selectedStudent;
	//private ArrayList<String> emails;
	
	private JTextArea header;
	private JTextArea footer;
	private JTextField userName;
	private JPasswordField pass;
	private JButton selectAll;
	private JButton selectNone;
	private JButton next;	
	private JButton previous;
	private JButton send;
	private JTextArea viewEmail;
	private JLabel EnterEmail;
	private JLabel EnterPword;
	private File loadedSettings;
	private String[] settingsArray;
	
	
	public Email(ArrayList<Student> students, File settings){
		//emails = new ArrayList<String>();
		
		loadedSettings = settings;
		
		
		/*for (int i = 0; i < settingsArray.length; i++){
			System.out.println(settingsArray[i]);
		}*/
		
		
		selectedStudent = new ArrayList<Student>();
		selectAll = new JButton("select all");
		selectAll.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				listPanel.removeAll();
				listPanel.add(createTable(true));
				validate();
			}
			
		});
		
		selectNone = new JButton("select none");
		selectNone.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				listPanel.removeAll();
				listPanel.add(createTable(false));
				validate();
			}
			
		});
		userDetails = new JPanel(new GridLayout(5,1));
		userName = new JTextField(5);

		if (!(loadedSettings == null)) {
			//System.out.println("hello");
			String []temp = settingsData(loadedSettings);
			if (temp.length == 4){
				settingsArray = temp;
				userName.setText(settingsArray[2]);
			}
		}
		
		
		pass = new JPasswordField();
		EnterEmail = new JLabel("Enter Email:");
		EnterPword = new JLabel("Enter Password:");
		
		mainNext = new JPanel(new BorderLayout());
		viewEmail = new JTextArea();
		viewEmail.setPreferredSize(new Dimension(450, 360));
		mainNext.add(viewEmail);
		next = new JButton("Next");
		next.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				main.remove(west);
				main.remove(center);
				main.add(mainNext,BorderLayout.CENTER);
				
				userDetails.add(EnterEmail); 
				userDetails.add(userName);
				userDetails.add(EnterPword); 
				userDetails.add(pass);
				main.add(userDetails, BorderLayout.NORTH);
				
				getCheckedStudents();
				String template = "";
				template = header.getText() +"\r\n";
				template += "RESULTS WILL APPEAR HERE"+"\r\n";
				template += footer.getText();
				viewEmail.setText(template);
				validate();
				repaint();
			}
			
		});
		previous = new JButton("Previous");
		previous.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				main.remove(mainNext);
				main.add(west);
				main.add(center);
				validate();
				repaint();
				
			}
			
		});
		send = new JButton("Send");
		send.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					for (Student s : selectedStudent){
						sendEmail(s.getEmail(),createEmail(s));
					}
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}	
		});
		south = new JPanel(new BorderLayout());
		student = new ArrayList<Student>(students);
		main = new JPanel(new BorderLayout());
		buttons = new JPanel();
		listPanel = new JPanel();
		west = new JPanel(new BorderLayout());
		center = new JPanel(new GridLayout(2,1,0,10));
		
		header = new JTextArea();
		header.setPreferredSize(new Dimension(50, 50));
		footer = new JTextArea();
		footer.setPreferredSize(new Dimension(50, 50));
		
		setSize(600, 550);

		JPanel SouthWest =  new JPanel();
		SouthWest.add(send);
		SouthWest.add(previous);
		
		JPanel SouthEast=  new JPanel();
		SouthEast.add(next);
		
		south.add(SouthWest,BorderLayout.WEST);
		south.add(SouthEast,BorderLayout.EAST);
		buttons.add(selectNone);
		buttons.add(selectAll);
		
		listPanel.add(createTable(false));
		
		west.add(buttons,BorderLayout.NORTH);
		west.add(listPanel, BorderLayout.CENTER);
		
		center.add(header);
		center.add(footer);
		
		main.add(center,BorderLayout.CENTER);
		main.add(west, BorderLayout.WEST);
		main.add(south, BorderLayout.SOUTH);
		
		setVisible(true);
		add(new JScrollPane(main));
		}
	
	public JScrollPane createTable(boolean b){
		MyTableModel model = new MyTableModel();
		
		for (Student s : student) {
			model.addRow(new Object[] {s.getName().replaceAll("\"", ""), b });
		}
		table = new JTable(model);

		table.setPreferredScrollableViewportSize(new Dimension(300, 350));
		table.setFillsViewportHeight(true);
		table.setGridColor(Color.GRAY);
		JScrollPane scrollPane = new JScrollPane(table);
		
		repaint();
		revalidate();
		
		return scrollPane;
	}
	
	public void getCheckedStudents(){
		ArrayList<String> selectedRows = new ArrayList<String>();
		for(int i = 0; i < table.getRowCount(); i++) {
		     if((Boolean) table.getValueAt(i, 1)) {
		         selectedRows.add((String) table.getValueAt(i, 0));
		     }
		}
		
		for(Student temp: student){
			for(String h :selectedRows){
				if(temp.getName().equals(h)){
					selectedStudent.add(temp);
				}
			}
		}
	}
	
	public String createEmail(Student s){
		ArrayList<String> marks = new ArrayList<String>();
		
		
			String email ="";
			email = header.getText() + "\r\n";
			marks.addAll(s.getMarks());
			
			for(String st : marks){
				
				email += st + "\r\n"; 
			}
			
			email+=footer.getText();
			System.out.println(email);
			//emails.add(email);
			marks.clear();
			return email;
	}
	
	public class MyTableModel extends DefaultTableModel {

	    public MyTableModel() {
	      super(new String[]{"Student Name","Check"}, 0);
	    }

	    @Override
	    public Class<?> getColumnClass(int columnIndex) {
	      Class clazz = String.class;
	      switch (columnIndex) {
	        case 0:
	          clazz = String.class;
	          break;
	        case 1:
	          clazz = Boolean.class;
	          break;
	      }
	      return clazz;
	    }

	    @Override
	    public boolean isCellEditable(int row, int column) {
	      return column == 1;
	    }

	    @Override
	    public void setValueAt(Object aValue, int row, int column) {
	      if (aValue instanceof Boolean && column == 1) {
	        System.out.println(aValue);
	        Vector rowData = (Vector)getDataVector().get(row);
	        rowData.set(1, (boolean)aValue);
	        fireTableCellUpdated(row, column);
	      }
	    }

	}
	
	public void sendEmail(String toAddress, String body) throws UnsupportedEncodingException{
		String email = body;
		String to = "toAddress"; ;//change accordingly  
	       
		//default settings
	      String hostAddress = "outlook.office365.com";
	      String port = "587";//or IP address  
	      String from = userName.getText();//change accordingly  
	      String startTls = "true";
	      
	      //if the settings were loaded correctly, then the default settings will be changed
	      if (!(settingsArray == null)){
	    	  hostAddress = settingsArray[0];
	    	  port = settingsArray[1];
	    	  startTls = settingsArray[3];
	      }
	  
	     //Holds the server default settings for outlook
	      Properties prop = System.getProperties();  
	      prop.put("mail.smtp.auth", "true");
	      prop.put("mail.smtp.socketFactory.port", port);
	      prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	      prop.put("mail.smtp.starttls.enable", startTls);
	      prop.put("mail.smtp.host", hostAddress);
	      prop.put("mail.smtp.port", port);
	      
	      
	      Session session = Session.getDefaultInstance(prop,
	    		  new javax.mail.Authenticator(){
	    	  
	    	  protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
	    		  return new javax.mail.PasswordAuthentication(from, new String(pass.getPassword()));
	    	  }
	      }
	    		  	  
	    		  );  
	  
	     //compose the message  
	      try{  
	         MimeMessage message = new MimeMessage(session);  
	         message.setFrom(new InternetAddress(from));  
	         message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
	         message.setSubject("Results");  
	         message.setText(email);  
	  
	         // Send message  
	         Transport transport = session.getTransport("smtp");
	         transport.connect();
	         transport.sendMessage(message, message.getAllRecipients());  
	         transport.close();
	         System.out.println("message sent successfully....");  
	  
	      }catch (MessagingException mex) {mex.printStackTrace();}  
	     
	}  
	
	//Reads the File object and extracts the info from it, puts it into a String array
	public String[] settingsData(File settings){
		
		BufferedReader br;
		String[] settingsArray = {};
		try {
			br = new BufferedReader(new FileReader(settings));
			String s = br.readLine();
			br.close();
			System.out.println(s);
			settingsArray = s.split(",");
			return settingsArray;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return settingsArray;
		
	}
}

