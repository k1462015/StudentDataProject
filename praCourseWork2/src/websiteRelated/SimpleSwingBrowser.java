package websiteRelated;

import static javafx.concurrent.Worker.State.FAILED;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.JFXPanel;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebView;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

public class SimpleSwingBrowser extends JFrame {

	private final JFXPanel jfxPanel = new JFXPanel();
	protected WebEngine engine;

	private final JPanel panel = new JPanel(new BorderLayout());
	private final JLabel lblStatus = new JLabel();

	private final JButton btnGo = new JButton("Go");
	private final JTextField txtURL = new JTextField();
	private final JProgressBar progressBar = new JProgressBar();
	private static ArrayList<String> emails;
	private static ArrayList<String> durations;

	public SimpleSwingBrowser(ArrayList<String> emails,ArrayList<String> durations) {
		super();
		initComponents();
		setSortedEmails(emails);
		setSortedDurations(durations);
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

		JButton button = new JButton("FETCH DATA");
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				readDocument();

			}

		});
		add(button, BorderLayout.SOUTH);

		getContentPane().add(panel);

		setPreferredSize(new Dimension(1024, 600));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		pack();

	}

	public static void printDocument(Document doc, OutputStream out)
			throws IOException, TransformerException {
		System.out.println("Retreiving document data");
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
		transformer.setOutputProperty(OutputKeys.METHOD, "xml");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		transformer.setOutputProperty(
				"{http://xml.apache.org/xslt}indent-amount", "4");

		// transformer.transform(new DOMSource(doc),
		// new StreamResult(new OutputStreamWriter(out, "UTF-8")));

		StringWriter writer = new StringWriter();
		transformer.transform(new DOMSource(doc), new StreamResult(writer));
		String[] output = writer.toString().split("\\n");
		ArrayList<String> emails1 = new ArrayList<String>();
		ArrayList<String> durations1 = new ArrayList<String>();
		for (int i = 0; i < output.length; i++) {
			 if(output[i].contains("@") && (output[i].contains(".com") || output[i].contains(".co.uk"))){
				 emails1.add(output[i]);
			 }

			if (output[i].contains("mins")||output[i].contains("secs")||output[i].contains("hours")||output[i].contains("days")) {
				durations1.add(output[i]);
			}

		}

		WebsiteSorter ws = new WebsiteSorter(emails1,durations1);
		websiteSorter(ws);


	}
	
	public static void websiteSorter(WebsiteSorter ws){
		emails = ws.getSortedEmails();
		durations = ws.getSortedDurations();
	}
	
	public void setSortedEmails(ArrayList<String> emails){
		emails = this.emails;
	}
	
	public void setSortedDurations(ArrayList<String> durations){
		durations = this.durations;
	}

	private void createScene() {

		Platform.runLater(new Runnable() {
			@Override
			public void run() {

				WebView view = new WebView();
				engine = view.getEngine();

				engine.titleProperty().addListener(
						new ChangeListener<String>() {
							@Override
							public void changed(
									ObservableValue<? extends String> observable,
									String oldValue, final String newValue) {
								SwingUtilities.invokeLater(new Runnable() {
									@Override
									public void run() {
										SimpleSwingBrowser.this
												.setTitle(newValue);
									}
								});
							}
						});

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

				engine.locationProperty().addListener(
						new ChangeListener<String>() {
							@Override
							public void changed(
									ObservableValue<? extends String> ov,
									String oldValue, final String newValue) {
								SwingUtilities.invokeLater(new Runnable() {
									@Override
									public void run() {
										txtURL.setText(newValue);
									}
								});
							}
						});

				engine.getLoadWorker().workDoneProperty()
						.addListener(new ChangeListener<Number>() {
							@Override
							public void changed(
									ObservableValue<? extends Number> observableValue,
									Number oldValue, final Number newValue) {
								SwingUtilities.invokeLater(new Runnable() {
									@Override
									public void run() {
										progressBar.setValue(newValue
												.intValue());
									}
								});
							}
						});

				engine.getLoadWorker().exceptionProperty()
						.addListener(new ChangeListener<Throwable>() {

							public void changed(
									ObservableValue<? extends Throwable> o,
									Throwable old, final Throwable value) {
								if (engine.getLoadWorker().getState() == FAILED) {
									SwingUtilities.invokeLater(new Runnable() {
										@Override
										public void run() {
											JOptionPane
													.showMessageDialog(
															panel,
															(value != null) ? engine
																	.getLocation()
																	+ "\n"
																	+ value.getMessage()
																	: engine.getLocation()
																			+ "\nUnexpected error.",
															"Loading error...",
															JOptionPane.ERROR_MESSAGE);
										}
									});
								}
							}
						});

				jfxPanel.setScene(new Scene(view));
			}
		});

	}

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

	public void readDocument() {
		try {
			printDocument(engine.getDocument(), System.out);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}