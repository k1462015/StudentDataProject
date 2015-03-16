package praCourseWork2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import org.jfree.data.xy.XYSeries;

import studentdata.Connector;
import studentdata.DataTable;
import websiteRelated.WebviewFrame;

import com.itextpdf.text.DocumentException;

import emailRelated.EmailSettingsFrame;
import emailRelated.SendEmailFrame;
import extraFeatures.EditLogin;
import extraFeatures.PDFGenerator;
import graphRelated.ScatterPlot;

public class StudentFrame extends JFrame {
	private ArrayList<Student> students;
	private ArrayList<Assessment> assesments;
	private JList list;
	private JTabbedPane tabbedPane;
	private boolean examLoaded;
	private boolean anonLoaded;
	private File settingsFile; // Holds directory of settings file. The file
								// itself may or may not exist
	ArrayList<String> emails;
	ArrayList<String> durations;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DisplayPopUpFrame display = null;

	public StudentFrame() {
		// Initialises frame and sets title to team name
		super("PRA Coursework - TMH");
		examLoaded = false;
		anonLoaded = false;
		findSettingsFile();
		InitUI();

	}

	public void InitUI() {

		tabbedPane = new JTabbedPane();
		// addJTable();
		JMenuBar menu = new JMenuBar();

		JMenu file = new JMenu("File");
		JMenu data = new JMenu("Data");
		JMenu extra = new JMenu("Extra");

		JMenuItem settings = new JMenuItem("Email Settings");
		settings.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				new EmailSettingsFrame(settingsFile);

			}

		});

		JMenuItem pdf = new JMenuItem("Generate PDF");
		extra.add(pdf);
		pdf.addActionListener(new pdfListener());

		data.add(settings);

		JMenuItem loadAnon = new JMenuItem("Load anonymous marking codes");
		JMenuItem loadExam = new JMenuItem("Load exam results");
		JMenuItem userEdit= new JMenuItem("User settings");
		userEdit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				JFrame userSet = new EditLogin();

			}

		});;
		
		
		JMenuItem compareAverage = new JMenuItem("Compare to Average");
		AverageListener avgListener = new AverageListener();
		compareAverage.addActionListener(avgListener);

		JMenuItem emailStudent = new JMenuItem("Email to Students");
		emailStudent.addActionListener(new emailListener());
		
		data.add(emailStudent);
		data.add(compareAverage);

		JMenuItem fetchPart = new JMenuItem("Fetch Participation");
		fetchPart.addActionListener(new fetchListener());
		data.add(fetchPart);

		// Initiliases assesment arrayList
		assesments = new ArrayList<Assessment>();

		LoadListener loadListen = new LoadListener();
		loadAnon.addActionListener(loadListen);
		loadExam.addActionListener(new loadExamListener());

		file.add(loadExam);
		file.add(loadAnon);
		menu.add(file);
		this.setJMenuBar(menu);
		setJMenuBar(menu);

		students = new ArrayList<Student>();
		// Fetches all student details from the server and adds to the student
		// ArrayList
		ServerConnect sc = new ServerConnect(students);

		JList list = createJList(students);

		JTextField search = new JTextField(22);
		search.addKeyListener(new searchListener());
		list.setFixedCellHeight(30);// cell formatting
		list.setFixedCellWidth(260);// same thing
		extra.add(userEdit);
		// Sets top panel with search to borderLayout, so search JTextField
		// Stretches through the top dynamically

		// Adds menu items
		menu.add(file);
		menu.add(data);
		menu.add(extra);

		// Adds search and list to LeftPane Panel
		JPanel leftPane = new JPanel();
		leftPane.setLayout(new BorderLayout());

		leftPane.add(search, BorderLayout.NORTH);
		leftPane.add(new JScrollPane(list), BorderLayout.CENTER);

		add(leftPane, BorderLayout.WEST);
		add(tabbedPane, BorderLayout.CENTER);

		// Default JFrame settings
		setSize(1200, 650);// MR:added size
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);// MR:added location
		validate();
		setVisible(true);

	}
	
	private class searchListener implements KeyListener{

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			JTextField search = (JTextField) e.getSource();
			// clear the list
			DefaultListModel listModel = (DefaultListModel) list.getModel();
			listModel.removeAllElements();
			// get whatever user types into text field
			String buffer = search.getText();
			// store all matching students in serachStudent arraylist
			for (Student i : students) {
				if (i.getName().toLowerCase()
						.contains(buffer.toLowerCase())
						|| i.getStudentNumber().contains(buffer)) {
					listModel.addElement(i);
				}
			}
			
		}

	
		
	}
	
	private class emailListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// // New Email Frame
			if (anonLoaded == true && examLoaded == true) {
				if (!(settingsFile == null)){//If settings file has been loaded 
					new SendEmailFrame(students, settingsFile);
					
				} else {
					
					int confirm = JOptionPane.showConfirmDialog(StudentFrame.this, "Email Settings have not been configured."
							+ "\nDefault Outlook Office 365 settings will be used."
							+ "\nDo you wish to continue?", "Email to Students", JOptionPane.YES_NO_CANCEL_OPTION);
					if (confirm == JOptionPane.YES_OPTION){
						new SendEmailFrame(students, settingsFile);
					}
					
					
				}
			} else {
				JOptionPane
						.showMessageDialog(
								null,
								"Anonymous Markings Codes file needs to be"
										+ " uploaded \nExam marks file needs to be uploaded ");
			}
			
		}
		
	}
	
	private class pdfListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				new PDFGenerator().createPdf(students);
			} catch (DocumentException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			JOptionPane.showMessageDialog(
					null,
					students.size()
							+ " Student information succesfully succesfully imported to PDF in desktop");
			
		}
		
	}
	
	private class loadExamListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser choosy = new JFileChooser();
			String assessment = "";
			File f = new File("C://Users//Saif//workspace");
			choosy.setCurrentDirectory(f);

			// Creates filter so user can only select CSV file
			FileNameExtensionFilter filter = new FileNameExtensionFilter(
					"CSV Files", "csv");
			choosy.setFileFilter(filter);

			// Checks if a file has been opened
			int returnValue = choosy.showOpenDialog(StudentFrame.this);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				boolean validFile = false;

				// Sets file to chosen file
				File file = choosy.getSelectedFile();
				// First checks if file first row/column contains numbers
				// If it does then it is not exam file
				BufferedReader bf;
				try {
					bf = new BufferedReader(new FileReader(file));
					// Finds corresponding column indexes
					// Reads first line to get column headings
					String line = bf.readLine();
					String[] linesplit = line.split(",");
					if (!linesplit[0].matches((".*\\d.*"))) {
						validFile = true;
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				if (validFile) {
					loadExams(file, assessment);
				} else {
					JOptionPane.showMessageDialog(null,
							"Please upload a valid exam.csv file");
				}
			}

			
		}
		
	}
	
	private class fetchListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			emails = new ArrayList<String>();
			durations = new ArrayList<String>();
			JTextField moduleField = new JTextField(6);
			JTextField urlField = new JTextField(20);

			JPanel messagePanel = new JPanel();
			messagePanel.setLayout(new BoxLayout(messagePanel,
					BoxLayout.PAGE_AXIS));
			messagePanel.add(new JLabel("Module:"));
			messagePanel.add(moduleField);
			// messagePanel.add(Box.createHorizontalStrut(15)); // a spacer
			messagePanel.add(new JLabel("URL:"));
			messagePanel.add(urlField);

			int response = JOptionPane.showConfirmDialog(null,
					messagePanel, "Please Enter module Code and the URL",
					JOptionPane.OK_CANCEL_OPTION);

			if (response == JOptionPane.OK_OPTION) {
				System.out.println("Module code is: "
						+ moduleField.getText());
				System.out.println("URL is : " + urlField.getText());
				while (moduleField.getText().equals("")
						&& urlField.getText().equals("")) {
					response = JOptionPane.showConfirmDialog(null,
							messagePanel,
							"Please Enter module Code and the URL",
							JOptionPane.OK_CANCEL_OPTION);
				}
				WebviewFrame wb = new WebviewFrame(urlField.getText());
				wb.btnFetch.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						wb.browser.readDocument();
						emails = wb.getEmails();
						durations = wb.getDurations();

						System.out.println("Emails size is "
								+ emails.size() + " and duration size is "
								+ durations.size());
						for (int i = 0; i < emails.size(); i++) {
							for (Student s : students) {
								if (s.email.equals(emails.get(i))) {
									System.out.println("Found email of "
											+ emails.get(i)
											+ " with duration "
											+ durations.get(i));
									s.addParticipation(moduleField
											.getText()
											+ " "
											+ durations.get(i) + " ago");
								}
							}
						}
						JOptionPane.showMessageDialog(
								null,
								emails.size()
										+ " participation records were succesfully imported");
						wb.dispose();
					}
				});
			}
			
		}
		
	}
	


	public void loadExams(File file, String assessment) {
		try {
			BufferedReader bf = new BufferedReader(new FileReader(file));
			// Finds corresponding column indexes
			// Reads first line to get column headings
			String line = bf.readLine();
			String[] linesplit = line.split(",");
			int yearCol = 0;
			int periodCol = 0;
			int occCol = 0;
			int nameCol = 0;
			int mapCol = 0;

			int moduleCol = 0;
			int assCol = 0;
			int candCol = 0;
			int markCol = 0;
			int gradeCol = 0;
			for (int i = 0; i < linesplit.length; i++) {
				System.out.println(linesplit[i]);
				if (linesplit[i].equals("\"#Module\"")
						|| linesplit[i].equals("#Module")) {
					moduleCol = i;
				} else if (linesplit[i].equals("\"#Ass#\"")
						|| linesplit[i].equals("#Ass#")) {
					assCol = i;
				} else if (linesplit[i].equals("\"#Cand Key\"")
						|| linesplit[i].equals("#Cand Key")) {
					candCol = i;
				} else if (linesplit[i].equals("\"Mark\"")
						|| linesplit[i].equals("Mark")) {
					markCol = i;
				} else if (linesplit[i].equals("\"Grade\"")
						|| linesplit[i].equals("Grade")) {
					gradeCol = i;
				} else if (linesplit[i].equals("\"Year\"")
						|| linesplit[i].equals("Year")) {
					yearCol = i;
				} else if (linesplit[i].equals("\"Period\"")
						|| linesplit[i].equals("Period")) {
					periodCol = i;
				} else if (linesplit[i].equals("\"Occ\"")
						|| linesplit[i].equals("Occ")) {
					occCol = i;
				} else if (linesplit[i].equals("\"Name\"")
						|| linesplit[i].equals("Name")) {
					nameCol = i;
				} else if (linesplit[i].equals("\"#Map\"")
						|| linesplit[i].equals("#Map")) {
					mapCol = i;
				}

			}

			// Adds records to assessments
			while ((line = bf.readLine()) != null) {
				linesplit = line.split(",");
				String ass = linesplit[assCol].replaceAll("\"", "");
				Result temp = new Result(
						linesplit[yearCol].replaceAll("\"", ""),
						linesplit[periodCol].replaceAll("\"", ""),
						linesplit[moduleCol].replaceAll("\"", ""),
						linesplit[occCol], linesplit[mapCol].replaceAll("\"",
								""), ass, linesplit[candCol].replaceAll("\"",
								""), linesplit[nameCol].replaceAll("\"", ""),
						Integer.parseInt(linesplit[markCol]),
						linesplit[gradeCol]);
				assessment = temp.getModuleCode();
				// First checks if Assessment array is empty
				if (assesments.isEmpty()) {
					Assessment t1 = new Assessment();
					t1.addResult(temp);
					assesments.add(t1);
					// Now checks if there is already an assessment
					// object
					// With same assessment number
					// If not make new assessment object
					// Then add record
				} else if (!checkAllAss(temp.getAssessment())) {
					Assessment t1 = new Assessment();
					t1.addResult(temp);
					assesments.add(t1);
				} else {
					// Since there is existing assessment object
					// Finds it, and adds record
					for (int i = 0; i < assesments.size(); i++) {
						if (assesments.get(i).results.get(0).getAssessment()
								.equals(temp.getAssessment())) {
							assesments.get(i).addResult(temp);
						}
					}
				}

			}
			//Creates table and adds to Tabbed Pane
			tabbedPane();
			examLoaded = true;

		} catch (FileNotFoundException p) {
			System.out.println("File not found");
		} catch (IOException g) {
			System.out.println("Error");
		}

	}

	public void tabbedPane() {
		String name = "";
		int count = 0;
		// loops through each assesment and creates a tab and a table for that
		// assessment
		for (Assessment a : assesments) {
			name = (a.getModuleCode(a.getIndex(count))).replaceAll("\"", "")
					+ " " + a.getAssessment(a.getIndex(count));
			count++;
			ExamTable jtable = new ExamTable(a,assesments,students);
			jtable.getTable().addMouseListener(new TableListener());
			tabbedPane.addTab(name, new JScrollPane(jtable.getTable()));
		}
		assesments.clear();
	}

	/**
	 * Checks through all Assessment Objects Within Assessment ArrayList If
	 * finds existing one, that matches string returns true
	 * 
	 * @param s
	 *            - assessment code
	 * @return
	 */
	public boolean checkAllAss(String s) {
		for (Assessment t : assesments) {
			if (t.results.get(0).getAssessment().equals(s)) {
				// If does have assessment already
				return true;
			}
		}
		return false;
	}

	
	private class TableListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			JTable table = (JTable) e.getSource();
			if (e.getClickCount() == 2) {
				int row = table.getSelectedRow();
				int column = table.getSelectedColumn();
				// Checks if column is student name column
				if (column == 7) {
					// Create Display PopUp
					String selectedItem = (String) table.getValueAt(row,
							column - 1);
					if (!selectedItem.substring(0, 1).equals("#")) {
						System.out.println("Create Display PopUp");
						showDisplayPopUp(selectedItem);
					} else {
						System.out
								.println("Not valid student Number/Anonymous marking code present");
					}

				}
			}

			
		}

		@Override
		public void mousePressed(MouseEvent e) {}

		@Override
		public void mouseReleased(MouseEvent e) {}

		@Override
		public void mouseEntered(MouseEvent e) {}

		@Override
		public void mouseExited(MouseEvent e) {}
		
	}

	public JList createJList(ArrayList<Student> students) {

		DefaultListModel defListMod = new DefaultListModel();// create a list of
																// items that
																// are editable.
																// original list
		// does not allow you to do that. this allows more flexibility

		// goes through arraylist of Student objects, calls toString and adds
		// the Strings to DefaultListModel (DLM)
		for (Student s : students) {
			defListMod.addElement(s.toString());
		}

		list = new JList(defListMod);// creates a new JList using the DLM
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Student findStudent = null;
				if (e.getClickCount() == 2) {
					String selectedItem = list.getSelectedValue().toString();
					showDisplayPopUp(selectedItem);
				}
			}
		});

		list.setSelectionBackground(Color.black);
		list.setSelectionForeground(Color.WHITE);
		list.setFont(new Font("Calibri", Font.BOLD, 20));
		return list;
	}

	public void showDisplayPopUp(String data) {
		Student findStudent = null;
		findStudent = findStudent(data, students);
		if (display != null) {
			if (display.name.getText().equals(findStudent.name)) {
				// Debugging purposes
				System.out.println("Disposed");
				display.dispose();
			}
		}
		display = new DisplayPopUpFrame(findStudent);

	}

	public Student findStudent(String check, ArrayList<Student> studentArrayList) {
		Student found = null;

		for (int i = 0; i < studentArrayList.size(); i++) {
			// Checks if searching using student Number or toString
			if (!check.substring(check.length() - 1, check.length())
					.equals(")")) {
				if (studentArrayList.get(i).getStudentNumber().equals(check)) {
					found = studentArrayList.get(i);
				}
			} else {
				if (studentArrayList.get(i).toString().equals(check)) {
					found = studentArrayList.get(i);
				}

			}

		}
		return found;

	}

	private class AverageListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			if (examLoaded == true && anonLoaded == true) {

				JScrollPane currentScrollPane = (JScrollPane) tabbedPane
						.getComponentAt(tabbedPane.getSelectedIndex());
				JViewport viewport = currentScrollPane.getViewport();
				JTable currentTable = (JTable) viewport.getView();

				XYSeries data = new XYSeries("Student");// Will hold our data,
														// or plot points

				int numOfRecords = currentTable.getRowCount();

				// Loops through the records, gets the appropriate student
				// object from the arraylist,
				// gets the average of the student and plots it with their mark.
				for (int i = 0; i < numOfRecords; i++) {
					String tempCandKey = (String) currentTable.getValueAt(i, 6);
					System.out.println(tempCandKey);
					Student tempStu = findStudent(tempCandKey, students);// The
																			// student
																			// of
																			// a
																			// specific
																			// row

					// if the tempStu is not null, then gets their mark from the
					// table
					// and then plots the tempStu's average against their
					// current mark
					if (!(tempStu == null)) {
						System.out.println(tempStu.toString());
						int stuMarkInt = (Integer) currentTable
								.getValueAt(i, 8);
						// double stuMark = (double) stuMarkInt;
						// System.out.println(stuMark);
						tempStu.calcAverage();
						data.add(tempStu.average, stuMarkInt);
					} else {
						System.out.println("Error error error");
					}
				}

				String modCode = (String) currentTable.getValueAt(0, 2);
				System.out.println("Making chart...");
				ScatterPlot scatter = new ScatterPlot("Graph",
						"Comparison of Average in Assessment", modCode, data);

			} else if (anonLoaded == true && examLoaded == false) {
				JOptionPane
						.showMessageDialog(rootPane,
								"You need to load an exam results file, before you can create the chart");
			} else if (anonLoaded == false && examLoaded == false) {
				JOptionPane
						.showMessageDialog(
								rootPane,
								"You need to first load an anonymous marking code file, then an exam results file");
			}

		}

	}

	private class LoadListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser choosy = new JFileChooser();

			File f = new File("C://Users");
			choosy.setCurrentDirectory(f);

			// Creates filter so user can only select CSV file
			FileNameExtensionFilter filter = new FileNameExtensionFilter(
					"CSV Files", "csv");
			choosy.setFileFilter(filter);

			int returnValue = choosy.showOpenDialog(StudentFrame.this);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				boolean validFile = false;

				// Just some code to help with debugging later
				File file = choosy.getSelectedFile();
				int succesImport = 0;
				int totalImports = 0;
				// First checks if valid file
				BufferedReader bfCheck;
				try {
					bfCheck = new BufferedReader(new FileReader(file));

					String line = bfCheck.readLine();
					String[] linesplit = line.split(",");
					if (linesplit[0].matches((".*\\d.*"))) {
						validFile = true;
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if (validFile) {
					try {
						BufferedReader bf = new BufferedReader(new FileReader(
								file));
						while (bf.ready()) {
							String[] line = bf.readLine().split(",");
							for (Student s : students) {
								int temp = Integer.parseInt(line[0]);
								if (temp == s.studentNumber) {
									s.setAMC(line[1]);
									succesImport++;
								}
							}

							totalImports++;
						}
						int failedImports = totalImports - succesImport;
						String results = "Annonymous marking codes imported. "
								+ succesImport
								+ " codes were \nfor known students; "
								+ failedImports + " were or unknown students";
						JOptionPane.showMessageDialog(StudentFrame.this,
								results);

						anonLoaded = true;

					} catch (FileNotFoundException p) {
						System.out.println("File not found");
					} catch (IOException g) {
						System.out.println("Error");
					}

					System.out.println("You have chosen "
							+ choosy.getSelectedFile().getName()
							+ " to be imported");
				} else {
					JOptionPane
							.showMessageDialog(null,
									"Please upload a valid \nAnonymous marking code csv file");
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
				settingsFile = f;
				
			} else {
				System.out.println("Settings.ini doesn't exist yet");
				
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
				settingsFile = f;
				
			} else {
				System.out.println("Settings.ini doesn't exist yet");
				
			}
		} else if (OS.contains("nix")) {
			String filePathStr = "~/Desktop";
			System.out.println(filePathStr);
			filePathStr += "/settings.ini";
			System.out.println(filePathStr);

			File f = new File(filePathStr);

			if (f.exists() && !f.isDirectory()) {
				System.out.println("Settings.ini exists");
				settingsFile = f;
				
			} else {
				System.out.println("Settings.ini doesn't exist yet");
				
			}
		}
		

	}

}
