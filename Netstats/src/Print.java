import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import javax.swing.JTextArea;

/**
 * This class facilitates the printing function
 */
public class Print implements Printable {
	
    private String printData;
    static JTextArea textArea;

    public Print(String printDataIn){    	
    	this.printData = printDataIn;    
    }

	@Override
	public int print(Graphics g, PageFormat pf, int page) throws PrinterException{

	    if (page > 0){
	        return NO_SUCH_PAGE;
	    }
	
	    // adding the "Imageable" to the x and y puts the margins on the page, to make it safe for printing

	    Graphics2D g2d = (Graphics2D)g;
	    int x = (int) pf.getImageableX();
	    int y = (int) pf.getImageableY();        
	    g2d.translate(x, y); 
	
	    // calculate the line height
	    Font font = new Font("Serif", Font.PLAIN, 10);
	    FontMetrics metrics = g.getFontMetrics(font);
	    int lineHeight = metrics.getHeight();
	
	    BufferedReader br = new BufferedReader(new StringReader(printData));
	
	    // draw the page
	    try{
	        String line;
	        x += 50;
	        y += 50;
	        while ((line = br.readLine()) != null){
	            y += lineHeight;
	            g2d.drawString(line, x, y);
	        }
	    }
	    catch (IOException e) {
	      
	    }
	
	    return PAGE_EXISTS;
	}
	
	/**
	 * This method creates and shows a modal dialog to initiate actual print job 
	 * @param textArea the text content from a .txt file or a scam report
	 */
	public static void printToPrinter(JTextArea textArea){
		
	    String printData = textArea.getText() + "\n";
	    PrinterJob job = PrinterJob.getPrinterJob();
	    job.setPrintable(new Print(printData));
	    boolean doPrint = job.printDialog();
	    
	    if (doPrint){ 
	        try {
	           job.print();
	        }
	        catch (PrinterException e) {
	            
	        }
	    }
	}
}