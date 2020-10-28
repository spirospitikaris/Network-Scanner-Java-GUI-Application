/**
 * This class implements socket ports as objects
 */
public class Ports {

		int id;
		int portNumber;
		String protocol;
		String status;
		String hostAddress;
		String dateTime;
		
		/**
		 * 
		 * This constructs a port object with a specified id, portNumber, protocol, status, hostName and dateTime
		 * 
		 * @param id the unique identifier of the record
		 * @param portNumber the number of the port
		 * @param protocol the transport protocol		  
		 * @param hostAddress the host address the port is cross checked for its use
		 * @param dateTime the date and time the port was spotted 
		 */
		Ports(int id, int portNumber, String protocol, String status, String hostAddress, String dateTime) {
			this.id = id;
			this.portNumber = portNumber;
			this.protocol = protocol;
			this.status = status;
			this.hostAddress = hostAddress;
			this.dateTime = dateTime;
		}
		
		/**
		 * This method converts an object to its string representation so that it is suitable for display
		 * @return id, status, protocol, status, hostAddress, dateTime in String type for further use 
		 */
		public String toString() {
	        return "[Id=" + id 
	                + ", port=" + status 
	                + ", protocol=" + protocol
	                + ", status=" + status
	                +", hostAddress=" + hostAddress
	                +", dateTime=" + dateTime
	                + "]";
		}
}
