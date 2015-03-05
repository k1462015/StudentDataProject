package praCourseWork2;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;

public class Email extends JFrame {
	private JPanel main;
	private JList list;
	public Email(){
		setVisible(true);
		main = new JPanel();
		setSize(550, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main.setLayout(new BorderLayout());
	}
}
