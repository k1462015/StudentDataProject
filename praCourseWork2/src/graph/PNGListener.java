package graph;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;

public class PNGListener implements ActionListener {
	
	private JFreeChart chart;
	private String modCode;
	
	public PNGListener(JFreeChart freeChart, String module){
		this.chart = freeChart;
		this.modCode = module;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		JMenuItem temp = (JMenuItem) e.getSource();
		
		JFileChooser chooser = new JFileChooser() {
			public void approveSelection(){
				
				File filePath = super.getSelectedFile();
				
				String pathStr = filePath.getPath();
				
				if(!pathStr.endsWith(".png")){//if file path doesn't have ".png"...
					pathStr += ".png"; //...appends ".png" to the end
					filePath = new File(pathStr);//Updates filePath with new path
				}
				
				if (filePath.exists() && getDialogType() == SAVE_DIALOG){//If that file exists
					int decision = JOptionPane.showConfirmDialog(this, "This file already exists. Would you like to overwrite?", "File Exists", 
							JOptionPane.YES_NO_CANCEL_OPTION);
					switch (decision){
					case JOptionPane.YES_OPTION:
							try {
								
									ChartUtilities.saveChartAsPNG(filePath, chart, 600, 400);
									JOptionPane.showMessageDialog(null, "Chart saved as PNG file", "Success", JOptionPane.INFORMATION_MESSAGE);
									this.cancelSelection();
				
							} catch (IOException e1) {
									JOptionPane.showMessageDialog(null, "Failed to export chart", "Error", JOptionPane.ERROR_MESSAGE);
								
						}
						super.approveSelection();//Goes ahead with the file saving
					case JOptionPane.NO_OPTION:
						return;
					case JOptionPane.CLOSED_OPTION:
						return;
					case JOptionPane.CANCEL_OPTION:
						cancelSelection();
						return;
					
					}
				}
				
				try {
					
						ChartUtilities.saveChartAsPNG(filePath, chart, 600, 400);
						JOptionPane.showMessageDialog(null, "Chart saved as PNG file", "Success", JOptionPane.INFORMATION_MESSAGE);
						this.cancelSelection();
					
				
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "Failed to export chart", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		};
		
		chooser.setSelectedFile(new File(modCode+"." + temp.getText().toLowerCase()));
		
		int option = chooser.showSaveDialog(null);
		
	}

}
