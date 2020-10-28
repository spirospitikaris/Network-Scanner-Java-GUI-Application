import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JFormattedTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.PlainDocument;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;

/**
 * This class sets the main operational parameters of the application
 */
public class Options extends JFrame{
	
	// declaring variables
	
	private JPanel contentPane;
	JFrame dialog;
	static InetAddress inetAddress = null;
	static JFormattedTextField txtIpStart;
	static JFormattedTextField txtIpEnd;
	static JFormattedTextField txtHostAddress;
	static JTextField txtPortStart;
	static JTextField txtPortEnd;
	public static Properties prop;
	   
    private IPAddressValidator ipAddressValidator;
    
    public Options() {
        OptionsGUI();
        CenteredFrame(this); 
    }

	public void OptionsGUI() {
		
		// declaring all GUI elements with their properties for the window OPTIONS
			
		setTitle("Scan Options");
        setIconImage(new ImageIcon(this.getClass().getResource("/icon.png")).getImage());
		
        // validates the user input regarding ipv4 addresses
		ipAddressValidator = new IPAddressValidator();
				
	    try (InputStream input = new FileInputStream("config.properties")) {
	    	
			    Properties prop = new Properties();
			    prop.load(input);
			    							    
				setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				setBounds(100, 100, 314, 357);
				contentPane = new JPanel();
				contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
				setContentPane(contentPane);
				contentPane.setLayout(null);
				
				// restores the parameters to factory defaults
				
				JButton btnReset = new JButton("Reset defaults");
				btnReset.setToolTipText("Reset all parameters to factory defaults");
				btnReset.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						  //restoreDefaults();
							FileOutputStream output;
							try {
								 // sets the default properties values
								output = new FileOutputStream("config.properties");
					            prop.setProperty("host", getHost());
					            prop.setProperty("host.start", getHostStart());
					            prop.setProperty("host.end", getHostEnd());
					            prop.setProperty("port.host", getPortHost());
					            prop.setProperty("port.start", setPortRangeStart()+"");
					            prop.setProperty("port.end", setPortRangeEnd()+"");					            
					            prop.store(output, null);
					            output.close();
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						
					      txtIpStart.setText(prop.getProperty("host.start"));
				          txtIpEnd.setText(prop.getProperty("host.end"));
				          txtHostAddress.setText(prop.getProperty("port.host"));
				          txtPortStart.setText(prop.getProperty("port.start"));
				          txtPortEnd.setText(prop.getProperty("port.end"));
				          
					}
				});
				btnReset.setBounds(175, 235, 109, 23);
				contentPane.add(btnReset);
				
				// saves the user choices, only when user input its acceptable
				
				JButton btnOK = new JButton("OK");
				btnOK.setToolTipText("Save all changes");
				btnOK.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						boolean a=true;
						boolean validIp1 = ipAddressValidator.validate(txtIpStart.getText());
						boolean validIp2 = ipAddressValidator.validate(txtIpEnd.getText());
						boolean validIp3 = ipAddressValidator.validate(txtHostAddress.getText());
						
						// checking the ip addresses 
						if (validIp1==false||validIp2==false||validIp3==false) {
							JOptionPane.showMessageDialog(dialog,"Enter a valid IP address", "IP address validation error", JOptionPane.ERROR_MESSAGE);
							a=false;
						}
						
						// checking if a field its blank
						if(txtPortStart.getText().equals("")||txtPortEnd.getText().equals("")||txtHostAddress.getText().equals("")) {
							JOptionPane.showMessageDialog(dialog,"Please fill in all required fields", "Data validation error", JOptionPane.ERROR_MESSAGE);
							a=false;
					    }
						
						// checking if ports range input is correct
						if (Integer.parseInt(txtPortStart.getText())>=Integer.parseInt(txtPortEnd.getText())||Integer.parseInt(txtPortStart.getText())>=65535){
							JOptionPane.showMessageDialog(dialog,"Start port range value can't be higher or same with end value\n"+ 
									"Start port range value can't be higher than 65534","Invalid Port value", JOptionPane.ERROR_MESSAGE);
							a=false;
						}
						
