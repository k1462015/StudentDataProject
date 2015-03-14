package extraFeatures;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;

import praCourseWork2.Student;

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
 * First iText example: Hello World.
 */
public class PDFGenerator {
 
    /** Path to the resulting PDF file. */
    public static final String RESULT
        = "/Users/tahmidulislam/Desktop/Document2.pdf";
 
    /**
     * Creates a PDF file: hello.pdf
     * @param    args    no arguments needed
     */
//    public static void main(String[] args)
//    	throws DocumentException, IOException {
//    	Student s = new Student("Tahmidul Islam","t@kcl.ac.uk",12312,"tutor@kcl.ac.uk");
//    	s.addMarks("4CCS1", 24);
//    	s.addMarks("4CCPRP",90);
//    	s.addMarks("4CCS21", 74);
//    	s.addMarks("4CCPR2P",50);
//    	s.addParticipation("4CCS1PRP 45mins ago");
//    	new PDFTEST().createPdf(s);
//    }
    
    public void createPDFFile(String filepath){
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
     * Creates a PDF document.
     * @param filename the path to the new PDF document
     * @throws    DocumentException 
     * @throws    IOException 
     */
    public void createPdf(ArrayList<Student> students)
	throws DocumentException, IOException {
    	//Checks users operating system
    	//Then creates PDF in desktop
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
            
            if(s.getParticipationArray().size() > 0){
            	//Adds participation records
            	document.add(createAccessTable(s));
            }
            document.newPage();
        }
//        //Adds title to PDF with student name and #Student
//        Font fontbold = FontFactory.getFont("COURIER_BOLDOBLIQUE", 20, Font.BOLD + Font.UNDERLINE);
//        Paragraph p = new Paragraph(s.getName()+","+"#"+s.getStudentNumber(), fontbold);
//        p.setSpacingAfter(10);
//        p.setAlignment(1); // Center
//        document.add(p);
//        
//        
//        //Creates table with student details
//        document.add(createFirstTable(s));
//        //Adds table that shows results
//        document.add(createResultsTable(s));
//        
//        if(s.getParticipationArray().size() > 0){
//        	//Adds participation records
//        	document.add(createAccessTable(s));
//        }
        // step 5
        document.close();
    }
    
    /**
     * Creates our first table
     * @return our first table
     * @throws DocumentException 
     */
    public static PdfPTable createFirstTable(Student s) throws DocumentException {
    	// a table with three columns
        PdfPTable table = new PdfPTable(2);
        table.setSpacingBefore(5);
        table.setWidths(new int[]{30,50});
        // the cell object
        PdfPCell cell;
        Font fontH1 = FontFactory.getFont("COURIER", 20, Font.BOLD);

        
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
        cell = new PdfPCell(new Phrase(s.getStudentNumber(),fontH1));
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
    
    public static PdfPTable createResultsTable(Student s) throws DocumentException {
    	// a table with three columns
        PdfPTable table = new PdfPTable(1);
        table.setWidthPercentage(50);
        table.setSpacingBefore(10);
//        table.setWidths(new int[]{30,50});
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
//            cell.setBorder(PdfPCell.NO_BORDER);
            table.addCell(cell);
        }
        
        
    	return table;
    }
    
    public static PdfPTable createAccessTable(Student s){
    	PdfPTable table = new PdfPTable(1);
        table.setWidthPercentage(50);
        table.setSpacingBefore(1);
//        table.setWidths(new int[]{30,50});
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
    	
    	
        for(int i = 0;i < s.getParticipationArray().size();i++){
        
        	cell = new PdfPCell(new Phrase(s.getParticipationArray().get(i),fontH1));
        	cell.setBorderWidth(2); 
            cell.setFixedHeight(40f);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setLeading(30f, 0f);
        	table.addCell(cell);
        }
    	
		return table;
    	
    }
}