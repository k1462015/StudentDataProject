package emailRelated;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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

import javax.mail.AuthenticationFailedException;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import praCourseWork2.Student;

public class SendEmailFrame extends JFrame {
	private ArrayList<Student> student;
	private JTable table;
	private JPanel listPanel;
	private JPanel firstPage;
	private JPanel secondPage;
	private File loadedSettings;
	private String[] settingsArray;

	private JTextField emailField;
	private JPasswordField password;
	private ArrayList<Student> selectedStudent;
	private JTextArea headerField;
	private JTextArea footerField;
	JTextArea prevField;

	public SendEmailFrame(ArrayList<Student> student, File settings) {
		super("Email Frame");
		this.student = student;
		initUi();
	}

	public void initUi() {
		createTable(false);
		selectedStudent = new ArrayList<Student>();
		// loadedSettings = settings;
		findSettingsFile(); // sets the File obj var as the directory for
							// settings
		// The file may or may not exist
		
		LineBorder firstPageBorder = new LineBorder(Color.BLACK,2);
		Font btnFont = new Font("Century Gothic",Font.BOLD,15);
		// First Page
		// //JList
		firstPage = new JPanel(new BorderLayout());
//		firstPage.setBorder(firstPageBorder);
		// ///////JList of students
		JPanel leftPanel = new JPanel(new BorderLayout());
		leftPanel.setBorder(firstPageBorder);
		listPanel = new JPanel(new BorderLayout());

		listPanel.add(createTable(false), BorderLayout.CENTER);

		// JList studentList = new JList();
		// listPanel.add(studentList,BorderLayout.CENTER);
		leftPanel.add(listPanel, BorderLayout.CENTER);

		// Select all/none buttons
		JPanel selectPanel = new JPanel();
		JButton selNone = new JButton("Select None");
		selNone.setFont(btnFont);
		JButton selAll = new JButton("Select All");
		selAll.setFont(btnFont);
		selAll.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				listPanel.removeAll();
				listPanel.add(createTable(true));
				validate();
			}

		});
		selNone.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				listPanel.removeAll();
				listPanel.add(createTable(false));
				validate();
			}

		});
		selectPanel.add(selNone);
		selectPanel.add(selAll);

		leftPanel.add(selectPanel, BorderLayout.NORTH);
		firstPage.add(leftPanel, BorderLayout.WEST);

		// /////Right side of Pane
		JPanel rightPane = new JPanel();
		rightPane.setLayout(new BoxLayout(rightPane, BoxLayout.PAGE_AXIS));
		rightPane.setBorder(firstPageBorder);
		// /////////Header and footer
		JLabel header = new JLabel("Header",SwingConstants.LEFT);
		header.setFont(new Font("Century Gothic",Font.BOLD,30));
		headerField = new JTextArea();
		headerField.setFont(new Font("Century Gothic",Font.BOLD,20));
		headerField.setBorder(firstPageBorder);
		
		JLabel footer = new JLabel("Footer",SwingConstants.LEFT);
		footer.setFont(new Font("Century Gothic",Font.BOLD,30));
		footerField = new JTextArea();
		footerField.setFont(new Font("Century Gothic",Font.BOLD,20));
		footerField.setBorder(firstPageBorder);

		rightPane.add(header);
		rightPane.add(new JScrollPane(headerField));
		rightPane.add(footer);
		rightPane.add(new JScrollPane(footerField));

		firstPage.add(rightPane);

		// ////////Next Button
		JPanel nextHolder = new JPanel(new BorderLayout());
		JButton next = new JButton("Next");
		next.setFont(btnFont);
	
		next.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				getContentPane().remove(firstPage);
				add(secondPage, BorderLayout.CENTER);
				repaint();
				setVisible(true);
				getCheckedStudents();
				prevField.setText("");
				prevField.append(headerField.getText() + "\r\n");
				prevField.append("RESULTS WILL APPEAR HERE" + "\r\n");
				prevField.append(footerField.getText());

			}

		});
		nextHolder.add(next, BorderLayout.EAST);
		firstPage.add(nextHolder, BorderLayout.SOUTH);

		// Second Page
		LineBorder secondPageBorder = new LineBorder(Color.BLACK,2);
		secondPage = new JPanel(new BorderLayout());
		// ////////Preview
		JPanel preview = new JPanel(new BorderLayout());
		prevField = new JTextArea();
		prevField.setEditable(false);
		prevField.setFont(new Font("Century Gothic",Font.PLAIN,30));
		preview.add(prevField, BorderLayout.CENTER);
		prevField.setBorder(secondPageBorder);
		secondPage.add(new JScrollPane(preview), BorderLayout.CENTER);

		// ///////Bottom email info Panel and buttons
		JPanel bottomPanel = new JPanel(new BorderLayout());
		secondPage.add(bottomPanel, BorderLayout.SOUTH);

		JPanel infoPanel = new JPanel();
		infoPanel.setBorder(new LineBorder(Color.BLACK,1));
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.PAGE_AXIS));
		JPanel emailPanel = new JPanel(new BorderLayout());
		JPanel passwordPanel = new JPanel(new BorderLayout());
		
		JLabel email = new JLabel("Email:");
		email.setFont(new Font("Century Gothic",Font.BOLD,15));
		JLabel pass = new JLabel("Password:");
		pass.setFont(new Font("Century Gothic",Font.BOLD,15));
		
		
		
		emailField = new JTextField();
		password = new JPasswordField();
		
		emailPanel.add(email,BorderLayout.WEST);
		emailPanel.add(emailField,BorderLayout.CENTER);
		
		passwordPanel.add(pass,BorderLayout.WEST);
		passwordPanel.add(password,BorderLayout.CENTER);
		
		infoPanel.add(emailPanel);
		infoPanel.add(passwordPanel);
		bottomPanel.add(infoPanel, BorderLayout.CENTER);

		// ////Preview and Send button
		JPanel buttonPanel = new JPanel();
		JButton previous = new JButton("Previous");
		previous.setFont(btnFont);
		JButton send = new JButton("Send");
		send.setFont(btnFont);
		send.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {


				int emailsSent = 0;
                int authFail = 0;
                int generalFail = 0;
                String failedRecipients = "";

					for (Student s : selectedStudent) {
						try {
                            sendEmail(s.getEmail(), createEmail(s));
                            if (sendEmail(s.getEmail(), createEmail(s))){
                                emailsSent++;
                                
                            } else{
                            	
                                generalFail++;
                                failedRecipients += "\n" + " - " + s.getName();
                            }
                        } catch (UnsupportedEncodingException e1) {
                        	failedRecipients += "\n" + " - " + s.getName();
                            generalFail++;
                        } catch (AuthenticationFailedException e2) {
                            //JOptionPane.showMessageDialog(null,"Email failed. Check your login and SMTP settings");
                        	failedRecipients += "\n" + " - " + s.getName();
                            authFail++;
                            generalFail++;
                            e2.printStackTrace();
                        } catch (MessagingException e3) {
                            //JOptionPane
                                //    .showMessageDialog(null,
                                    //        "Email failed. Check recipient email address, your login and SMTP settings");
                        	failedRecipients += s.getEmail() + " ";
                            generalFail++;
                            e3.printStackTrace();
                        }

					}
					
	                String message  = "";
	                if(emailsSent > 0){
	                    message += emailsSent +" emails have been sent successfully.";
	                } 
	                
	                if(generalFail > 0){
	                    message += generalFail +" emails have failed.";
	                    message += "\nFailed to send to these recipients: " + failedRecipients;
	                } 
	                
	                    
	                JOptionPane.showMessageDialog(null, message);
	            



				
			}

		});
		previous.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				getContentPane().remove(secondPage);
				add(firstPage, BorderLayout.CENTER);
				repaint();
				setVisible(true);
			}

		});
		buttonPanel.add(previous);
		buttonPanel.add(send);
		JPanel buttonPostionRight = new JPanel(new BorderLayout());
		buttonPostionRight.add(buttonPanel, BorderLayout.EAST);
		bottomPanel.add(buttonPostionRight, BorderLayout.SOUTH);

		if (!(loadedSettings == null)) {
			// System.out.println("hello");
			String[] temp = settingsData(loadedSettings);
			if (temp.length == 4) {
				settingsArray = temp;
				emailField.setText(settingsArray[2]);
			}
		}

		// Default JFrame Stuff
		add(firstPage, BorderLayout.CENTER);
		// add(secondPage,BorderLayout.CENTER);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setVisible(true);
		setSize(800, 600);
		setLocationRelativeTo(null);
	}
	


	public JScrollPane createTable(boolean b) {
		MyTableModel model = new MyTableModel();

		for (Student s : student) {
			model.addRow(new Object[] { s.getName().replaceAll("\"", ""), b });
		}
		table = new JTable(model);
		table.setFont(new Font("Century Gothic",Font.BOLD,12));

		JTableHeader header = table.getTableHeader();
		header.setFont(new Font("Century Gothic",Font.BOLD,15));
	    header.setBackground(Color.black);
	    header.setForeground(Color.WHITE);
		
		table.setPreferredScrollableViewportSize(new Dimension(300, 350));
		table.setFillsViewportHeight(true);
		table.setGridColor(Color.BLACK);
		JScrollPane scrollPane = new JScrollPane(table);
		
		table.setGridColor(Color.black);

		repaint();
		revalidate();

		return scrollPane;
	}

	public class MyTableModel extends DefaultTableModel {

		public MyTableModel() {
			super(new String[] { "Student Name", "Check" }, 0);
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
				Vector rowData = (Vector) getDataVector().get(row);
				rowData.set(1, (boolean) aValue);
				fireTableCellUpdated(row, column);
			}
		}

	}

	public String[] settingsData(File settings) {

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

	public void getCheckedStudents() {
		ArrayList<String> selectedRows = new ArrayList<String>();
		for (int i = 0; i < table.getRowCount(); i++) {
			if ((Boolean) table.getValueAt(i, 1)) {
				selectedRows.add((String) table.getValueAt(i, 0));
			}
		}

		for (Student temp : student) {
			for (String h : selectedRows) {
				if (temp.getName().equals(h)) {
					System.out.println(temp.getName() + "checked students");
					selectedStudent.add(temp);
				}
			}
		}
	}

	public void findSettingsFile() {
		String OS = System.getProperty("os.name").toLowerCase();

		if (OS.contains("windows")) {
			String user = System.getProperty("user.name");
			String filePathStr = "C:\\Users\\" + user + "\\Documents";
			System.out.println(filePathStr);
			filePathStr += "\\settings.ini";
			System.out.println(filePathStr);

			File f = new File(filePathStr);

			if (f.exists() && !f.isDirectory()) {
				System.out.println("Settings.ini exists");
				loadedSettings = f;
			}

		} else if (OS.contains("mac")) {
			String user = System.getProperty("user.name");
			String filePathStr = "/Users/" + user + "/Desktop";
			System.out.println(filePathStr);
			filePathStr += "/settings.ini";
			System.out.println(filePathStr);

			File f = new File(filePathStr);

			if (f.exists() && !f.isDirectory()) {
				System.out.println("Settings.ini exists");
				loadedSettings = f;
			}
		}
	}

	public boolean sendEmail(String toAddress, String body)
			throws UnsupportedEncodingException, AuthenticationFailedException, MessagingException {
		System.out.println(toAddress);
		String email = body;
		String to = toAddress;
		;// change accordingly
		// default settings
		String hostAddress = "outlook.office365.com"; //IP address in the form of DNS name
		String port = "587";
		String from = emailField.getText();// change accordingly
		String startTls = "true";

		// if the settings were loaded correctly, then the default settings will
		// be changed
		if (!(settingsArray == null)) {
			hostAddress = settingsArray[0];
			port = settingsArray[1];
			startTls = settingsArray[3];
		}

		// Holds the server default settings for outlook
		Properties prop = System.getProperties();
		prop.put("mail.smtp.auth", "true");

		if (!hostAddress.equals("smtp.mail.yahoo.com")) {// if it isn't yahoo,
															// add these
															// properties
			prop.put("mail.smtp.socketFactory.port", port);
			prop.put("mail.smtp.socketFactory.class",
					"javax.net.ssl.SSLSocketFactory");
		}

		prop.put("mail.smtp.starttls.enable", startTls);
		prop.put("mail.smtp.host", hostAddress);
		prop.put("mail.smtp.port", port);

		Session session = Session.getDefaultInstance(prop,
				new javax.mail.Authenticator() {

					@Override
					protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
						return new javax.mail.PasswordAuthentication(from,
								new String(password.getPassword()));
					}
				}

		);

		// compose the message
		
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(
					to));
			message.setSubject("Results");
			message.setText(email);

			// Send message
			Transport transport = session.getTransport("smtp");
			transport.connect();
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
			System.out.println("message sent successfully....");
			return true;

		

	}

	public String createEmail(Student s) {
		ArrayList<String> marks = new ArrayList<String>();

		String email = "";
		email = headerField.getText() + "\r\n";
		marks.addAll(s.getMarks());

		for (String st : marks) {

			email += st + "\r\n";
		}

		email += footerField.getText();
		System.out.println(email);
		// emails.add(email);
		marks.clear();
		return email;
	}

}
