import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;
 
/**
 * This class is responsible of creating a new file in a directory of the system the application operates
 */
public class CreateNewFile {

	final static String newLine = System.getProperty("line.separator");
		
	
	/**
	 * This method creates a .txt file, and populates the file with the records stored in the 2 main arraylists
	 * @param path the file will be saved to
	 */
    public static void createFile(String path) throws IOException { 
    	
    	  ArrayList < Client > listClients = new ArrayList < Client > ();   
    	  ArrayList < Ports > listOpenPorts = new ArrayList < Ports > ();
          File file = new File(path);
          
          try {
        	  	// imports current collected information from main arraylists
        	  	// writes the data to the designated file
        	  	LocalDateTime now = LocalDateTime.now();
        	  	FileUtils.writeStringToFile(file, "File Created: " +now.toString(), "UTF-8", true);
        	  	FileUtils.writeStringToFile(file, newLine, "UTF-8", true);
        	  	FileUtils.writeStringToFile(file, newLine, "UTF-8", true);
        	  	FileUtils.writeStringToFile(file, "Network Scan Report:", "UTF-8", true);
        	  	FileUtils.writeStringToFile(file, newLine, "UTF-8", true);
              	FileUtils.writeLines(file, Interface.loadClients(listClients), true);
              	FileUtils.writeStringToFile(file, newLine, "UTF-8", true);  
              	FileUtils.writeStringToFile(file, "Ports Scan Report:", "UTF-8", true);
              	FileUtils.writeStringToFile(file, newLine, "UTF-8", true);
              	FileUtils.writeLines(file, PortScanner.loadPorts(listOpenPorts), true);
              	FileUtils.writeStringToFile(file, newLine, "UTF-8", true);              	

          } catch (IOException e) {
              e.printStackTrace();
          }
                 
    }
}