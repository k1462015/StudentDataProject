package extraFeatures;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;

import student.Student;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
 
/**
 * PDF Generator for student data
 */
public class PDFGenerator { 
    /**
     * Creates a pdf file to write to
     * @param filepath - directory to save to
     */
    private void createPDFFile(String filepath){
    	 try {
            OutputStream file = new FileOutputStream(new File(filepath));
 
            Document document = new Document();
            PdfWriter.getInstance(document, file);
 
            document.open();
            document.add(new Paragraph("Created new PDF"));
            document.add(new Paragraph(new Date().toString()));
 
            document.close();
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
   
 
    /**
     * Adds data to PDF document
     * @param students - ArrayList of students
     * @throws  DocumentException - If unable to write to document
     * @throws  IOException - If unable to read file
     */
    public void addDataPdf(ArrayList<Student> students)
    	throws DocumentException, IOException {
			//Checks users operating system then creates PDF in desktop
			String filename = System.getProperty("user.home") + "/Desktop/"+"studentInfo.pdf";
			System.getProperty("os.name");
			if(System.getProperty("os.name").equals("Mac OS X")){
				filename = System.getProperty("user.home")+"/Desktop/"+"studentInfo.pdf";
			}
			
			createPDFFile(filename);
		
		    // Sets orientation to landscape
		    Document document = new Document(PageSize.LETTER.rotate());
		    // Grabs the pdf made on the desktop in order to modify it
		    PdfWriter.getInstance(document, new FileOutputStream(filename));
		    // Opens PDF ready for editing
		    document.open();
		    // Adding pdf properties   
		    document.addTitle("Student details");
		    document.addTitle("Student Info Card");
		    document.addAuthor("TMH");
		    document.addSubject("Contains students details and results");
		    
		    for(int i = 0;i < students.size();i++){
        	Student s = students.get(i);
        	//Adds title to PDF with student name and #Student
            Font fontbold = FontFactory.getFont("COURIER_BOLDOBLIQUE", 20, Font.BOLD + Font.UNDERLINE);
            Paragraph p = new Paragraph(s.getName()+","+"#"+s.getStudentNumber(), fontbold);
            p.setSpacingAfter(10);
            p.setAlignment(1); // Center
            document.add(p);
            
            
            //Creates table with student details
            document.add(createFirstTable(s));
            //Adds table that shows results
            document.add(createResultsTable(s));
            
            if(s.getLastAccessArray().size() > 0){
            	//Adds participation records
            	document.add(createAccessTable(s));
            }
            document.newPage();
        }
		    document.close();
    }
    
   
    private static PdfPTable createFirstTable(Student s) throws DocumentException {
	    	// a table with three columns
	        PdfPTable table = new PdfPTable(2);
	        table.setSpacingBefore(5);
	        table.setWidths(new int[]{30,50});
	        // the cell object
	        PdfPCell cell;
	        Font fontH1 = FontFactory.getFont("COURIER", 16, Font.BOLD);
	
	        
	        cell = new PdfPCell(new Phrase("Name",fontH1));
	        cell.setBorderWidth(3);
	        cell.setBackgroundColor(BaseColor.YELLOW);
	        table.addCell(cell);
	        
	        cell = new PdfPCell(new Phrase(s.getName(),fontH1));
	        cell.setLeading(30f, 0f);
	        cell.setBorderWidth(3);
	        cell.setFixedHeight(40f);
	        cell.setPadding(5);
	        cell.setColspan(2);
	        table.addCell(cell);
	        
	        cell = new PdfPCell(new Phrase("Email",fontH1));
	        cell.setBorderWidth(3);
	        cell.setBackgroundColor(BaseColor.YELLOW);
	        table.addCell(cell);
	        cell = new PdfPCell(new Phrase(s.getEmail(),fontH1));
	        cell.setFixedHeight(40f);
	        cell.setPadding(5);
	        cell.setBorderWidth(3);
	        cell.setLeading(30f, 0f);
	        cell.setColspan(2);
	        table.addCell(cell);
	        
	        cell = new PdfPCell(new Phrase("Student Number",fontH1));
	        cell.setBorderWidth(3);
	        cell.setBackgroundColor(BaseColor.YELLOW);
	        table.addCell(cell);
	        cell = new PdfPCell(new Phrase(s.getStudentNumber()+"",fontH1));
	        cell.setFixedHeight(40f);
	        cell.setPadding(5);
	        cell.setLeading(30f, 0f);
	        cell.setBorderWidth(3);
	        cell.setColspan(2);
	        table.addCell(cell);
	        
	        cell = new PdfPCell(new Phrase("Tutor Email",fontH1));
	        cell.setBorderWidth(3);
	        cell.setBackgroundColor(BaseColor.YELLOW);
	        table.addCell(cell);
	        cell = new PdfPCell(new Phrase(s.getTutor(),fontH1));
	        cell.setFixedHeight(40f);
	        cell.setPadding(5);
	        cell.setBorderWidth(3);
	        cell.setLeading(30f, 0f);
	        cell.setColspan(2);
	        table.addCell(cell);
	
	        return table;
    }
    
    private static PdfPTable createResultsTable(Student s) throws DocumentException {
    		// a table with three columns
	        PdfPTable table = new PdfPTable(1);
	        table.setWidthPercentage(50);
	        table.setSpacingBefore(10);
	        // the cell object
	        PdfPCell cell;
	        
	        Font fontH1 = FontFactory.getFont("COURIER", 20, Font.BOLD);
	        
	        if(s.getAssessMarks().size() > 0){
	        	cell = new PdfPCell(new Phrase("Results",fontH1));
	        	cell.setBackgroundColor(BaseColor.YELLOW);
	        	cell.setBorderWidth(2); 
	            cell.setFixedHeight(40f);
	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	            cell.setLeading(30f, 0f);
	        	table.addCell(cell);
	        }
	        for(int i = 0;i < s.getAssessMarks().size();i++){
	        	cell = new PdfPCell(new Phrase(s.getAssessMarks().get(i),fontH1));
	        	
	        	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	            cell.setBorderWidth(2); 
	            cell.setFixedHeight(40f);
	            cell.setLeading(30f, 0f);
	            table.addCell(cell);
	        }
        
        
    	return table;
    }
    
    
    private static PdfPTable createAccessTable(Student s){
	    	PdfPTable table = new PdfPTable(1);
	        table.setWidthPercentage(50);
	        table.setSpacingBefore(1);
	        // the cell object
	        PdfPCell cell;
	        
	        Font fontH1 = FontFactory.getFont("COURIER", 20, Font.BOLD);
	        cell = new PdfPCell(new Phrase("Last Access Times",fontH1));
	    	cell.setBackgroundColor(BaseColor.YELLOW);
	    	cell.setBorderWidth(2); 
	        cell.setFixedHeight(40f);
	        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	        cell.setLeading(30f, 0f);
	    	table.addCell(cell);
    	
    	
	        for(int i = 0;i < s.getLastAccessArray().size();i++){
	        
	        	cell = new PdfPCell(new Phrase(s.getLastAccessArray().get(i),fontH1));
	        	cell.setBorderWidth(2); 
	            cell.setFixedHeight(40f);
	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	            cell.setLeading(30f, 0f);
	        	table.addCell(cell);
	        }
    	
		return table;
    	
    }
}