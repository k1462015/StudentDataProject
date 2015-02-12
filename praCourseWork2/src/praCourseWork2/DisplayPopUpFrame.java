package praCourseWork2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class DisplayPopUpFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private JLabel name;
	private JLabel emailAddress;
	private JLabel studentNumber;
	private JLabel tutor;
	private JPanel main;
	private JPanel bottom;

	public DisplayPopUpFrame(Student student) {
		super("Student Information - Display");
		// Initialises all required fields with constructor arguments
		this.name = new JLabel(student.name);
		this.emailAddress = new JLabel(student.email);
		this.studentNumber = new JLabel("Student No. :   "
				+ student.studentNumber);
		this.tutor = new JLabel("Tutor:     " + student.tutor);

		// Initialises requried JPanels
		main = new JPanel();
		bottom = new JPanel();

		// Required JFrame
		setVisible(true);
		setSize(700, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		name.setFont (this.name.getFont ().deriveFont (64.0f));
		//this.emailAddress.setFont (this.emailAddress.getFont ().deriveFont (40.0f));
		studentNumber.setFont (this.studentNumber.getFont ().deriveFont (32.0f));
		tutor.setFont (this.tutor.getFont ().deriveFont (32.0f));

		//Aligns all JLabels
		name.setHorizontalAlignment(SwingConstants.CENTER);
		emailAddress.setHorizontalAlignment(SwingConstants.CENTER);
		studentNumber.setHorizontalAlignment(SwingConstants.LEFT);
		tutor.setHorizontalAlignment(SwingConstants.LEFT);

		//Sets email address to italics
		emailAddress.setFont(new Font("Arial",Font.ITALIC,40));
		
		//Adds sNumber and tutor email to bottom JPanel
		bottom.setLayout(new BoxLayout(bottom,BoxLayout.PAGE_AXIS));
		bottom.add(this.studentNumber);
		bottom.add(this.tutor);
		
		//Adds all labels to the main Panel
		main.add(this.name, BorderLayout.NORTH);
		main.add(this.emailAddress, BorderLayout.CENTER);
		main.add(bottom, BorderLayout.SOUTH);
		
		//Adds main panel to Main JFrame
		add(main);


	}

}
