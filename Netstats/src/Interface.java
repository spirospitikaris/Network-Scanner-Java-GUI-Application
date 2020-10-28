import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;
import javax.swing.JTabbedPane;
import java.awt.Color;
import java.awt.Desktop;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.JProgressBar;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import javax.swing.JSeparator;
import java.awt.Panel;
import javax.swing.JPanel;

/**
 * This class constructs the main application interface, including all menus and submenus
 */
public class Interface {
	
	// declaring variables
	
    public static JFrame frame;
    private static JTable tableNetwork;
    private JTable tablePorts;   
    private static DefaultTableModel modelTableNetwork;
    static DefaultTableModel modelTablePorts;
    static JProgressBar progressBar;
    public static JLabel lblPortsStatus;
    public static JLabel lblPortsResult;
    static JLabel lblStatus;
    static JLabel lblOnline;
    static JLabel lblOffline;
    static JLabel lblUnknown;
    static JButton  btnStart;
    static JButton  btnStop;
    static JTextField txtIpStart;
    static JTextField txtIpEnd;
    static JLabel lblTargetHost;
    static JTextArea textArea;    
    static Properties prop;
        
    static String ip;
    static String hostStartrange;
    static int sRange;          
    static String hostEndrange;
    static int eRange;
    int progressCounter;
    int onlineClients;
    int offlineClients;
    Timer simpleTimer;
        
    public volatile static boolean stopRequest;  
    
    // declaring the main arraylists which will be used to store temporary all application data

    String[] tableNetworkHeaders = {"Index","Status","IP Address","MAC Address","Host Name","Added On"};
    String[] tablePortsHeaders = {"Index","Port","Protocol","Status","Host Address","Added On"};

    static ArrayList < Client > listClients = new ArrayList < Client > ();
    static ArrayList < Ports > listOpenPorts = new ArrayList < Ports > ();
    
  
    public Interface() {
        initialize();
        frame.setVisible(true);
        Options.CenteredFrame(frame);
    }
   
