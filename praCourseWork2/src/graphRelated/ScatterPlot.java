package graphRelated;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
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
	private String moduleCode;
	
	public ScatterPlot(String appTitle, String chartTitle, String module,XYSeries data){
		
		super(appTitle);
		moduleCode = module;
		bar = new JMenuBar();
		exportMenu = new JMenu("Export as...");
		
		ExportListener exportListen = new ExportListener();
		
		pngItem = new JMenuItem("PNG");
		pngItem.addActionListener(exportListen);
		
		jpgItem = new JMenuItem("JPG");
		jpgItem.addActionListener(exportListen);
		
		exportMenu.add(pngItem); exportMenu.add(jpgItem);
		
		bar.add(exportMenu);
		
		dataset = new XYSeriesCollection();
		dataset.addSeries(data);
		
		chart = ChartFactory.createScatterPlot("Compare to Average", "Average of Student's Marks", moduleCode + " mark", dataset);
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
			
			
			chooser.setSelectedFile(new File(moduleCode+"." + temp.getText().toLowerCase()));
			chooser.showSaveDialog(null);
			
			File filePath = chooser.getSelectedFile();
			
			
			String pathStr = filePath.getPath();
			//System.out.println(filePath.getPath());
			if (temp.getText().equals("PNG")){
				
				if(!pathStr.endsWith(".png")){//if file path doesn't have ".png"...
					pathStr += ".png"; //...appends ".png" to the end
					filePath = new File(pathStr);//Updates filePath with new path
				}
				
				try {
					ChartUtilities.saveChartAsPNG(filePath, chart, 600, 400);
					JOptionPane.showMessageDialog(null, "Chart saved as PNG file", "Success", JOptionPane.INFORMATION_MESSAGE);
				} catch (IOException e1) {
					//JOptionPane.showMessageDialog(null, "Failed to export chart", "Error", JOptionPane.ERROR_MESSAGE);
					//e1.printStackTrace();
				}
			} else{
				
				if(!pathStr.endsWith(".jpg")){//if file path doesn't have ".jpg"...
					pathStr += ".jpg"; 
					filePath = new File(pathStr);
				}
				
				try {
					ChartUtilities.saveChartAsJPEG(filePath, chart, 600, 400);
					JOptionPane.showMessageDialog(null, "Chart saved as JPG file", "Success", JOptionPane.INFORMATION_MESSAGE);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "Failed to export chart", "Error", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				}
			}
			
			/*try {
				ImageIO.write(chartImage, temp.getText(), filePath);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}*/
			
		}
		
	}

}
