package student;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;
/**
 * Popup frame containing student info
 * @author TMH
 *
 */
public class DisplayPopUpFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private JLabel name;
	private JLabel emailAddress;
	private JLabel studentNumber;
	private JLabel tutor;
	private JLabel results;
	private JPanel main;
	private JPanel bottom;
	private ArrayList<String> marks;
	private ArrayList<String> participation;

	public DisplayPopUpFrame(Student student) {
		super(student.getName() + " - Information Card");
		// Initialises all required fields with constructor arguments
		this.name = new JLabel(student.getName());
		this.emailAddress = new JLabel(student.getEmail());
		this.studentNumber = new JLabel("Student No. :   "
				+ student.getStudentNumber());
		this.tutor = new JLabel("Tutor:     " + student.getTutor());
		this.marks = student.getAssessMarks();
		participation = student.getParticipationArray();

		// Calls Popup method to make required JPanel
		makePopUp();

	}
	
	private void makePopUp() {
		// Initialises required JPanels
		main = new JPanel();
		bottom = new JPanel();

		// Sets JPanel to BoxLayout
		// setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		main.setLayout(new BorderLayout());

		// Sets font size
		name.setFont(this.name.getFont().deriveFont(30.0f));
		// this.emailAddress.setFont (this.emailAddress.getFont ().deriveFont
		// (40.0f));
		studentNumber.setFont(this.studentNumber.getFont().deriveFont(20.0f));
		tutor.setFont(this.tutor.getFont().deriveFont(20.0f));

		// Aligns all JLabels
		name.setHorizontalAlignment(SwingConstants.CENTER);
		emailAddress.setHorizontalAlignment(SwingConstants.CENTER);
		studentNumber.setHorizontalAlignment(SwingConstants.LEFT);
		tutor.setHorizontalAlignment(SwingConstants.LEFT);

		// Sets email address to italics
		emailAddress.setFont(new Font("Arial", Font.ITALIC, 30));

		// Adds sNumber and tutor email to bottom JPanel
		bottom.setLayout(new BoxLayout(bottom, BoxLayout.PAGE_AXIS));
		JPanel sNumber = new JPanel(new BorderLayout());
		studentNumber.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
		sNumber.add(this.studentNumber, BorderLayout.WEST);
		JPanel tutorPanel = new JPanel(new BorderLayout());
		this.tutor.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
		tutorPanel.add(this.tutor, BorderLayout.WEST);
		bottom.add(sNumber);
		bottom.add(tutorPanel);

		// Gets participation data
		for (String s : participation) {
			JLabel temp = new JLabel("Last Access: " + s);
			temp.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
			temp.setFont(temp.getFont().deriveFont(18.0f));
			JPanel tempHolder = new JPanel(new BorderLayout());
			tempHolder.add(temp, BorderLayout.CENTER);
			bottom.add(tempHolder);
		}

		if (!marks.isEmpty()) {
			// Sets font of results label and adds it to the window
			results = new JLabel("Results:", SwingConstants.CENTER);
			results.setFont(this.results.getFont().deriveFont(20.0f));
			JPanel tResult = new JPanel(new BorderLayout());
			tResult.add(results, BorderLayout.CENTER);
			bottom.add(tResult);

			// Loops through each of the marks and adds them to the bottom of
			// the panel
			for (String m : marks) {
				JLabel temp = new JLabel(m);
				temp.setFont(temp.getFont().deriveFont(18.0f));
				temp.setHorizontalAlignment(SwingConstants.CENTER);
				JPanel tempHolder = new JPanel(new BorderLayout());
				tempHolder.add(temp, BorderLayout.CENTER);
				bottom.add(tempHolder);
			}
		}

		// Adds all labels to the main Panel
		main.add(this.name, BorderLayout.NORTH);
		main.add(this.emailAddress, BorderLayout.CENTER);
		main.add(bottom, BorderLayout.SOUTH);

		// Adds main panel to Main JFrame
		add(main);

		// Required JFrame
		getRootPane().setBorder(new LineBorder(Color.BLACK, 5));
		setBackground(Color.white);
		setSize(550, 300);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);

	}
	
	/**
	 * return - name of student in display pop up
	 */
	public String getName(){
		return name.getText();
	}

}