    public void initialize() {
    	
    	// setting the defaults operation parameters
		Options.setDefaults();
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		// declaring all GUI elements with their properties
	
	    frame = new JFrame();
	    
	    frame.setTitle("NetStats Network Scanner");
	    frame.setIconImage(new ImageIcon(this.getClass().getResource("/icon.png")).getImage());
	    frame.getContentPane().setBackground(SystemColor.window);
	    frame.setBackground(SystemColor.window);
	    frame.setBounds(100, 100, 727, 581);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.getContentPane().setLayout(null);
	    
	    // calling and creating the menu bar
	    menuBar();
	   
	    JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	    tabbedPane.setBackground(Color.WHITE);
	    tabbedPane.setBounds(5, 89, 703, 408);
	    frame.getContentPane().add(tabbedPane);
	           
	    JScrollPane scrollPane = new JScrollPane();
	    
	    tabbedPane.add("Network", scrollPane);
	    
	    JPanel panel_1 = new JPanel();
	    panel_1.setBorder(null);
	    tabbedPane.addTab("Ports",panel_1);
	    panel_1.setLayout(null);
	    
	    JScrollPane scrollPane_2 = new JScrollPane();
	    scrollPane_2.setBounds(0, 0, 698, 325);
	    panel_1.add(scrollPane_2);
	    
	    tableNetwork = new JTable();
	    tableNetwork.setEnabled(false);
	    tableNetwork.setBackground(Color.WHITE);
	    tableNetwork.setShowHorizontalLines(false);
	    tableNetwork.setShowVerticalLines(false);
	    tableNetwork.setShowGrid(false);
	    scrollPane.setViewportView(tableNetwork);
	    
	    modelTableNetwork = new DefaultTableModel();
	    modelTableNetwork.setColumnIdentifiers(tableNetworkHeaders);
	    tableNetwork.setModel(modelTableNetwork);
	    
	    tablePorts = new JTable();
	    tablePorts.setEnabled(false);
	    tablePorts.setBackground(Color.WHITE);
	    tablePorts.setShowHorizontalLines(false);
	    tablePorts.setShowVerticalLines(false);
	    tablePorts.setShowGrid(false);       
	    scrollPane_2.setViewportView(tablePorts);
	    
	    modelTablePorts = new DefaultTableModel();
	    modelTablePorts.setColumnIdentifiers(tablePortsHeaders);
	    tablePorts.setModel(modelTablePorts);
	     
	    JLabel lblPortScanResult = new JLabel("Scan Result:");
	    lblPortScanResult.setBounds(23, 336, 67, 14);
	    panel_1.add(lblPortScanResult);
	    
	    lblPortsStatus = new JLabel("Ready");
	    lblPortsStatus.setBounds(88, 336, 600, 14);
	    panel_1.add(lblPortsStatus);
	    
	    lblPortsResult = new JLabel("");
	    lblPortsResult.setBounds(88, 355, 600, 14);
	    panel_1.add(lblPortsResult);
	                   
	    tableNetwork.getColumnModel().getColumn(0).setMinWidth(50);
	    tableNetwork.getColumnModel().getColumn(0).setMaxWidth(50);
	    tableNetwork.getColumnModel().getColumn(1).setMinWidth(80);
	    tableNetwork.getColumnModel().getColumn(1).setMaxWidth(80);
	    tableNetwork.getColumnModel().getColumn(2).setMinWidth(120);
	    tableNetwork.getColumnModel().getColumn(2).setMaxWidth(120);
	    tableNetwork.getColumnModel().getColumn(3).setMinWidth(120);
	    tableNetwork.getColumnModel().getColumn(3).setMaxWidth(120);
	    tableNetwork.getColumnModel().getColumn(4).setMinWidth(120);
	    tableNetwork.getColumnModel().getColumn(4).setMaxWidth(120);
	          
	    tablePorts.getColumnModel().getColumn(0).setMinWidth(50);
	    tablePorts.getColumnModel().getColumn(0).setMaxWidth(50);
	    tablePorts.getColumnModel().getColumn(1).setMinWidth(80);
	    tablePorts.getColumnModel().getColumn(1).setMaxWidth(80);
	    tablePorts.getColumnModel().getColumn(2).setMinWidth(120);
	    tablePorts.getColumnModel().getColumn(2).setMaxWidth(120);
	    tablePorts.getColumnModel().getColumn(3).setMinWidth(120);
	    tablePorts.getColumnModel().getColumn(3).setMaxWidth(120);
	    tablePorts.getColumnModel().getColumn(4).setMinWidth(120);
	    tablePorts.getColumnModel().getColumn(4).setMaxWidth(120);
	
	    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	    
	    centerRenderer.setHorizontalAlignment(JLabel.CENTER);
	    
	    tableNetwork.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
	    tableNetwork.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
	    tableNetwork.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
	    tableNetwork.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
	    tableNetwork.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
	    tableNetwork.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
	    tablePorts.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
	    tablePorts.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
	    tablePorts.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
	    tablePorts.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
	    tablePorts.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
	    tablePorts.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
	            
	    lblStatus = new JLabel("Ready");
	    lblStatus.setBounds(15, 495, 76, 25);
	    frame.getContentPane().add(lblStatus);
	           
	    ip = Options.prop.getProperty("host.start");        
	    
	    hostStartrange = Options.prop.getProperty("host.start"); 
	    sRange = Integer.parseInt(hostStartrange.substring(hostStartrange.lastIndexOf(".")+1).trim());
	          
	    hostEndrange = Options.prop.getProperty("host.end");
	    eRange = Integer.parseInt(hostEndrange.substring(hostEndrange.lastIndexOf(".")+1));
	        
	    String hostStart = Options.prop.getProperty("host.start");
	    String hostEnd = Options.prop.getProperty("host.end");
	    
	    txtIpStart = new JTextField();
	    txtIpStart.setToolTipText("The first scan ipv4 address point");
	    txtIpStart.setEditable(false);
	    txtIpStart.setFont(new Font("Segoe UI", Font.PLAIN, 12));
	    txtIpStart.setHorizontalAlignment(SwingConstants.CENTER);
	    txtIpStart.setText(hostStart);
	    txtIpStart.setBounds(123, 53, 152, 25);
	    frame.getContentPane().add(txtIpStart);
	    
	    JLabel lblTo = new JLabel("to");
	    lblTo.setBounds(280, 58, 18, 19);
	    frame.getContentPane().add(lblTo);
	    
	    txtIpEnd = new JTextField();
	    txtIpEnd.setToolTipText("The last scan ipv4 address point");
	    txtIpEnd.setEditable(false);
	    txtIpEnd.setFont(new Font("Segoe UI", Font.PLAIN, 12));
	    txtIpEnd.setHorizontalAlignment(SwingConstants.CENTER);
	    txtIpEnd.setText(hostEnd);
	    txtIpEnd.setBounds(295, 53, 152, 25);
	    frame.getContentPane().add(txtIpEnd);
	    
	    progressBar = new JProgressBar();
	    progressBar.setMinimum(0);
	    progressBar.setMaximum(eRange-sRange+1);
	    progressBar.setBounds(295, 495, 412, 25);
	    progressBar.setStringPainted(true);
	    progressBar.setForeground(new Color(50, 205, 50));
	    frame.getContentPane().add(progressBar);
	    
	    // clears the tables from gathered data and restarts the applications to pre scan state
	    
	    JButton btnClear = new JButton("");
	    btnClear.setToolTipText("Clear all fields from previous scan acquired information");
	    try {
	        Image img = ImageIO.read(this.getClass().getResource("/clear.png"));
	        btnClear.setIcon(new ImageIcon(img));
	      } catch (Exception ex) {
	        System.out.println(ex);
	      }
	    btnClear.setMargin(new Insets(0, 0, 0, 0));
	    btnClear.setFocusPainted(false);
	    btnClear.setBorderPainted(false);
	    btnClear.setBorder(null);
	    btnClear.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		stopScan();
	    		clear();
	    	}
	    });
	    btnClear.setBounds(259, 3, 43, 32);
	    frame.getContentPane().add(btnClear);
	    
	    // opens a .txt file or a previous scan report to read mode
	            
	    JButton btnOpen = new JButton("");
	    btnOpen.setToolTipText("Open a previous scan report or a text file in read mode to a new window");
	    try {
	        Image img = ImageIO.read(this.getClass().getResource("/open.png"));
	        btnOpen.setIcon(new ImageIcon(img));
	      } catch (Exception ex) {
	        System.out.println(ex);
	      }
	    btnOpen.setMargin(new Insets(0, 0, 0, 0));
	    btnOpen.setFocusPainted(false);
	    btnOpen.setBorderPainted(false);
	    btnOpen.setBorder(null);
	    
	    btnOpen.setBackground(SystemColor.control);
	    btnOpen.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	        	stopScan();
	        	new Open();           		
	        }
	    });
	    btnOpen.setBounds(305, 3, 43, 32);
	    frame.getContentPane().add(btnOpen);
	    
	    // opens encryption window
	    
	    JButton btnEncrypt = new JButton("");
	    btnEncrypt.setToolTipText("Initiate encryption operation, opens a new window with further directions");
	    try {
	        Image img = ImageIO.read(this.getClass().getResource("/key.png"));
	        btnEncrypt.setIcon(new ImageIcon(img));
	      } catch (Exception ex) {
	        System.out.println(ex);
	      }
	    btnEncrypt.setMargin(new Insets(0, 0, 0, 0));
	    btnEncrypt.setFocusPainted(false);      
	    btnEncrypt.setBorderPainted(false);
	    btnEncrypt.setBorder(null);
	    btnEncrypt.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		stopScan();
	    		Encryption frame = new Encryption();
				frame.setVisible(true);        	
	    	}
	    });
	    btnEncrypt.setBounds(351, 3, 43, 32);
	    frame.getContentPane().add(btnEncrypt);
	    
	    // opens decryption window
	            
	    JButton btnDecrypt = new JButton("");
	    btnDecrypt.setToolTipText("Initiate decryption operation, opens a new window with further directions");
	    try {
	        Image img = ImageIO.read(this.getClass().getResource("/unlock.png"));
	        btnDecrypt.setIcon(new ImageIcon(img));
	      } catch (Exception ex) {
	        System.out.println(ex);
	      }
	    btnDecrypt.setMargin(new Insets(0, 0, 0, 0));
	    btnDecrypt.setFocusPainted(false);        
	    btnDecrypt.setBorderPainted(false);
	    btnDecrypt.setBorder(null);
	    btnDecrypt.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		Decryption frame = new Decryption();
	    		stopScan();
				frame.setVisible(true);        		
	    	}
	    });
	    btnDecrypt.setBounds(392, 3, 43, 32);
	    frame.getContentPane().add(btnDecrypt);   
	    
	    // opens options window
	    
	    JButton btnOptions = new JButton("");
	    btnOptions.setToolTipText("Change all scan related parameters, for more specific scan results");
	    try {
	        Image img = ImageIO.read(this.getClass().getResource("/settings.png"));
	        btnOptions.setIcon(new ImageIcon(img));
	      } catch (Exception ex) {
	        System.out.println(ex);
	      }
	    btnOptions.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		stopScan();
	    		Options frame = new Options();
				frame.setVisible(true);				
	    	}
	    });
	 
	    btnOptions.setMargin(new Insets(0, 0, 0, 0));
	    btnOptions.setFocusPainted(false);       
	    btnOptions.setBorderPainted(false);
	    btnOptions.setBorder(null);
	    btnOptions.setBounds(436, 3, 43, 32);
	    frame.getContentPane().add(btnOptions);
	    
	    // prints current scan report
	    
	    JButton btnPrint = new JButton("");
	    btnPrint.setToolTipText("Print the scan report ");
	    try {
	        Image img = ImageIO.read(this.getClass().getResource("/print.png"));
	        btnPrint.setIcon(new ImageIcon(img));
	      } catch (Exception ex) {
	        System.out.println(ex);
	      }
	    btnPrint.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		stopScan();
	    		textArea = new JTextArea();
				for(Client a : listClients){
					   textArea.append(a + "\n");
				}
				Print.printToPrinter(textArea);
	    	}
	    });
	
	    btnPrint.setMargin(new Insets(0, 0, 0, 0));
	    btnPrint.setFocusPainted(false);       
	    btnPrint.setBorderPainted(false);
	    btnPrint.setBorder(null);
	    btnPrint.setBounds(485, 3, 43, 32);
	    frame.getContentPane().add(btnPrint);
	    
	    // opens about window
	    
	    JButton btnAbout = new JButton("");
	    btnAbout.setToolTipText("Learn more about the development tools used to create this application");
	    try {
	        Image img = ImageIO.read(this.getClass().getResource("/about.png"));
	        btnAbout.setIcon(new ImageIcon(img));
	      } catch (Exception ex) {
	        System.out.println(ex);
	      }
	    btnAbout.setMargin(new Insets(0, 0, 0, 0));
	    btnAbout.setFocusPainted(false);       
	    btnAbout.setBorderPainted(false);
	    btnAbout.setBorder(null);
	    btnAbout.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		stopScan();
	    		About frame = new About();
				frame.setVisible(true);
	    	}
	    });
	    btnAbout.setBounds(537, 3, 43, 32);
	    frame.getContentPane().add(btnAbout);
	    
	    // opens application website for further documentation
	    
	    JButton btnWeb = new JButton("");
	    btnWeb.setToolTipText("Visit application website for further documentation and guidance");
		    try {
		        Image img = ImageIO.read(this.getClass().getResource("/globe.png"));
		        btnWeb.setIcon(new ImageIcon(img));
		    } catch (Exception ex) {
		        System.out.println(ex);
		    }
		    
	    btnWeb.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		stopScan();
				if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
				    try {
						Desktop.getDesktop().browse(new URI("http://www.google.com"));
					} catch (IOException | URISyntaxException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}  
				}
	    	}
	    });
	    
	    btnWeb.setMargin(new Insets(0, 0, 0, 0));
	    btnWeb.setFocusPainted(false);       
	    btnWeb.setBorderPainted(false);
	    btnWeb.setBorder(null);
	    btnWeb.setBounds(585, 3, 43, 32);
	    frame.getContentPane().add(btnWeb);
	    
	    JLabel lblCurrentScan = new JLabel("Current Scan Range:");
	    lblCurrentScan.setBounds(10, 58, 114, 14);
	    frame.getContentPane().add(lblCurrentScan);
	    
	    // starts network and ports scan based on the predefined parameters in options
	    
	    btnStart = new JButton("Start Scan");
	    btnStart.setToolTipText("Scans for devices on the network");
	    try {
	        Image img = ImageIO.read(this.getClass().getResource("/play.png"));
	        btnStart.setIcon(new ImageIcon(img));
	      } catch (Exception ex) {
	        System.out.println(ex);
	      }
	    btnStart.setBounds(5, 0, 115, 40);
	   
	    frame.getContentPane().add(btnStart);
	    
	    btnStart.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	        	btnStart.setEnabled(false); 
	        	clear();
	        	stopRequest = false;
	        	lblStatus.setText("Scanning...");
	            Thread queryThread = new Thread() {
	                public void run() {
	                	while(!stopRequest) {
	                		btnStop.setEnabled(true);
	                		runQueries(ip,sRange,eRange);
	                	}
	                }
	            };
	            queryThread.start();
	           
	            Thread queryThread2 = new Thread() {                	
	                public void run() {
	                	
	                		try {
	                			lblPortsStatus.setText("Scanning...");
								PortScanner.runPorts(lblPortsResult, lblPortsStatus);
								
							} catch (InterruptedException e ) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (ExecutionException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}  
	                }
	            };
	            queryThread2.start();
	        }
	    });
	    
	    // stops scan process
	    
	    btnStop = new JButton("Stop");
	    btnStop.setToolTipText("Stops a running scan");
	    btnStop.setEnabled(false);
	    btnStop.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		stopScan();
	    		btnStop.setEnabled(false);
	    	}
	    });
	    btnStop.setBounds(123, 0, 115, 40);
	    frame.getContentPane().add(btnStop);
	    
	    lblOnline = new JLabel("");
	    lblOnline.setBounds(81, 500, 70, 14);
	    frame.getContentPane().add(lblOnline);
	    
	    lblOffline = new JLabel("");
	    lblOffline.setBounds(149, 500, 70, 14);
	    frame.getContentPane().add(lblOffline);
	    
	    lblUnknown = new JLabel("");
	    lblUnknown.setBounds(220, 500, 70, 14);
	    frame.getContentPane().add(lblUnknown);
	    
	    JSeparator separator = new JSeparator();
	    separator.setBounds(0, 38, 708, 14);
	    frame.getContentPane().add(separator);
	    
	    Panel panel = new Panel();
	    panel.setBackground(SystemColor.menu);
	    panel.setBounds(0, 0, 711, 43);
	    frame.getContentPane().add(panel);
	    
	    JLabel blCurrent = new JLabel("Current IPv4 Address:");
	    blCurrent.setBounds(457, 59, 114, 14);
	    frame.getContentPane().add(blCurrent);
	    
	    JLabel lblIpv4Address = new JLabel(Options.prop.getProperty("host"));
	    lblIpv4Address.setToolTipText("Host's current ip address");
	    
	    lblIpv4Address.setBounds(582, 59, 108, 14);
	    frame.getContentPane().add(lblIpv4Address);
	    
	    JLabel lblPortsTargetHost = new JLabel("Ports Target Host:");
	    lblPortsTargetHost.setBounds(457, 77, 114, 14);
	    frame.getContentPane().add(lblPortsTargetHost);
	    
	    lblTargetHost = new JLabel(Options.prop.getProperty("port.host"));
	    lblTargetHost.setToolTipText("The ip address the ports scan targets");
	    lblTargetHost.setBounds(582, 77, 108, 14);
	    frame.getContentPane().add(lblTargetHost);
	    
	}   
    
    public void menuBar() {
    	
    	// creates a menu bar with its items
    	
        ActionListener menuListener = new MenuActionListener();

        JMenuBar menuBar = new JMenuBar();
        
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        menuBar.add(fileMenu);
       
        JMenuItem newscanMenuItem = new JMenuItem("New", KeyEvent.VK_N);
        newscanMenuItem.addActionListener(menuListener);
        fileMenu.add(newscanMenuItem);
        
        JMenuItem openMenuItem = new JMenuItem("Open...", KeyEvent.VK_O);
        openMenuItem.addActionListener(menuListener);
        fileMenu.add(openMenuItem);
        
        JMenuItem saveMenuItem = new JMenuItem("Save As", KeyEvent.VK_S);
        saveMenuItem.addActionListener(menuListener);
        fileMenu.add(saveMenuItem);

        fileMenu.addSeparator();

        JMenuItem exitMenuItem = new JMenuItem("Exit", KeyEvent.VK_X);
        exitMenuItem.addActionListener(menuListener);
        fileMenu.add(exitMenuItem);

        JMenu editMenu = new JMenu("Actions");
        editMenu.setMnemonic(KeyEvent.VK_A);
        menuBar.add(editMenu);

        JMenuItem encryptMenuItem = new JMenuItem("Encrypt...", KeyEvent.VK_E);
        encryptMenuItem.addActionListener(menuListener);
        KeyStroke ctrlEKeyStroke = KeyStroke.getKeyStroke("control E");
        encryptMenuItem.setAccelerator(ctrlEKeyStroke);
        editMenu.add(encryptMenuItem);

        JMenuItem decryptMenuItem = new JMenuItem("Decrypt...", KeyEvent.VK_D);
        decryptMenuItem.addActionListener(menuListener);
        KeyStroke ctrlDKeyStroke = KeyStroke.getKeyStroke("control D");
        decryptMenuItem.setAccelerator(ctrlDKeyStroke);
        editMenu.add(decryptMenuItem);
        
        editMenu.addSeparator();

        JMenuItem printMenuItem = new JMenuItem("Print Report", KeyEvent.VK_P);
        printMenuItem.addActionListener(menuListener);
        KeyStroke ctrlPKeyStroke = KeyStroke.getKeyStroke("control P");
        printMenuItem.setAccelerator(ctrlPKeyStroke);
        editMenu.add(printMenuItem);
      
        frame.setJMenuBar(menuBar);  
        
        JMenu optionsMenu = new JMenu("Options");       
        optionsMenu.setMnemonic(KeyEvent.VK_L);
        menuBar.add(optionsMenu);
    			optionsMenu.addMouseListener(new MouseAdapter() {
    	            @Override
    	            public void mouseClicked(MouseEvent e) {
    	            	Options frame = new Options();
    					frame.setVisible(true);
    	            }
    	        });
   			
        JMenu helpMenu = new JMenu("Help");
        helpMenu.setMnemonic(KeyEvent.VK_H);
        menuBar.add(helpMenu);
    
        helpMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

    			if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
    			    try {
						Desktop.getDesktop().browse(new URI("http://www.google.com"));
					} catch (IOException | URISyntaxException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}   
    			}
            }
        });

    }  
    
    
    static class MenuActionListener implements ActionListener {
        public void actionPerformed(ActionEvent actionEvent) {
        	
        		if(actionEvent.getActionCommand()=="New") {
        			stopScan(); 
                	clear();
        		}
        		
        		if(actionEvent.getActionCommand()=="Open...") {  
        			stopScan();
        			new Open();
        		}
        		
        		if(actionEvent.getActionCommand()=="Save As") {
        			stopScan();
        			new Save();
        		}
        		
        		if(actionEvent.getActionCommand()=="Exit") {  
        			System.exit(0);
        		}
        		
        		if(actionEvent.getActionCommand()=="Encrypt...") {
        			stopScan();
        			Encryption frame = new Encryption();
    				frame.setVisible(true);
        		}
        		
        		if(actionEvent.getActionCommand()=="Decrypt...") {
        			stopScan();
        			Decryption frame = new Decryption();
    				frame.setVisible(true);
        		}
        		
        		if(actionEvent.getActionCommand()=="Print Report") {
        			stopScan();
        			textArea = new JTextArea();
        			for(Client c : listClients){
        				   textArea.append(c + "\n");
        				}
        			textArea.append("\n");
        			for(Ports p : PortScanner.listOpenPorts){
     				   textArea.append(p + "\n");
     				}
        			textArea.append("\n");
        			LocalDateTime now = LocalDateTime.now();
        			textArea.append("File Created: "+now.toString());
        			Print.printToPrinter(textArea);
        		}	
        		
        }
    }  
    
    /**
	 * This method looks for all reachable ip addresses within a range, and prints all available info in a table
	 * @param ip user defined ipv4 address which is used as scan main parameter 
	 * @param sRange start range value
	 * @param eRange end range value
	 */
    private void runQueries(String ip, int sRange, int eRange) {
    	
         int timeout = 1000;
         progressCounter=0;
         onlineClients=0;
         offlineClients=0;
         int index=0;
         int unknownClients=eRange-sRange+1;

         for (int i = sRange; i <= eRange; i++) {
        	
          	if (stopRequest == true){
                break;
            } 
        	
          	// get current time and date
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        	LocalDateTime now = LocalDateTime.now();
            String subnet = ip;
            subnet = subnet.substring(0, subnet.lastIndexOf("."));
            String host = subnet + "." + i;
            
            // temporary array to store collected data to populate later the table
            Object[] row = new Object[6];
        	progressCounter++;
        	index++;
        	InetAddress ip2;
            try {
            		           		
            		if (InetAddress.getByName(host).isReachable(timeout)) {
            			
            			 StringBuilder sb = new StringBuilder();
            			
            			 // getting the hosts physical address
            			 try {
	            			ip2 = InetAddress.getLocalHost();
	            			NetworkInterface network = NetworkInterface.getByInetAddress(ip2);	            			

	            			byte[] mac = network.getHardwareAddress();   
	            			sb = new StringBuilder();
	            			
	            			if (mac != null) {		            				
		            			for (int k = 0; k < mac.length; k++) {
		            				sb.append(String.format("%02X%s", mac[k], (k < mac.length - 1) ? "-" : ""));		
		            			}
	            			}
            			 } catch (UnknownHostException e) {           				
            				e.printStackTrace();           				
            			 } catch (SocketException e){            					
            				e.printStackTrace();            					
            			 } 
            			            			
	                     onlineClients++;
	
	                     row[0] = index;
	                     row[1] = "Online";
	                     row[2] = host;
	                     row[3] = sb.toString();
	                     row[4] = InetAddress.getByName(host).getHostName();
	                     row[5] = dtf.format(now);
	                     
	                     // adds the collected data to new line in table
	                     updateTable(row);
	          
	                     // stores the collected data in a predefined arraylist of objects
	                     listClients.add(new Client(index, "Online", host,sb.toString(),InetAddress.getByName(host).getHostName(), dtf.format(now)));
	                     
                    } 
            		else {
                    	
	                	 offlineClients++;
	                     row[0] = index;
	                     row[1] = "Offline";
	                     row[2] = host;
	                     row[3] = "";
	                     row[4] = "";
	                     row[5] = dtf.format(now);
	                     updateTable(row);
	                     listClients.add(new Client(index, "Offline", host,"","", dtf.format(now)));
                    }
                 
                // responsible to refresh the scan progress in the footer bar
                simpleTimer = new Timer(100, new ActionListener(){
            	    @Override
            	    public void actionPerformed(ActionEvent e) {
            	    	
            	    	 if(stopRequest == false) {
            	    	 lblOnline.setText(String.valueOf(onlineClients)+" online, ");
            	    	 lblOffline.setText(String.valueOf(offlineClients)+ " offline, ");
            	    	 lblUnknown.setText(String.valueOf(unknownClients-onlineClients-offlineClients+ " unknown "));
            	    	 progressBar.setValue(progressCounter);
            	    	 }
            	    }
            	});
            	simpleTimer.start();
            	
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
         
        stopRequest = true;
        btnStart.setEnabled(true);
        lblOnline.setText(String.valueOf(onlineClients)+" online, ");
   	 	lblOffline.setText(String.valueOf(offlineClients+ " offline, "));
   	 	lblUnknown.setText(String.valueOf(unknownClients-onlineClients-offlineClients+ " unknown "));        
        lblStatus.setText("Finished"); 
    }

    /**
	 * This method updates the table view every time a new addition occurs
	 */
    private void updateTable(final Object[] query) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {     	
            	if(stopRequest == false) {
                modelTableNetwork.addRow(query);
            	}
            }
        });
    }
  
    
    /**
	 * This method stops the scan process and updates the status labels
	 */
    public static void stopScan(){
        stopRequest = true;        
        btnStart.setEnabled(true);
        lblStatus.setText("Finished");
		lblPortsStatus.setText("Finished");
    }
    
    /**
	 * This method clears and removes all obtained information from a scan
	 */
    public static void clear() {
		 modelTableNetwork.setRowCount(0);
		 modelTablePorts.setRowCount(0);
		 lblOnline.setText("");
		 lblOffline.setText("");
		 lblUnknown.setText("");
		 lblStatus.setText("Ready");
		 lblPortsStatus.setText("Ready");
		 lblPortsResult.setText("");
		 progressBar.setValue(0);
		 ArrayList < Ports > scannedPorts = PortScanner.loadPorts(listOpenPorts);
		 scannedPorts.clear();
		 listClients.clear();
    }
    
    /**
	 * This method provides access to the records the variable holds
	 * @param clients arraylist
	 * @return arraylist of objects/records  
	 */
    public static ArrayList<Client> loadClients(ArrayList < Client > clients) {		
		return listClients;
	}
    
    /**
	 * This method updates the parameters in the main screen after changes in the options have taken place
	 */
    public static void updateGUI(String startIp, String endIp, String hostIp){
    	txtIpStart.setText(startIp);
    	txtIpEnd.setText(endIp);
    	lblTargetHost.setText(hostIp);
    	ip = startIp;
    	hostStartrange = startIp; 
    	sRange = Integer.parseInt(hostStartrange.substring(hostStartrange.lastIndexOf(".")+1).trim());	          
	    hostEndrange = endIp;
	    eRange = Integer.parseInt(hostEndrange.substring(hostEndrange.lastIndexOf(".")+1));
    }
    
}