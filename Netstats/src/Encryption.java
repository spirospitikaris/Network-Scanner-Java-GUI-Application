import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.border.Border;
import java.awt.Color;
import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.border.EtchedBorder;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.ActionEvent;

/**
 * This class draws the encryption window and facilitates the file encryption function
 */
public class Encryption extends JFrame {
	
	// declaring variables

	private JPanel contentPane;
	private static JTextField txtInputUrl;
	private JTextField txtOutputUrl;
	private JTextField txtRecoveryKeyUrl;
	static JButton btnOutput;
	static byte[] content;
	static byte[] content2;
	static JFileChooser chooser;
	static File file;
	JComboBox<String> cboEncryption;
	
	
    public Encryption() {
	    EncryptionGUI();
        Options.CenteredFrame(this); 
    }

	public void EncryptionGUI() {
		
		// declaring all GUI elements with their properties
	
		setTitle("Encrypt file");
        setIconImage(new ImageIcon(this.getClass().getResource("/icon.png")).getImage());
				
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 450, 479);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		String title = "Input file";
		Border border = BorderFactory.createTitledBorder(title);
		panel.setBorder(border);
		panel.setBounds(10, 11, 416, 103);
		contentPane.add(panel);
		panel.setLayout(null);
		
		cboEncryption = new JComboBox<String>();
		cboEncryption.setToolTipText("A representation of all available encryption algorithms to user");
		cboEncryption.setEnabled(false);
		cboEncryption.setBounds(134, 296, 292, 22);
		
		cboEncryption.addItem("Rivest Cipher 4 (RC4)");
		cboEncryption.addItem("Advanced Encryption Standard (AES) (256-bit key)");
		cboEncryption.addItem("Advanced Encryption Standard (AES) (128-bit key)");
	    cboEncryption.addItem("Data Encryption Standard (DES) (56-bit key)");
		contentPane.add(cboEncryption);
		
		// shows a modal dialog containing the file chooser
		
