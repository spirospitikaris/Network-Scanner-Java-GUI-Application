/**
 * This class implements network available clients as objects
 */
public class Client{

	int id;
	String status;
	String ip;
	String macAddress;
	String hostName;
	String dateTime;
	
	/**
	 * 
	 * This constructs a client object with a specified id, status, ip, macAddress, hostName and dateTime
	 * 
	 * @param id the unique identifier of the record
	 * @param status if the ip is reachable or no
	 * @param ip the ipv4 address
	 * @param macAddress the physical address
	 * @param hostName the name of a particular device
	 * @param dateTime the date and time the client was spotted in the network
	 */
	Client(int id, String status, String ip, String macAddress, String hostName, String dateTime) {
		this.id = id;
		this.status = status;
		this.ip = ip;
		this.macAddress = macAddress;
		this.hostName = hostName;
		this.dateTime = dateTime;
	}
	
	/**
	 * This method converts an object to its string representation so that it is suitable for display
	 * @return id, status, ip, macAddress, hostName, dateTime in String type for further use 
	 */
	public String toString() {
        return "[Id=" + id 
                + ", status=" + status 
                + ", ip=" + ip
                + ", macAddress=" + macAddress
                +", hostName=" + hostName
                +", dateTime=" + dateTime
                + "]";
    }

}
