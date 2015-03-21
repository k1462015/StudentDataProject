package keatsScraper;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.JFXPanel;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebView;

import javax.swing.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static javafx.concurrent.Worker.State.FAILED;
/**
 * SwingBrowser to display website
 * @author TMH
 *
 */
public class Browser extends JPanel {

	private final JFXPanel jfxPanel = new JFXPanel();
	private WebEngine engine;

	private final JPanel panel = new JPanel(new BorderLayout());
	private final JLabel lblStatus = new JLabel();

	private final JButton btnGo = new JButton("Go");
	private final JTextField txtURL = new JTextField();
	private final JProgressBar progressBar = new JProgressBar();

	private static ArrayList<String> emails;
	private static ArrayList<String> durations;
	/**
	 * Displays browser
	 */
	public Browser() {
		super();
		setLayout(new BorderLayout());
		initComponents();
	}

	private void initComponents() {
		createScene();

		ActionListener al = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loadURL(txtURL.getText());
			}
		};

		btnGo.addActionListener(al);
		txtURL.addActionListener(al);

		progressBar.setPreferredSize(new Dimension(150, 18));
		progressBar.setStringPainted(true);

		JPanel topBar = new JPanel(new BorderLayout(5, 0));
		topBar.setBorder(BorderFactory.createEmptyBorder(3, 5, 3, 5));
		topBar.add(txtURL, BorderLayout.CENTER);
		topBar.add(btnGo, BorderLayout.EAST);

		JPanel statusBar = new JPanel(new BorderLayout(5, 0));
		statusBar.setBorder(BorderFactory.createEmptyBorder(3, 5, 3, 5));
		statusBar.add(lblStatus, BorderLayout.CENTER);
		statusBar.add(progressBar, BorderLayout.EAST);

		panel.add(topBar, BorderLayout.NORTH);
		panel.add(jfxPanel, BorderLayout.CENTER);
		panel.add(statusBar, BorderLayout.SOUTH);

		this.add(panel);

		setPreferredSize(new Dimension(1024, 600));

	}

	private void createScene() {

		Platform.runLater(new Runnable() {
			@Override
			public void run() {

				WebView view = new WebView();
				engine = view.getEngine();

				engine.setOnStatusChanged(new EventHandler<WebEvent<String>>() {
					@Override
					public void handle(final WebEvent<String> event) {
						SwingUtilities.invokeLater(new Runnable() {
							@Override
							public void run() {
								lblStatus.setText(event.getData());
							}
						});
					}
				});

				engine.locationProperty().addListener(new ChangeListener<String>() {
					@Override
					public void changed(ObservableValue<? extends String> ov,String oldValue, final String newValue) {
						SwingUtilities.invokeLater(new Runnable() {
							@Override
							public void run() {
								txtURL.setText(newValue);
							}
						});
					}
				});

				engine.getLoadWorker().workDoneProperty().addListener(new ChangeListener<Number>() {
					@Override
					public void changed(ObservableValue<? extends Number> observableValue,Number oldValue, final Number newValue) {
						SwingUtilities.invokeLater(new Runnable() {
							@Override
							public void run() {
								progressBar.setValue(newValue
										.intValue());
							}
						});
					}
				});

				engine.getLoadWorker().exceptionProperty().addListener(new ChangeListener<Throwable>() {

					@Override
					public void changed(ObservableValue<? extends Throwable> o,Throwable old, final Throwable value) {
						if (engine.getLoadWorker().getState() == FAILED) {
							SwingUtilities.invokeLater(new Runnable() {
								@Override
								public void run() {
									JOptionPane.showMessageDialog(panel,	(value != null) ? engine.getLocation()+ "\n"+ value.getMessage(): engine.getLocation()+ "\nUnexpected error.","Loading error...",JOptionPane.ERROR_MESSAGE);
								}
							});
						}
					}
				});

				jfxPanel.setScene(new Scene(view));
			}
		});
	}
	
	/**
	 * Loads url into browser
	 * @param url - URL of website
	 */
	public void loadURL(final String url) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				String tmp = toURL(url);

				if (tmp == null) {
					tmp = toURL("http://" + url);
				}

				engine.load(tmp);
			}
		});
	}

	private static String toURL(String str) {
		try {
			return new URL(str).toExternalForm();
		} catch (MalformedURLException exception) {
			return null;
		}
	}
	/**
	 * Reads document and filters out email and duration times
	 * @param doc - currentPage
	 * @throws IOException - Throws IOException if can't read file
	 * @throws TransformerException - Throws if can't transform document 
	 */
	public static void printDocument(Document doc)throws IOException, TransformerException {
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
		transformer.setOutputProperty(OutputKeys.METHOD, "xml");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

		StringWriter writer = new StringWriter();
		transformer.transform(new DOMSource(doc), new StreamResult(writer));
		
		String[] output = writer.toString().split("\\n");
		emails = new ArrayList<String>();
		durations = new ArrayList<String>();
		//Loop through all lines of text taken from website
		for (int i = 0; i < output.length; i++) {
			//Checks if line contains email
			if (output[i].contains("@")&& (output[i].contains(".com") || output[i].contains(".co.uk"))) {
				emails.add(output[i].substring(
						output[i].lastIndexOf("c3\">") + 4,
						output[i].indexOf("</TD>")));
			}
			//Checks if lines contains access time
			if ((output[i].contains("mins") || output[i].contains("secs")|| output[i].contains("hours")
				|| output[i].contains("days") || output[i].contains("now")|| output[i].contains("hour") || output[i].contains("day"))
					&& output[i].contains("participants")) {
				durations.add(output[i].substring(output[i].lastIndexOf("c6\">") + 4,output[i].indexOf("</TD>")));
			}

		}

	}
	/**
	 * Turns website into a document and then reads text
	 */
	public void readDocument() {
		try {
			printDocument(engine.getDocument());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Returns ArrayList of all emails fetched from website
	 * @return emails - ArrayList of emails
	 */
	public ArrayList<String> getEmails() {
		return emails;
	}
	/**
	 * Returns ArrayList of all access times from website
	 * @return - Arraylist of access times
	 */
	public ArrayList<String> getAccessTimes() {
		return durations;
	}

}
