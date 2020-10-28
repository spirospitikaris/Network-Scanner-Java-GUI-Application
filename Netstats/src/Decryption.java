import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.border.Border;
import java.awt.Color;
import java.awt.Desktop;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.border.EtchedBorder;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Key;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

/**
 * This class draws the decryption window and facilitates the file decryption function
 */
public class Decryption extends JFrame {
	
	// declaring variables

	private JPanel contentPane;
	private static JTextField txtInputFileUrl;
	private JTextField txtRecoveryKeyUrl;
	static byte[] content;
	static JFileChooser fileChooser;
	static JFileChooser keyFileChooser;
	static File f;
	static JButton btnInputKey;
	private static JTextField txtDestinationUrl;
	public static JComboBox<String> cboEncryption;
	
	
    public Decryption() {
	    DecryptionGUI();
        Options.CenteredFrame(this); 
    }

	public void DecryptionGUI() {
		
		// declaring all GUI elements with their properties
		
		setTitle("Decrypt file");
        setIconImage(new ImageIcon(this.getClass().getResource("/icon.png")).getImage());
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 450, 434);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		String title = "Input file";
		Border border = BorderFactory.createTitledBorder(title);
		panel.setBorder(border);
		panel.setBounds(10, 11, 416, 79);
		contentPane.add(panel);
		panel.setLayout(null);
		
		txtInputFileUrl = new JTextField();
		txtInputFileUrl.setEnabled(false);
		txtInputFileUrl.setEditable(false);
		txtInputFileUrl.setBounds(6, 42, 301, 20);
		panel.add(txtInputFileUrl);
		txtInputFileUrl.setColumns(10);
		
		// shows a modal dialog containing the file chooser
		