		JButton btnInput = new JButton("Browse");
		btnInput.setToolTipText("Find and select the text file that you want to encrypt");
		btnInput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getFile();				
			}
		});
		btnInput.setBounds(317, 41, 89, 23);
		panel.add(btnInput);
		
		JCheckBox cbxReport = new JCheckBox("Encrypt and save current report");
		cbxReport.setToolTipText("Enable/Disable the function to encrypt current scan report");
		cbxReport.setBounds(6, 69, 203, 23);
		panel.add(cbxReport);
		
		cbxReport.addItemListener(new ItemListener(){
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED){
                	txtInputUrl.setEnabled(false);
                	btnInput.setEnabled(false);
                	btnOutput.setEnabled(true);
                }
                else if(e.getStateChange() == ItemEvent.DESELECTED){
                	txtInputUrl.setEnabled(true);
                	btnInput.setEnabled(true);
                	btnOutput.setEnabled(false);
                }
            }
        });
			
		txtInputUrl = new JTextField();
		txtInputUrl.setEditable(false);
		txtInputUrl.setBounds(6, 42, 301, 20);
		panel.add(txtInputUrl);
		txtInputUrl.setColumns(10);
		
		JLabel lblProtect = new JLabel("Please select the file that you want to protect:");
		lblProtect.setBounds(10, 22, 297, 14);
		panel.add(lblProtect);
		
		JButton btnEncrypt = new JButton("Encrypt");
		btnEncrypt.setToolTipText("Start encryption operation when all fields are filled");
		btnEncrypt.setEnabled(false);
		btnEncrypt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// in case user wants to encrypt current scan report
				
				if(cbxReport.isSelected()) {
					File f2=null;
					try {
						CreateNewFile.createFile(chooser.getSelectedFile()+"");
						InputStream is = null;
						f2 = chooser.getSelectedFile();
						try {
							is = new FileInputStream(f2);
						} catch (FileNotFoundException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
						content2 = null;
						try {
							content2 = new byte[is.available()];			
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						try {
							is.read(content2);
						} catch (IOException e3) {
							// TODO Auto-generated catch block
							e3.printStackTrace();
						}
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					getEncryptionType(content2);
				}	
				else {	
					
					// in case user wants to encrypt other .txt file
					getEncryptionType(content);
				}
				
			}
		});
		btnEncrypt.setBounds(244, 397, 89, 23);
		contentPane.add(btnEncrypt);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setToolTipText("Return to main screen without encrypting a file");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancel.setBounds(337, 397, 89, 23);
		contentPane.add(btnCancel);
		
		// opens application website for further documentation
		
		JButton btnHelp = new JButton("Help");
		btnHelp.setToolTipText("Visit us online to get further guidance");
		btnHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
		
		
		btnHelp.setBounds(35, 397, 89, 23);
		contentPane.add(btnHelp);
		
		JLabel lblEncryption = new JLabel("Encryption algorithm:");
		lblEncryption.setBounds(22, 300, 104, 14);
		contentPane.add(lblEncryption);
			
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Output file", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1.setLayout(null);
		panel_1.setBounds(10, 127, 416, 79);
		contentPane.add(panel_1);
		
		btnOutput = new JButton("Browse");
		btnOutput.setToolTipText("Define where the encrypted final text file will be stored");
		btnOutput.setEnabled(false);
		btnOutput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// continuous loop to ensure that no file is overwritten automatically

				boolean acceptable = false;
				do {		
					
					// shows a modal dialog containing the file chooser to define the output file directory
					chooser = new JFileChooser(); 
				    chooser.setCurrentDirectory(new java.io.File("."));
				    chooser.setDialogTitle("Save To");
				    chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				    chooser.setAcceptAllFileFilterUsed(false);
				    int userSelection = chooser.showSaveDialog(null);
					if (userSelection == JFileChooser.APPROVE_OPTION) {
						File fileToSave = new File(chooser.getSelectedFile()+".txt");
				            if (fileToSave.exists()) {
				                int result = JOptionPane.showConfirmDialog(null, "The file already exists, overwrite?","Existing file", JOptionPane.YES_NO_CANCEL_OPTION);
					                if (result == JOptionPane.YES_OPTION) {
					        			txtOutputUrl.setText(chooser.getSelectedFile()+"");
										String url = chooser.getSelectedFile()+"";			    	
										url = url.substring(0, url.lastIndexOf("\\"));
										txtRecoveryKeyUrl.setText(url);
										cboEncryption.setEnabled(true);
										btnEncrypt.setEnabled(true);
										acceptable = true;
					                }
				            }
				            else {
								txtOutputUrl.setText(chooser.getSelectedFile()+"");
								String url = chooser.getSelectedFile()+"";			    	
								url = url.substring(0, url.lastIndexOf("\\"));
								txtRecoveryKeyUrl.setText(url);
								cboEncryption.setEnabled(true);
								btnEncrypt.setEnabled(true);
								acceptable = true;
				            }
					}
					else {
						acceptable = true;
					}
				 } while (!acceptable);

			}
		});
		btnOutput.setBounds(317, 41, 89, 23);
		panel_1.add(btnOutput);
		
		txtOutputUrl = new JTextField();
		txtOutputUrl.setEditable(false);
		txtOutputUrl.setColumns(10);
		txtOutputUrl.setBounds(6, 42, 301, 20);
		panel_1.add(txtOutputUrl);
		
		JLabel lblDestination = new JLabel("Please define a destination folder to save the file:");
		lblDestination.setBounds(10, 22, 297, 14);
		panel_1.add(lblDestination);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Recovery key", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_2.setLayout(null);
		panel_2.setBounds(10, 206, 416, 79);
		contentPane.add(panel_2);
		
		txtRecoveryKeyUrl = new JTextField();
		txtRecoveryKeyUrl.setToolTipText("Defines where the encrypted file recovery key will be stored");
		txtRecoveryKeyUrl.setColumns(10);
		txtRecoveryKeyUrl.setBounds(6, 42, 400, 20);
		txtRecoveryKeyUrl.setEnabled(false);
		panel_2.add(txtRecoveryKeyUrl);
		
		JLabel lblRecovery = new JLabel("Recovery key destination folder:");
		lblRecovery.setBounds(10, 22, 297, 14);
		panel_2.add(lblRecovery);
	}
	
	/**
	 * This method shows a file chooser dialog, gets the file, and reads its content
	 * @return the selected file content 
	 */
	public static byte[] getFile() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Open");
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Documents (*.txt)", "txt");
		fileChooser.setFileFilter(filter);
		fileChooser.addChoosableFileFilter(filter);
		int userSelection = fileChooser.showOpenDialog(null);
		if (userSelection == JFileChooser.APPROVE_OPTION) {
			
			file = fileChooser.getSelectedFile();
			txtInputUrl.setText(file+"");
			btnOutput.setEnabled(true);
				
			InputStream is = null;
			try {
				is = new FileInputStream(file);
			} catch (FileNotFoundException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			content = null;
			try {
				content = new byte[is.available()];			
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				is.read(content);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
			return content;
		}
		return null;

	}
	
	/**
	 * This method encrypts data with RC4 encryption algorithm
	 * @param key used to decipher the text
	 * @param content the text
	 * @return encrypted text
	 */
	public static byte[] encryptFileRC4(Key key, byte[] content) {
		Cipher cipher;
		byte[] encrypted = null;
		try {
			cipher = Cipher.getInstance("RC4");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			encrypted = cipher.doFinal(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encrypted;
	}
	
	
	/**
	 * This method encrypts data with AES encryption algorithm
	 * @param key used to decipher the text
	 * @param content the text
	 * @return encrypted text
	 */
	public static byte[] encryptFileAES(Key key, byte[] content) {
		Cipher cipher;
		byte[] encrypted = null;
		try {
			cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			encrypted = cipher.doFinal(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encrypted;
	}
	
	/**
	 * This method encrypts data with DES encryption algorithm
	 * @param key used to cipher/decipher the text
	 * @param content the text
	 * @return encrypted text
	 */
	public static byte[] encryptFileDES(Key key, byte[] content) {
		Cipher cipher;
		byte[] encrypted = null;
		try {
			cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			encrypted = cipher.doFinal(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encrypted;
	}
	
	/**
	 * This method saves the encrypted text as an new file in drive
	 * @param bytes
	 * @param fileChooser
	 */
	public static void saveFile(byte[] bytes, JFileChooser fileChooser) throws IOException {
		File fileToSave=null;
		fileToSave = new File(fileChooser.getSelectedFile()+".txt");
		FileOutputStream fos = new FileOutputStream(fileToSave);
		fos.write(bytes);
		fos.close();
	}
	
	/**
	 * This method supplements and completes the encryption process
	 * Also generates the encryption-decryption key as output in drive 
	 * @param content
	 */
	public void getEncryptionType(byte [] content) {
		
		if(cboEncryption.getSelectedItem().equals("Rivest Cipher 4 (RC4)")){
			
			try {
				KeyGenerator keyGenerator;
				keyGenerator = KeyGenerator.getInstance("RC4");
				Key key = keyGenerator.generateKey();
				byte[] encoded = key.getEncoded();
				byte[] encrypted = encryptFileRC4(key, content);				
				saveFile(encrypted, chooser);
				File fileToSave = new File(chooser.getSelectedFile()+"_key.txt");
				FileOutputStream fos = new FileOutputStream(fileToSave);
				fos.write(encoded);
				fos.close();
			} catch (NoSuchAlgorithmException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}		
		}
		
		else if(cboEncryption.getSelectedItem().equals("Advanced Encryption Standard (AES) (256-bit key)")) {
			
			try {
				KeyGenerator keyGenerator;
				keyGenerator = KeyGenerator.getInstance("AES");
				keyGenerator.init(256);
				Key key = keyGenerator.generateKey();
				byte[] encoded = key.getEncoded();
				byte[] encrypted = encryptFileAES(key, content);				
				saveFile(encrypted, chooser);
				File fileToSave = new File(chooser.getSelectedFile()+"_key.txt");
				FileOutputStream fos = new FileOutputStream(fileToSave);
				fos.write(encoded);
				fos.close();
				
			} catch (NoSuchAlgorithmException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		else if(cboEncryption.getSelectedItem().equals("Advanced Encryption Standard (AES) (128-bit key)")) {
			
			try {
				KeyGenerator keyGenerator;
				keyGenerator = KeyGenerator.getInstance("AES");
				keyGenerator.init(128);
				Key key = keyGenerator.generateKey();
				byte[] encoded = key.getEncoded();
				byte[] encrypted = encryptFileAES(key, content);				
				saveFile(encrypted, chooser);
				File fileToSave = new File(chooser.getSelectedFile()+"_key.txt");
				FileOutputStream fos = new FileOutputStream(fileToSave);
				fos.write(encoded);
				fos.close();
				
			} catch (NoSuchAlgorithmException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	    else {
	    	
			try {
				KeyGenerator keyGenerator;
				keyGenerator = KeyGenerator.getInstance("DES");
				keyGenerator.init(56);
				Key key = keyGenerator.generateKey();						
				byte[] encoded = key.getEncoded();
				byte[] encrypted = encryptFileDES(key, content);
				saveFile(encrypted, chooser);										
				File fileToSave = new File(chooser.getSelectedFile()+"_key.txt");
				FileOutputStream fos = new FileOutputStream(fileToSave);
				fos.write(encoded);
				fos.close();

			} catch (NoSuchAlgorithmException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} 
		JOptionPane.showMessageDialog(null,"Encryption is complete");
		dispose();
	}
}