						if (Integer.parseInt(txtPortEnd.getText())<=Integer.parseInt(txtPortStart.getText())||Integer.parseInt(txtPortEnd.getText())>65535){
							JOptionPane.showMessageDialog(dialog,"End port range value can't be lower or same with start value\n"+ 
									"End port range value can't be higher than 65535","Invalid Port value", JOptionPane.ERROR_MESSAGE);
							a=false;
						}
						
						if(a==true) {
							try {
									 
									// loads current saved application operation parameters
									 
									FileInputStream in = new FileInputStream("config.properties");
							        prop.load(in);
							        in.close();
							        
							        // changes the saved application operation parameters, to user selected ones
							        						        
							        String subnet = txtIpStart.getText();
						            subnet = subnet.substring(0, subnet.lastIndexOf("."));
						            String host = subnet + "." + 255;
									txtIpEnd.setText(host);
							        FileOutputStream out = new FileOutputStream("config.properties");
							        prop.setProperty("host.start", txtIpStart.getText());
						            prop.setProperty("host.end", txtIpEnd.getText());
						            prop.setProperty("port.host", txtHostAddress.getText());
						            prop.setProperty("port.start", txtPortStart.getText());
						            prop.setProperty("port.end", txtPortEnd.getText()); 
							        prop.store(out, null);
							        out.close();
							        
							        // updates the textfields in the main screen
							        
							        Interface.updateGUI(txtIpStart.getText(),txtIpEnd.getText(), txtHostAddress.getText());
							        dispose();
							        						        
								} catch (FileNotFoundException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
						}

					}
				});
				btnOK.setBounds(100, 284, 89, 23);
				contentPane.add(btnOK);
				
				JButton btnCancel = new JButton("Cancel");
				btnCancel.setToolTipText("Return to main screen without altering any parameter");
				btnCancel.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {						
			            dispose();			
					}
				});
				btnCancel.setBounds(195, 284, 89, 23);
				contentPane.add(btnCancel);
				
				JPanel panel = new JPanel();
				panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "IP Scan options", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
				panel.setBounds(10, 11, 278, 106);
				contentPane.add(panel);
				panel.setLayout(null);
				
				JLabel lblNewLabel_2 = new JLabel("Resolve IP addresses range:");
				lblNewLabel_2.setBounds(10, 26, 157, 14);
				panel.add(lblNewLabel_2);
				
				JLabel lblNewLabel = new JLabel("From:");
				lblNewLabel.setBounds(10, 53, 37, 14);
				panel.add(lblNewLabel);
				
				JLabel lblNewLabel_1 = new JLabel("To:");
				lblNewLabel_1.setBounds(151, 54, 46, 14);
				panel.add(lblNewLabel_1);
				
				txtIpStart = new JFormattedTextField();
				txtIpStart.setToolTipText("This field can be edited only if the below checkbox is enabled");
				txtIpStart.setEnabled(false);
				txtIpStart.setBounds(48, 52, 97, 17);
				panel.add(txtIpStart);
				txtIpStart.setText(prop.getProperty("host.start"));
				
				// listens for user actions on the selected textfield, and changes another textfield
				
				txtIpStart.getDocument().addDocumentListener(new DocumentListener() {
					
					  public void changedUpdate(DocumentEvent e) {
						  changeTextfield();
					  }
					  
					  public void removeUpdate(DocumentEvent e) {
						  changeTextfield();
					  }
					  
					  public void insertUpdate(DocumentEvent e) {
						  changeTextfield();
					  }

					  public void changeTextfield() {
						  
						  txtIpEnd.setEnabled(false);
						  
						  boolean validIp3 = ipAddressValidator.validate(txtIpStart.getText());
							if (validIp3==true) {
								String subnet = txtIpStart.getText();
					            subnet = subnet.substring(0, subnet.lastIndexOf("."));
					            String host = subnet + "." + 255;
								txtIpEnd.setText(host);
							}
							else {
								txtIpEnd.setText(txtIpStart.getText());
							}							
					  }
				});
							
				txtIpEnd = new JFormattedTextField();
				txtIpEnd.setToolTipText("You can't edit this field");
				txtIpEnd.setEnabled(false);
				txtIpEnd.setBounds(171, 52, 97, 17);
				panel.add(txtIpEnd);
				String subnet = txtIpStart.getText();
	            subnet = subnet.substring(0, subnet.lastIndexOf("."));
	            String host = subnet + "." + 255;
				txtIpEnd.setText(host);
				
				JCheckBox cbxIpParameters = new JCheckBox("Update parameters");
				cbxIpParameters.setToolTipText("Enable/Disable the editing of scan target ip range");
				cbxIpParameters.setBounds(10, 74, 134, 23);
				panel.add(cbxIpParameters);
				cbxIpParameters.addItemListener(new ItemListener(){
	                @Override
	                public void itemStateChanged(ItemEvent e) {
	                    if(e.getStateChange() == ItemEvent.SELECTED){
	                    	txtIpStart.setEnabled(true);                   	
	                    }
	                    else if(e.getStateChange() == ItemEvent.DESELECTED){
	                    	txtIpStart.setEnabled(false);
	                    	txtIpEnd.setEnabled(false);	                    	
	                    }
	                }
	            });
				
				JPanel panel_1 = new JPanel();
				panel_1.setLayout(null);
				panel_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Port Scan options", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
				panel_1.setBounds(10, 121, 278, 106);
				contentPane.add(panel_1);
				
				JLabel lblTargetHost = new JLabel("Target Host:");
				lblTargetHost.setBounds(10, 29, 76, 14);
				panel_1.add(lblTargetHost);
				
				JLabel lblPortNo = new JLabel("Port No:");
				lblPortNo.setBounds(10, 54, 76, 14);
				panel_1.add(lblPortNo);
				
				txtHostAddress = new JFormattedTextField();
				txtHostAddress.setToolTipText("This field can be edited only if the below checkbox is enabled");
				txtHostAddress.setBounds(105, 27, 165, 17);
				txtHostAddress.setEnabled(false);
				panel_1.add(txtHostAddress);
				txtHostAddress.setText(prop.getProperty("port.host"));
				
				txtPortStart = new JTextField();
				txtPortStart.setToolTipText("This field can be edited only if the below checkbox is enabled");
				txtPortStart.setBounds(105, 52, 62, 17);
				txtPortStart.setEnabled(false);
				panel_1.add(txtPortStart);
				txtPortStart.setText(prop.getProperty("port.start"));
				
				JLabel label = new JLabel("To:");
				label.setBounds(181, 54, 16, 14);
				panel_1.add(label);

				txtPortEnd = new JTextField();
				txtPortEnd.setToolTipText("This field can be edited only if the below checkbox is enabled");
				txtPortEnd.setBounds(208, 52, 62, 17);
				txtPortEnd.setEnabled(false);
				panel_1.add(txtPortEnd);
				txtPortEnd.setText(prop.getProperty("port.end"));
				
				// validates that user inputs only integers 
												
				PlainDocument doc = (PlainDocument) txtPortStart.getDocument();
				PlainDocument doc2 = (PlainDocument) txtPortEnd.getDocument();
			    doc.setDocumentFilter(new IntegerFilter());
			    doc2.setDocumentFilter(new IntegerFilter());
			    
				JCheckBox cbxPortParameters = new JCheckBox("Update parameters");
				cbxPortParameters.setToolTipText("Enable/Disable the editing of ports related scan parameters");
				cbxPortParameters.setBounds(10, 75, 134, 23);
				panel_1.add(cbxPortParameters);
				
				cbxPortParameters.addItemListener(new ItemListener(){
	                @Override
	                public void itemStateChanged(ItemEvent e) {
	                	
	                    if(e.getStateChange() == ItemEvent.SELECTED){
	                    	txtPortEnd.setEnabled(true);
	                    	txtPortStart.setEnabled(true);
	                    	txtHostAddress.setEnabled(true);
	                    }	                    
	                    else if(e.getStateChange() == ItemEvent.DESELECTED){
	                    	txtPortEnd.setEnabled(false);
	                    	txtPortStart.setEnabled(false);
	                    	txtHostAddress.setEnabled(false);
	                    }
	                }
	            });
				
		
	      } catch (IOException ex) {
	            ex.printStackTrace();
	      }
	}
		
	/**
	 * This method sets the factory default parameters
	 */
	public static void setDefaults() {
		
		// creating a persistent set of properties for the application to store simple parameters as key-values
	    // gets the start values from certain methods
		
        try (FileOutputStream output = new FileOutputStream("config.properties")) {

            prop = new Properties();

            // sets the default properties values
            prop.setProperty("host", getHost());
            prop.setProperty("host.start", getHostStart());
            prop.setProperty("host.end", getHostEnd());
            prop.setProperty("port.host", getPortHost());
            prop.setProperty("port.start", setPortRangeStart()+"");
            prop.setProperty("port.end", setPortRangeEnd()+"");
            
            prop.store(output, null);
            output.close();
            
        } catch (IOException io) {
            io.printStackTrace();
        }
		
	}
	
	/**
	 * This method gets the host ipv4 address
	 * @return the ip address 
	 */
	public static String getHost() {
		  try {
				inetAddress = InetAddress.getLocalHost();
		  } catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
		  }
	        
	      String subnet = inetAddress.getHostAddress();
	        
        return subnet;
    }
	
	/**
	 * This method gets the host ipv4 address first subnet ip
	 * @return the scan first ip address
	 */
	public static String getHostStart() {
		  try {
				inetAddress = InetAddress.getLocalHost();
		  } catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
		  }
	        
	        String subnet = inetAddress.getHostAddress();
	        subnet = subnet.substring(0, subnet.lastIndexOf("."));
	        int hostStartrange= 1;
	        String hostStart = subnet + "." + hostStartrange;
	        	        
      return hostStart;
    }
	
	/**
	 * This method gets the host ipv4 address last subnet ip
	 * @return the scan last ip address
	 */
	public static String getHostEnd() {
		  try {
				inetAddress = InetAddress.getLocalHost();
		  } catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
		  }
	        
	        String subnet = inetAddress.getHostAddress();
	        subnet = subnet.substring(0, subnet.lastIndexOf("."));
	        int hostEndrange= 255;
	        String hostEnd = subnet + "." + hostEndrange;
	        
      return hostEnd;
    }
	
	/**
	 * This method gets the host ipv4 address 
	 * @return the host ip address 
	 */
	public static String getPortHost() {
		  try {
				inetAddress = InetAddress.getLocalHost();
		  } catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
		  }
	        
	      String subnet = inetAddress.getHostAddress();	        
      return subnet;
    }
	
	/**
	 * This method sets the subnet start value
	 * @return the ip subnet start value 
	 */
	public static int setHostRangeStart() {
		int hostStartRange= 1;       
	  return hostStartRange;
    }
	
	/**
	 * This method sets the subnet end value
	 * @return the ip subnet end value 
	 */
	public static int setHostRangeEnd() {
		int hostEndRange= 255;     
	  return hostEndRange;
    }
	
	/**
	 * This method sets the start range of ports for scan
	 * @return the ports start range
	 */
	public static int setPortRangeStart() {
		 int portStartRange=1;
	  return portStartRange;
    }
	
	/**
	 * This method sets the end range of ports for scan 
	 * @return the ports end range 
	 */
	public static int setPortRangeEnd() {
		int portEndRange=65535;
	  return portEndRange;
    }
	
    /**
	 * This method center the frames to the center of the monitor screen
	 * @param objFrame a frame object
	 */
    public static void CenteredFrame(JFrame objFrame){
        Dimension objDimension = Toolkit.getDefaultToolkit().getScreenSize();
        int iCoordX = (objDimension.width - objFrame.getWidth()) / 2;
        int iCoordY = (objDimension.height - objFrame.getHeight()) / 2;
        objFrame.setLocation(iCoordX, iCoordY); 
    } 
    
}
