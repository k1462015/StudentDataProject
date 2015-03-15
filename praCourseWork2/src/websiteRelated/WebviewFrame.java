package websiteRelated;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;


public class WebviewFrame extends JFrame {
	private String url;
	public Browser browser;
	public JButton btnFetch;
	
	public WebviewFrame(String url){
		super();
		this.url = url;
		initComponents();
	}
	
	public void initComponents(){
		JPanel bottomPane = new JPanel();
		bottomPane.setLayout(new BorderLayout());
		add(bottomPane,BorderLayout.SOUTH);
		
		JPanel boxIns = new JPanel();
		boxIns.setLayout(new BoxLayout(boxIns,BoxLayout.PAGE_AXIS));
		
		
		JLabel title = new JLabel("Instructions");
		title.setFont(new Font("Century Gothic",Font.BOLD,30));
		
		Font font = new Font("Century Gothic",Font.PLAIN,20);
		
		JLabel instr1 = new JLabel("1.Log into Keats");
		instr1.setFont(font);
		JLabel instr2 = new JLabel("2.Go to neccasary page");
		instr2.setFont(font);
		JLabel instr3 = new JLabel("3.Click fetch data once page is 100% loaded");
		instr3.setFont(font);
		boxIns.add(title);
		boxIns.add(instr1);
		boxIns.add(instr2);
		boxIns.add(instr3);
		
		
		bottomPane.add(boxIns,BorderLayout.WEST);
		
		btnFetch = new JButton("Fetch data");
		btnFetch.setFont(new Font("Century Gothic",Font.BOLD,60));
		btnFetch.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				browser.readDocument();

			}

		});
		bottomPane.add(btnFetch,BorderLayout.CENTER);
		
        SwingUtilities.invokeLater(new Runnable() {

            @Override
			public void run() {
                browser = new Browser();
                add(browser,BorderLayout.CENTER);
                browser.loadURL(url);
           }     
       });
        
        bottomPane.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.red), bottomPane.getBorder()));
		
		
		//Default stuff
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setVisible(true);
		setSize(900,700);
		
	}
	
	public ArrayList<String> getEmails(){
		return browser.getEmails();
	}
	
	public ArrayList<String> getDurations(){
		return browser.getDurations();
	}
	
	
	
 	
	public static void main(String [] args){
		WebviewFrame wf = new WebviewFrame("https://login-keats.kcl.ac.uk/?errorcode=4");
	}

}
