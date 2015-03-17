package email;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.font.TextAttribute;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
/**
 * JFrame containing help for email settings
 * @author TMH
 *
 */
public class HelpFrame extends JFrame {

	public HelpFrame() {
		super("Help");
		initUi();
	}
	
	/**
	 * Adds all required components
	 */
	public void initUi() {
		JPanel main = new JPanel();
		main.setLayout(new BoxLayout(main, BoxLayout.PAGE_AXIS));
		main.setMaximumSize(new Dimension(500,500));

		Font titleFonts = new Font("Calibri", Font.BOLD, 32);
		Font subFont = new Font("Calibri", Font.PLAIN, 21);
		Font paraFonts = new Font("Calibri", Font.PLAIN, 15);

		Map attributes = titleFonts.getAttributes();
		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);

		Map subattributes = subFont.getAttributes();
		subattributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);

		JLabel settings = new JLabel("Settings");
		settings.setFont(titleFonts.deriveFont(attributes));
		main.add(settings);

		JLabel serverName = new JLabel("Server Name");
		serverName.setFont(subFont.deriveFont(subattributes));
		// serverName.setFont(subFont);
		main.add(serverName);

		JLabel serverNameDes = new JLabel(
				"Most server names are written in the form (mail.domain.com)");
		serverNameDes.setFont(paraFonts);
		main.add(serverNameDes);

		JLabel port = new JLabel("Port");
		port.setFont(subFont.deriveFont(subattributes));
		main.add(port);

		JLabel portDes = new JLabel(
				"The port number identifies what type of port it is. E.G port 465 for SSMTP port");
		portDes.setFont(paraFonts);
		main.add(portDes);

		JLabel secAndAuthen = new JLabel("Security & Authentication");
		secAndAuthen.setFont(titleFonts.deriveFont(attributes));
		main.add(secAndAuthen);

		JLabel authenMethod = new JLabel("Authentication Mehtod");
		authenMethod.setFont(subFont.deriveFont(subattributes));
		// serverName.setFont(subFont);
		main.add(authenMethod);

		JLabel authenDes = new JLabel(
				"Default authentication method used here is StartTLS ");
		authenDes.setFont(paraFonts);
		main.add(authenDes);

		JLabel userName = new JLabel("Username");
		userName.setFont(subFont.deriveFont(subattributes));
		main.add(userName);

		JLabel userNameDes = new JLabel("A username is your email address password. An example could be tmh@mailanator.com");
		userNameDes.setFont(paraFonts);
		main.add(userNameDes);

		// Default JFrame stuff
		add(main);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		pack();
	}

}
