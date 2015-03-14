package praCourseWork2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;


public class ScatterPlot extends JFrame {
	
	private XYSeriesCollection dataset;
	private JFreeChart chart;
	private ChartPanel chartPanel;
	private JMenuBar bar;
	private JMenu exportMenu;
	private JMenuItem pngItem;
	private JMenuItem jpgItem;
	
	public ScatterPlot(String appTitle, String chartTitle, String module,XYSeries data){
		
		super(appTitle);
		
		bar = new JMenuBar();
		exportMenu = new JMenu("Export as...");
		
		ExportListener exportListen = new ExportListener();
		
		pngItem = new JMenuItem("png");
		pngItem.addActionListener(exportListen);
		jpgItem = new JMenuItem("jpg");
		jpgItem.addActionListener(exportListen);
		
		exportMenu.add(pngItem); exportMenu.add(jpgItem);
		
		bar.add(exportMenu);
		
		dataset = new XYSeriesCollection();
		dataset.addSeries(data);
		
		chart = ChartFactory.createScatterPlot("Compare to Average", "Average of Student's Marks", module + " mark", dataset);
		chartPanel = new ChartPanel(chart);
		this.add(chartPanel);
		
		setJMenuBar(bar);
		setVisible(true);
		setSize(800,400);
		
	}
	
	private class ExportListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			JMenuItem temp = (JMenuItem) e.getSource();
			
			BufferedImage chartImage = chart.createBufferedImage(600, 400);
			
			JFileChooser chooser = new JFileChooser();
			chooser.showSaveDialog(null);
			
			File filePath = chooser.getSelectedFile();
			
			try {
				ImageIO.write(chartImage, temp.getText(), filePath);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		
	}

}
