package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
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

import org.jfree.data.xy.XYSeries;

import student.Assessment;
import student.DisplayPopUpFrame;
import student.Student;
import KEATSScraper.WebviewFrame;

import com.itextpdf.text.DocumentException;

import data.CSVLoader;
import data.ExamTable;
import data.ServerConnect;
import email.EmailSettingsFrame;
import email.SendEmailFrame;
import email.Settings;
import extraFeatures.EditLogin;
import extraFeatures.PDFGenerator;
import graph.ScatterPlot;

/**
 * Main frame containing all components to display student data
 * 
 * @author TMH
 *
 */
public class MainFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private ArrayList<Student> students;
	private ArrayList<Assessment> assesments;
	private JList list;
	private JTabbedPane tabbedPane;
	private boolean examLoaded;
	private boolean anonLoaded;
	private File settingsFile;

	ArrayList<String> emails;
	ArrayList<String> durations;
	private DisplayPopUpFrame display = null;

	public MainFrame() {
		// Initialises frame and sets title to team name
		super("PRA Coursework - TMH");
		examLoaded = false;
		anonLoaded = false;
		settingsFile = new Settings().findSettingsFile();
		InitUI();

	}

	private void InitUI() {
		// Adds MenuBar
		addMenuBar();

		// //Fetches Students and creates JList
		students = new ArrayList<Student>();
		ServerConnect sc = new ServerConnect(students);
		JList list = createJList(students);
		JTextField search = new JTextField(22);
		search.addKeyListener(new searchListener());
		list.setFixedCellHeight(30);// cell formatting
		list.setFixedCellWidth(260);// same thing

		// Initializes required fields
		assesments = new ArrayList<Assessment>();
		tabbedPane = new JTabbedPane();

		// Adds search and list to LeftPane Panel
		JPanel leftPane = new JPanel();
		leftPane.setLayout(new BorderLayout());

		leftPane.add(search, BorderLayout.NORTH);
		leftPane.add(new JScrollPane(list), BorderLayout.CENTER);

		add(leftPane, BorderLayout.WEST);
		add(tabbedPane, BorderLayout.CENTER);

		// Default JFrame settings
		setSize(1200, 650);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		validate();
		setVisible(true);

	}

	private void addMenuBar() {
		// Adds MenuBar to frame
		JMenuBar menuBar = new JMenuBar();
		JMenu file = new JMenu("File");
		JMenu data = new JMenu("Data");
		JMenu extra = new JMenu("Extra");
		menuBar.add(file);
		menuBar.add(data);
		menuBar.add(extra);
		setJMenuBar(menuBar);

		// //////File Menu
		JMenuItem loadExam = new JMenuItem("Load exam results");
		JMenuItem loadAnon = new JMenuItem("Load anonymous marking codes");
		JMenuItem exit = new JMenuItem("Exit");

		loadAnon.addActionListener(new LoadAnonListener());
		loadExam.addActionListener(new LoadExamListener());
		exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();

			}

		});
		file.add(loadExam);
		file.add(loadAnon);
		file.add(exit);

		// //////Data Menu
		JMenuItem settings = new JMenuItem("Email Settings");
		JMenuItem emailStudent = new JMenuItem("Email to Students");
		JMenuItem compareAverage = new JMenuItem("Compare to Average");
		JMenuItem fetchPart = new JMenuItem("Fetch Participation");

		settings.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				new EmailSettingsFrame();

			}

		});
		emailStudent.addActionListener(new EmailListener());
		compareAverage.addActionListener(new GraphListener());
		fetchPart.addActionListener(new FetchListener());

		data.add(settings);
		data.add(emailStudent);
		data.add(compareAverage);
		data.add(fetchPart);

		// /////Extra Menu
		JMenuItem pdf = new JMenuItem("Generate PDF");
		JMenuItem userEdit = new JMenuItem("User settings");
		JMenuItem exportCSV = new JMenuItem("Export selected table");
		pdf.addActionListener(new PDFListener());
		userEdit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				JFrame userSet = new EditLogin();

			}

		});
		exportCSV.addActionListener(new CSVExporter());
		extra.add(userEdit);
		extra.add(pdf);
		extra.add(exportCSV);

	}

	
	private JList createJList(ArrayList<Student> students) {

		DefaultListModel defListMod = new DefaultListModel();
		// create a list of items that are editable original list

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
					generateDisplayPopUp(selectedItem);
				}
			}
		});

		list.setSelectionBackground(Color.black);
		list.setSelectionForeground(Color.WHITE);
		list.setFont(new Font("Calibri", Font.BOLD, 20));
		return list;
	}


	private Student findStudent(String studentIdentity,
			ArrayList<Student> students) {
		Student found = null;

		for (int i = 0; i < students.size(); i++) {
			// Checks if searching using student Number or toString
			if (!studentIdentity.substring(studentIdentity.length() - 1,
					studentIdentity.length()).equals(")")) {
				if (((students.get(i).getStudentNumber()+"").equals(studentIdentity))) {
					found = students.get(i);
				}
			} else {
				if (students.get(i).toString().equals(studentIdentity)) {
					found = students.get(i);
				}

			}

		}
		return found;

	}


	private void tabbedPane() {
		int count = 0;
		boolean checkExists = false;
		// loops through each assesment and creates a tab and a table for that
		// assessment
		for (Assessment a : assesments) {
			String name = (a.getIndex(count).getModuleCode()).replaceAll("\"", "")+ " " + a.getIndex(count).getAssessment()+" "+a.getIndex(count).getYear();
			for(int i = 0; i < tabbedPane.getTabCount();i++){
				if(tabbedPane.getTitleAt(i).equals(name)){
					checkExists = true;
				}
			}
			if(!checkExists){
			count++;
			ExamTable jtable = new ExamTable(a, assesments, students);
			jtable.getTable().addMouseListener(new NameCellListener());
			tabbedPane.addTab(name, new JScrollPane(jtable.getTable()));
			}else{
				JOptionPane.showMessageDialog(null, "You have already uploaded this CSV file before.\nPlease upload a different CSV file.");
			}

		}
		assesments.clear();
	}

	
	private void generateDisplayPopUp(String studentName) {
		Student findStudent = null;
		findStudent = findStudent(studentName, students);
		if(findStudent != null){
		if (display != null) {
			if (display.getName().equals(findStudent.getName())) {
				// Debugging purposes
				System.out.println("Disposed");
				display.dispose();
			}
		}
		display = new DisplayPopUpFrame(findStudent);
		}else{
			System.out.println("No student found");
		}
	}

	private class searchListener extends KeyAdapter {
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
				if (i.getName().toLowerCase().contains(buffer.toLowerCase())
						|| (i.getStudentNumber() + "").contains(buffer)) {
					listModel.addElement(i);
				}
			}

		}

	}

	private class LoadAnonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (new CSVLoader().loadAnonCode(students)) {
				anonLoaded = true;
			} else {
				anonLoaded = false;
			}

		}
	}

	private class LoadExamListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				if (new CSVLoader().loadExamCSV(assesments)) {
					// Creates table and adds to Tabbed Pane
					tabbedPane();
					examLoaded = true;
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		}

	}

	private class EmailListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// // New Email Frame
			if (anonLoaded == true && examLoaded == true) {
				if (!(settingsFile == null)) {// If settings file has been
												// loaded
					new SendEmailFrame(students, settingsFile);
				} else {
					int confirm = JOptionPane
							.showConfirmDialog(
									null,
									"Email Settings have not been configured."
											+ "\nDefault Outlook Office 365 settings will be used."
											+ "\nDo you wish to continue?",
									"Email to Students",
									JOptionPane.YES_NO_CANCEL_OPTION);
					if (confirm == JOptionPane.YES_OPTION) {
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

	private class PDFListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				new PDFGenerator().addDataPdf(students);
			} catch (DocumentException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			JOptionPane
					.showMessageDialog(
							null,
							students.size()
									+ " Student information succesfully succesfully imported to PDF in desktop");
		}

	}

	private class NameCellListener extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			JTable table = (JTable) e.getSource();
			if (e.getClickCount() == 2) {
				int row = table.getSelectedRow();
				int column = table.getSelectedColumn();
				// Checks if column is student name column
				if (column == 0) {
					// Create Display PopUp
					String selectedItem = (String) table.getValueAt(row, column+1);
					System.out.println(selectedItem);
					if (!selectedItem.substring(0, 1).equals("#")) {
						System.out.println("Create Display PopUp");
						generateDisplayPopUp(selectedItem);
					} else {
						System.out
								.println("Not valid student Number/Anonymous marking code present");
					}

				}
			}

		}

	}

	private class GraphListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (examLoaded == true && anonLoaded == true) {
				System.out.println("Plotting graph...");
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
					String tempCandKey = (String) currentTable.getValueAt(i, 0);
					System.out.println(tempCandKey);
					Student tempStu = findStudent(tempCandKey, students);
					// The student of a specific row
					// if the tempStu is not null, then gets their mark from the
					// table
					// and then plots the tempStu's average against their
					// current mark
					if (!(tempStu == null)) {
						System.out.println(tempStu.toString());
						int stuMarkInt = (Integer) currentTable
								.getValueAt(i, 3);
						// double stuMark = (double) stuMarkInt;
						// System.out.println(stuMark);
						tempStu.calcAverage();
						data.add(tempStu.getAverage(), stuMarkInt);
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

	private class FetchListener implements ActionListener {

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
			messagePanel.add(new JLabel("URL:"));
			messagePanel.add(urlField);

			int response = JOptionPane.showConfirmDialog(null, messagePanel,
					"Please Enter module Code and the URL",
					JOptionPane.OK_CANCEL_OPTION);

			if (response == JOptionPane.OK_OPTION) {
				System.out.println("Module code is: " + moduleField.getText());
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

						System.out.println("Emails size is " + emails.size()
								+ " and duration size is " + durations.size());
						for (int i = 0; i < emails.size(); i++) {
							for (Student s : students) {
								if (s.getEmail().equals(emails.get(i))) {
									System.out.println("Found email of "
											+ emails.get(i) + " with duration "
											+ durations.get(i));
									s.addParticipation(moduleField.getText()
											+ " " + durations.get(i) + " ago");
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

	private class CSVExporter implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (examLoaded) {
				new ExamTable().writeCSVFile(tabbedPane);
			}else{
				JOptionPane.showMessageDialog(null, "Please upload a exam.CSV file first");
			}
		}

	}

}
