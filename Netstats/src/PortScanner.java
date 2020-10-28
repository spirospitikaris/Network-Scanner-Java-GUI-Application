import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

/**
 * This class looks for all open ports within a range, considering the host address
 */
public class PortScanner {
	
	static ArrayList < Ports > listOpenPorts = new ArrayList < Ports > ();
		
    public static void runPorts(JLabel lblResult, JLabel lblStatus) throws InterruptedException, ExecutionException {
    	
    	 try (InputStream input = new FileInputStream("config.properties")) {

	            Properties prop = new Properties();
	            prop.load(input);
	            
	            int index=0;
	        	
		        final ExecutorService es = Executors.newFixedThreadPool(20);
		        String ip = prop.getProperty("port.host");
		        int portStart = Integer.parseInt(prop.getProperty("port.start"));
		        int portEnd = Integer.parseInt(prop.getProperty("port.end"));
		        final int timeout = 200;
		        final List < Future < ScanResult >> scannedPorts = new ArrayList < > ();
		
		        for (int port = portStart ; port <= portEnd; port++) {  		
		        	scannedPorts.add(portIsOpen(es, ip, port, timeout));
		        }
		        
		        es.awaitTermination(200L, TimeUnit.MILLISECONDS);
		        int openPortsCounter = 0;
		        
		        for (final Future < ScanResult > p: scannedPorts) {
		        	
		        	if(Interface.stopRequest == true) {
		        		break;
		        	}
		        	
		            if (p.get().isOpen()) {
		            	index++;
		            	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
			        	LocalDateTime now = LocalDateTime.now();
			        	
			        	// temporary array to store collected ports related data
		            	Object[] row = new Object[6];
		        	 	row[0] = index;
	                    row[1] = p.get().getPort();
	                    row[2] = "TCP/IP";
	                    row[3] = "Open";
	                    row[4] = ip;
	                    row[5] = dtf.format(now);
	                    
	                    // adds the collected data to table line
	                    updateTable(row);
		                openPortsCounter++;
		                
		                // stores the collected data in a predefined arraylist of objects
		                listOpenPorts.add(new Ports(index, p.get().getPort(), "TCP/IP", "Open",ip, dtf.format(now)));
		            }
		        }
		        
		        lblStatus.setText("Finished"); 
		        lblResult.setText("There are " + openPortsCounter + " open ports on host " + ip + " (probed with a timeout of " + timeout + "ms)");
		       		        
    	   } catch (IOException io) {
               io.printStackTrace();
           }
    }
    
    /**
	 * This method updates the table view every time a new addition occurs
	 */
    private static void updateTable(final Object[] query) {
	    SwingUtilities.invokeLater(new Runnable() {
	        public void run() {     	
	        	if(Interface.stopRequest == false) {
	            Interface.modelTablePorts.addRow(query);
	        	}
	        }
	    });
    }
    
	/**
	 * This method checks if the requested port is open or not
	 * @param es asynchronous execution mechanism
	 * @param ip host address
	 * @param port for check
	 * @param timeout connection timeout value
	 * @return the outcome of the check
	 */
     public static Future < ScanResult > portIsOpen(final ExecutorService es, final String ip, final int port, final int timeout) {
        return es.submit(new Callable < ScanResult > () {
            @Override
            public ScanResult call() {
                try {
                    Socket socket = new Socket();
                    socket.connect(new InetSocketAddress(ip, port), timeout);
                    socket.close();
                    return new ScanResult(port, true);
                } catch (Exception ex) {
                    return new ScanResult(port, false);
                }
            }
        });
     }
    
 	/**
 	 * This class implements scanned ports as objects
 	 */
     public static class ScanResult {
    	
        private int port;
        private boolean isOpen;
        
        public ScanResult(int port, boolean isOpen) {
            super();
            this.port = port;
            this.isOpen = isOpen;
        }
        
        public int getPort() {
            return port;
        }
        
        public void setPort(int port) {
            this.port = port;
        }
        
        public boolean isOpen() {
            return isOpen;
        }
        
        public void setOpen(boolean isOpen) {
            this.isOpen = isOpen;
        }
     }
   
     /**
 	 * This method provides access to the records the variable holds
 	 * @param openPorts arraylist
 	 * @return arraylist of objects/records  
 	 */
     public static ArrayList<Ports> loadPorts(ArrayList < Ports > openPorts) {		
		return listOpenPorts;
     }

}