		JButton btnInput = new JButton("Browse");
		btnInput.setToolTipText("Find and select the text file that you want to decrypt");
		btnInput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				getFile();				
			}
		});
		btnInput.setBounds(317, 41, 89, 23);
		panel.add(btnInput);
				
		JLabel lblDecrypt = new JLabel("Please select the file that you want to decrypt:");
		lblDecrypt.setBounds(10, 22, 297, 14);
		panel.add(lblDecrypt);
		
		JButton btnDecrypt = new JButton("Decrypt");
		btnDecrypt.setToolTipText("Start decryption operation when all fields are filled");
		btnDecrypt.setEnabled(false);
		btnDecrypt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Key key;
				byte[] encoded;
				byte[] keyFileContent=null;
				
				// reads the content of a chosen file to get encryption key string of bits 
				
				File f2 = keyFileChooser.getSelectedFile();
				
				InputStream is2 = null;
				try {
					is2 = new FileInputStream(f2);
				} catch (FileNotFoundException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				keyFileContent = null;
				try {
					keyFileContent = new byte[is2.available()];
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					is2.read(keyFileContent);
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}

				
				encoded = keyFileContent;
				key=null;
				
				// the potential decryption key gets formed from a combination of the above obtained information and the user choice regarding the prior encryption algorithm used 

				if(cboEncryption.getSelectedItem().equals("Rivest Cipher 4 (RC4)")){
					key = new SecretKeySpec(encoded, "RC4");
				}
				else if(cboEncryption.getSelectedItem().equals("Advanced Encryption Standard (AES) (256-bit key)")||(cboEncryption.getSelectedItem().equals("Advanced Encryption Standard (AES) (128-bit key)"))) {
					key = new SecretKeySpec(encoded, "AES");
				}
				else if(cboEncryption.getSelectedItem().equals("Data Encryption Standard (DES) (56-bit key)")) {
					key = new SecretKeySpec(encoded, "DES");
				}
				
				byte[] decrypted = decryptFile(key, content);
				
			
				try {
					
					// regardless the user input, the application finishes the decryption process-
					// by creating a new decrypted file/or a blank file in the same directory with the chosen key file
					
					saveFile(decrypted, keyFileChooser);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				JOptionPane.showMessageDialog(null,"Decryption is complete");
				dispose();
			}
		});
		btnDecrypt.setBounds(242, 350, 89, 23);
		contentPane.add(btnDecrypt);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setToolTipText("Return to main screen without decrypting a file");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancel.setBounds(335, 350, 89, 23);
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
		btnHelp.setBounds(33, 350, 89, 23);
		contentPane.add(btnHelp);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Recovery Key", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1.setLayout(null);
		panel_1.setBounds(10, 93, 416, 143);
		contentPane.add(panel_1);
		
		JLabel label_3 = new JLabel("New label");
		label_3.setBounds(6, -11, 46, 14);
		panel_1.add(label_3);
		
		txtRecoveryKeyUrl = new JTextField();
		txtRecoveryKeyUrl.setEnabled(false);
		txtRecoveryKeyUrl.setColumns(10);
		txtRecoveryKeyUrl.setBounds(6, 40, 301, 20);
		panel_1.add(txtRecoveryKeyUrl);
		
		// shows a modal dialog containing the file chooser to select file recovery key
		
		btnInputKey = new JButton("Browse");
		btnInputKey.setToolTipText("Enter the recovery key here for the file that you want to decrypt");
		btnInputKey.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				keyFileChooser = new JFileChooser();
				keyFileChooser.setDialogTitle("Open");
				keyFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Documents (*.txt)", "txt");
				keyFileChooser.setFileFilter(filter);
				keyFileChooser.addChoosableFileFilter(filter);
				int userSelection = keyFileChooser.showOpenDialog(null);
				
				if (userSelection == JFileChooser.APPROVE_OPTION) {
					txtRecoveryKeyUrl.setText(keyFileChooser.getSelectedFile()+"");
				}
				
				btnDecrypt.setEnabled(true);
			}
		});
		btnInputKey.setEnabled(false);
		btnInputKey.setBounds(317, 39, 89, 23);
		panel_1.add(btnInputKey);
		
		JLabel lblRecovery = new JLabel("Enter the recovery key for this file:");
		lblRecovery.setBounds(10, 24, 297, 14);
		panel_1.add(lblRecovery);
		
		JLabel lblEncryptionAlgorithm = new JLabel("Encryption algorithm used:");
		lblEncryptionAlgorithm.setBounds(10, 71, 140, 14);
		panel_1.add(lblEncryptionAlgorithm);
		
		cboEncryption = new JComboBox<String>();
		cboEncryption.setEnabled(false);
		cboEncryption.setBounds(6, 93, 400, 22);
		cboEncryption.addItem("Rivest Cipher 4 (RC4)");
		cboEncryption.addItem("Advanced Encryption Standard (AES) (256-bit key)");
		cboEncryption.addItem("Advanced Encryption Standard (AES) (128-bit key)");
	    cboEncryption.addItem("Data Encryption Standard (DES) (56-bit key)");
		panel_1.add(cboEncryption);
		
		JLabel lblCompatibility = new JLabel("File compatible algorithm must been chosen, otherwise the file can't be decrypted");
		lblCompatibility.setForeground(Color.DARK_GRAY);
		lblCompatibility.setBounds(10, 120, 396, 14);
		panel_1.add(lblCompatibility);
		
		JPanel panel_2 = new JPanel();
		panel_2.setLayout(null);
		panel_2.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_2.setBounds(10, 247, 416, 68);
		contentPane.add(panel_2);
		
		txtDestinationUrl = new JTextField();
		txtDestinationUrl.setToolTipText("Defines where the decrypted text file will be stored. ");
		txtDestinationUrl.setEditable(false);
		txtDestinationUrl.setEnabled(false);
		txtDestinationUrl.setColumns(10);
		txtDestinationUrl.setBounds(6, 31, 400, 20);
		panel_2.add(txtDestinationUrl);
		
		JLabel lblDestination = new JLabel("Decrypted file destination folder:");
		lblDestination.setBounds(10, 11, 297, 14);
		panel_2.add(lblDestination);
	}
	
	/**
	 * This method shows a file chooser dialog, gets the file, and reads its content
	 * @return the selected file content 
	 */
	public static byte[] getFile() {
		
		fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Open");
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Documents (*.txt)", "txt");
		fileChooser.setFileFilter(filter);
		fileChooser.addChoosableFileFilter(filter);
		int userSelection = fileChooser.showOpenDialog(null);
			if (userSelection == JFileChooser.APPROVE_OPTION) {
				
				f = fileChooser.getSelectedFile();
				txtInputFileUrl.setText(fileChooser.getSelectedFile()+"");
				String url = fileChooser.getSelectedFile()+"";			    	
		        url = url.substring(0, url.lastIndexOf("\\"));
				txtDestinationUrl.setText(url);
				
				btnInputKey.setEnabled(true);
				cboEncryption.setEnabled(true);
					
				InputStream is = null;
				try {
					is = new FileInputStream(f);
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
					System.out.println("343341");
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
				return content;
			}
		return null;		
	}
	
	/**
	 * This method saves the decrypted text as an new file in drive
	 * @param bytes
	 * @param fileChooser
	 */
	public static void saveFile(byte[] bytes, JFileChooser fileChooser) throws IOException {
		
		File fileToSave=null;	
		fileToSave = new File(fileChooser.getSelectedFile()+"_decrypted.txt");
		FileOutputStream fos = new FileOutputStream(fileToSave);
		fos.write(bytes);
		fos.close();
	}
	
	/**
	 * This method decrypts data according selected encryption algorithm
	 * @param key used to cipher/decipher the text
	 * @param content the text
	 * @return encrypted text
	 */
	public static byte[] decryptFile(Key key, byte[] textCryp) {
		
		Cipher cipher;
		byte[] decrypted = null;
		try {
			if(cboEncryption.getSelectedItem().equals("Rivest Cipher 4 (RC4)")){
				cipher = Cipher.getInstance("RC4");
				cipher.init(Cipher.DECRYPT_MODE, key);
				decrypted = cipher.doFinal(textCryp);	
			}
			else if(cboEncryption.getSelectedItem().equals("Advanced Encryption Standard (AES) (256-bit key)") || (cboEncryption.getSelectedItem().equals("Advanced Encryption Standard (AES) (128-bit key)"))) {
				cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
				cipher.init(Cipher.DECRYPT_MODE, key);
				decrypted = cipher.doFinal(textCryp);
			}
			else if(cboEncryption.getSelectedItem().equals("Data Encryption Standard (DES) (56-bit key)")) {
				cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
				cipher.init(Cipher.DECRYPT_MODE, key);
				decrypted = cipher.doFinal(textCryp);					
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,"Invalid encryption algorithm. Decryption couldn't complete.","Decryption error",JOptionPane.ERROR_MESSAGE);
		}

		return decrypted;
	}

}
