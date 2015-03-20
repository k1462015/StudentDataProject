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
import java.io.FileNotFoundException;
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
	private boolean examLoaded, anonLoaded;
	private File settingsFile;
	private ArrayList<String> emails, lastAccess;
	private DisplayPopUpFrame display = null;

	/**
	 * Constructs MainFrame with student JList and constructs UI
	 */
	public MainFrame() {
		// Initializes frame and sets title to team name
		super("PRA Coursework - TMH");
		examLoaded = false;
		anonLoaded = false;
		settingsFile = new Settings().findSettingsFile();
		InitUI();

	}

	private void InitUI() {
		// Adds MenuBar
		addMenuBar();

		////Fetches Students and creates JList
		students = new ArrayList<Student>();
		ServerConnect sc = new ServerConnect(students);
		JList list = createJList(students);
		JTextField search = new JTextField(22);
		search.addKeyListener(new searchListener());
		// Cell formatting
		list.setFixedCellHeight(30);
		list.setFixedCellWidth(260);

		//Initializes required fields
		assesments = new ArrayList<Assessment>();
		tabbedPane = new JTabbedPane();

		//Adds search and list to LeftPane Panel
		JPanel leftPane = new JPanel();
		leftPane.setLayout(new BorderLayout());

		leftPane.add(search, BorderLayout.NORTH);
		leftPane.add(new JScrollPane(list), BorderLayout.CENTER);

		add(leftPane, BorderLayout.WEST);
		add(tabbedPane, BorderLayout.CENTER);

		//Default JFrame settings
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
		JMenuItem loadExam = new JMenuItem("Load Exam Results");
		loadExam.setToolTipText("Loads exam results from CSV file");
		JMenuItem loadAnon = new JMenuItem("Load anonymous marking codes");
		loadAnon.setToolTipText("Loads anonymous marking codes from CSV file");
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
		settings.setToolTipText("Configure SMTP email settings");
		JMenuItem emailStudent = new JMenuItem("Send Email");
		emailStudent.setToolTipText("Email results to students");
		JMenuItem compareAverage = new JMenuItem("Compare to Average");
		compareAverage
				.setToolTipText("Generates a Scatter Plot Graph for the selected tab");
		JMenuItem fetchPart = new JMenuItem("Fetch Participation");
		fetchPart.setToolTipText("Fetches participation data from KEATS");

		settings.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					new EmailSettingsFrame();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}

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
		pdf.setToolTipText("Saves PDF with all existing data to Desktop");
		JMenuItem exportCSV = new JMenuItem("Export selected table");
		exportCSV
				.setToolTipText("Exports current selected table to a CSV file");
		pdf.addActionListener(new PDFListener());
		exportCSV.addActionListener(new CSVExporter());
		extra.add(pdf);
		extra.add(exportCSV);

	}

	private JList createJList(ArrayList<Student> students) {
		DefaultListModel defListMod = new DefaultListModel();
		// Goes through arraylist of Student objects, calls toString and adds
		// the Strings to DefaultListModel (DLM)
		for (Student s : students) {
			defListMod.addElement(s.toString());
		}
		// Adds model to JList
		list = new JList(defListMod);
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					String selectedItem = list.getSelectedValue().toString();
					generateDisplayPopUp(selectedItem);
				}
			}
		});

		// Sets selection colour of JList
		list.setSelectionBackground(Color.black);
		list.setSelectionForeground(Color.WHITE);
		list.setFont(new Font("Calibri", Font.BOLD, 20));
		return list;
	}

	private Student findStudent(String studentIdentity,
			ArrayList<Student> students) {
		Student found = null;
		for (int i = 0; i < students.size(); i++) {
			//If student Number is used as identity
			if (!studentIdentity.substring(studentIdentity.length() - 1,
					studentIdentity.length()).equals(")")) {
				if (((students.get(i).getStudentNumber() + "")
						.equals(studentIdentity))) {
					found = students.get(i);
				}
			}
			//If toString used as identity
			if (students.get(i).toString().equals(studentIdentity)) {
				found = students.get(i);
			}
		}
		// Returns found student
		return found;

	}

	private void tabbedPane() {
		int count = 0;
		boolean checkExists = false;
		// loops through each assesment and creates a tab and a table for that assessment
		for (Assessment a : assesments) {
			String name = (a.getResultAtIndex(count).getModuleCode()) + " "
					+ a.getResultAtIndex(count).getAssessment() + " "
					+ a.getResultAtIndex(count).getYear();
			//Loops through current tabs and checks if tab already exists
			for (int i = 0; i < tabbedPane.getTabCount(); i++) {
				if (tabbedPane.getTitleAt(i).equals(name)) {
					checkExists = true;
				}
			}
			//If tab doesn't exits, creates new tab
			if (!checkExists) {
				count++;
				ExamTable jtable = new ExamTable(a, assesments, students);
				jtable.getTable().addMouseListener(new NameCellListener());
				tabbedPane.addTab(name, new JScrollPane(jtable.getTable()));
			} else {
				JOptionPane.showMessageDialog(null,"You have already uploaded this CSV file before.\nPlease upload a different CSV file.");
			}

		}
		assesments.clear();
	}

	private void generateDisplayPopUp(String studentIdentity) {
		//First finds corresponding student
		Student findStudent = findStudent(studentIdentity, students);
		if (findStudent != null) {
			if (display != null) {
				// This checks if current display Popup is already being displayed
				// Disposes current display popup if already present
				if (display.getName().equals(findStudent.getName())) {
					display.dispose();
				}
			}
			display = new DisplayPopUpFrame(findStudent);
		}
	}

	private class searchListener extends KeyAdapter {
		@Override
		public void keyReleased(KeyEvent e) {
			JTextField search = (JTextField) e.getSource();
			// Clear the list
			DefaultListModel listModel = (DefaultListModel) list.getModel();
			listModel.removeAllElements();
			//Get whatever user types into text field
			String buffer = search.getText();
			//Adds all matching students with search query in serachStudent ArrayList
			for (Student i : students) {
				if (i.getName().toLowerCase().contains(buffer.toLowerCase())|| (i.getStudentNumber() + "").contains(buffer)) {
					listModel.addElement(i);
				}
			}

		}

	}

	private class LoadAnonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			//If valid Anon code is loaded sets anonLoaded boolean to true
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
				//Creates table and adds to Tabbed Pane if returns true
				if (new CSVLoader().loadExamCSV(assesments)) {
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
			if (examLoaded == true) {
				//If settings file has been loaded shows sendEmailFrame
				if (!(settingsFile == null)) {
					new SendEmailFrame(students, settingsFile);
				} else {
					int confirm = JOptionPane.showConfirmDialog(null,"Email Settings have not been configured."+ "\nDefault Outlook Office 365 settings will be used."+ "\nDo you wish to continue?","Email to Students",JOptionPane.YES_NO_CANCEL_OPTION);
					if (confirm == JOptionPane.YES_OPTION) {
						new SendEmailFrame(students, settingsFile);
					}
				}
			} else {
				JOptionPane.showMessageDialog(null,"Please upload an Exam.CSV file in order to send an email.");
			}

		}

	}

	private class PDFListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			//Grabs all current students loaded and generates PDF
			try {
				new PDFGenerator().addDataPdf(students);
			} catch (DocumentException | IOException e1) {
				e1.printStackTrace();
			}
			JOptionPane.showMessageDialog(null,students.size()+ " Student information succesfully succesfully imported to PDF in desktop");
		}

	}

	private class NameCellListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			JTable table = (JTable) e.getSource();
			if (e.getClickCount() == 2) {
				int row = table.getSelectedRow();
				int column = table.getSelectedColumn();
				//Checks if column is student name column
				if (column == 0) {
					// Create Display PopUp
					String selectedItem = (String) table.getValueAt(row,column + 1);
					if (!selectedItem.substring(0, 1).equals("#")) {
						generateDisplayPopUp(selectedItem);
					}

				}
			}

		}

	}

	private class GraphListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			//If an exam.csv has been uploaded, will generate a graph
			if (examLoaded == true) {
				// Gets current tab from Tabbed Pane
				JScrollPane currentScrollPane = (JScrollPane) tabbedPane.getComponentAt(tabbedPane.getSelectedIndex());
				JViewport viewport = currentScrollPane.getViewport();
				JTable currentTable = (JTable) viewport.getView();

				//Holds data from graph
				XYSeries data = new XYSeries("Student");
				int numOfRecords = currentTable.getRowCount();

				//Loops through the rows,matching students and calculate average
				for (int i = 0; i < numOfRecords; i++) {
					String tempCandKey = (String) currentTable.getValueAt(i, 1);
					Student tempStu = findStudent(tempCandKey, students);
					//Finds matching student from row, plots student average against mark of selected Module
					if (!(tempStu == null)) {
						int stuMarkInt = (Integer) currentTable.getValueAt(i, 4);
						tempStu.calcAverage();
						data.add(tempStu.getAverage(), stuMarkInt);
					}
				}
				
				//Generates scatter plot
				String modCode = (String) currentTable.getValueAt(0, 3);
				ScatterPlot scatter = new ScatterPlot("Graph","Comparison of Average in Assessment", modCode, data);

			} else {
				JOptionPane.showMessageDialog(rootPane,"You need to load an exam results file, before you can create the chart");
			}

		}

	}

	private class FetchListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			emails = new ArrayList<String>();
			lastAccess = new ArrayList<String>();
			
			JTextField moduleField = new JTextField(6);
			JTextField urlField = new JTextField(20);
			
			//Creates JPanel for entry of module code and URL to keats
			JPanel messagePanel = new JPanel();
			messagePanel.setLayout(new BoxLayout(messagePanel,BoxLayout.PAGE_AXIS));
			messagePanel.add(new JLabel("Module:"));
			messagePanel.add(moduleField);
			messagePanel.add(new JLabel("URL:"));
			messagePanel.add(urlField);

			int response = JOptionPane.showConfirmDialog(null, messagePanel,"Please Enter module Code and the URL",JOptionPane.OK_CANCEL_OPTION);

			if (response == JOptionPane.OK_OPTION) {
				//Re-opens dialogue box if no URL or module is entered
				while (moduleField.getText().equals("")&& urlField.getText().equals("")) {
					response = JOptionPane.showConfirmDialog(null,messagePanel,"Please Enter module Code and the URL",JOptionPane.OK_CANCEL_OPTION);
				}
				WebviewFrame wb = new WebviewFrame(urlField.getText());
				wb.getButton().addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						wb.getBrowser().readDocument();
						emails = wb.getEmails();
						lastAccess = wb.getAccessTimes();
						for (int i = 0; i < emails.size(); i++) {
							for (Student s : students) {
								if (s.getEmail().equals(emails.get(i))) {
									s.addLastAccess(moduleField.getText() + " "+ lastAccess.get(i) + " ago");
								}
							}
						}
						JOptionPane.showMessageDialog(null,emails.size()+ " participation records were succesfully imported");
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
				new ExamTable().exportCSVToFile(tabbedPane);
			} else {
				JOptionPane.showMessageDialog(null,"Please upload a exam.CSV file first");
			}
		}

	}

}
