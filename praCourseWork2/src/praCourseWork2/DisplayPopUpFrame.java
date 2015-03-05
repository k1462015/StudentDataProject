package praCourseWork2;

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

public class DisplayPopUpFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	protected JLabel name;
	private JLabel emailAddress;
	private JLabel studentNumber;
	private JLabel tutor;
	private JLabel results;
	private JPanel main;
	private JPanel bottom;
	private ArrayList<String> marks;

	public DisplayPopUpFrame(Student student) {
		super(student.getName()+" - Information Card");
		// Initialises all required fields with constructor arguments
		this.name = new JLabel(student.name);
		this.emailAddress = new JLabel(student.email);
		this.studentNumber = new JLabel("Student No. :   "
				+ student.studentNumber);
		this.tutor = new JLabel("Tutor:     " + student.tutor);
		results = new JLabel("Results:");
		this.marks = student.assessMarks;

		// Initialises required JPanels
		main = new JPanel();
		bottom = new JPanel();

		// Required JFrame
		setVisible(true);
		setSize(700, 500);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getRootPane().setBorder(
				BorderFactory.createMatteBorder(4, 4, 4, 4, Color.RED));
		setBackground(Color.white);

		// Calls Popup method to make required JPanel
		makePopUp();

		// Packs all panels to make neater
		pack();

	}

	public void makePopUp() {
		//Sets JPanel to BoxLayout
		//setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		main.setLayout(new BorderLayout());
		
		//Sets font size
		name.setFont (this.name.getFont ().deriveFont (30.0f));
		//this.emailAddress.setFont (this.emailAddress.getFont ().deriveFont (40.0f));
		studentNumber.setFont (this.studentNumber.getFont ().deriveFont (20.0f));
		tutor.setFont (this.tutor.getFont ().deriveFont (20.0f));

		//Aligns all JLabels
		name.setHorizontalAlignment(SwingConstants.CENTER);
		emailAddress.setHorizontalAlignment(SwingConstants.CENTER);
		studentNumber.setHorizontalAlignment(SwingConstants.LEFT);
		tutor.setHorizontalAlignment(SwingConstants.LEFT);

		//Sets email address to italics
		emailAddress.setFont(new Font("Arial",Font.ITALIC,30));
		
		//Adds sNumber and tutor email to bottom JPanel
		bottom.setLayout(new BoxLayout(bottom,BoxLayout.PAGE_AXIS));
		bottom.add(this.studentNumber);
		bottom.add(this.tutor);
		
		if (!marks.isEmpty()){
			//Sets font of results label and adds it to the window
			results.setFont(this.results.getFont().deriveFont(20.0f));
			bottom.add(results);
			
			//Loops through each of the marks and adds them to the bottom of the panel
			for(String m : marks){
				JLabel temp = new JLabel("             " + m);
				temp.setFont(temp.getFont().deriveFont(18.0f));
				
				bottom.add(temp);
			}
		}
		
		//Adds all labels to the main Panel
		main.add(this.name, BorderLayout.NORTH);
		main.add(this.emailAddress, BorderLayout.CENTER);
		main.add(bottom, BorderLayout.SOUTH);
		
		//Adds main panel to Main JFrame
		add(main);


	}

}
