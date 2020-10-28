import java.awt.Desktop;
import java.awt.SystemColor;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.awt.BorderLayout;

/**
 * This class creates a window to read previous scan reports or in general text files, regardless if they are encrypted or decrypted
 */
public class Read {
	
	public static JFrame frame;
	private JPanel contentPane;
	static JTextArea textArea;
	final static String newline = "\n";


	public Read(List<String> contents, String filename) {
		
		try {
			// setting the window current look and feel
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		// declaring all GUI elements with their properties for the window OPTIONS
		
		frame = new JFrame();
		frame.setVisible(true);
		frame.getContentPane().setBackground(SystemColor.window);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.setBackground(SystemColor.window);
		frame.setIconImage(new ImageIcon(this.getClass().getResource("/icon.png")).getImage());
		frame.setTitle(" " +filename+ " - Netstats (Read MODE)");

		
		frame.setBounds(100, 100, 680, 510);
		
		ActionListener menuListener = new MenuActionListener();
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBorderPainted(false);
		frame.setJMenuBar(menuBar);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		frame.setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		
		JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        menuBar.add(fileMenu);
        
        JMenuItem closeMenuItem = new JMenuItem("Close", KeyEvent.VK_C);
        closeMenuItem.addActionListener(menuListener);
        fileMenu.add(closeMenuItem);
     
        fileMenu.addSeparator();

        JMenuItem exitMenuItem = new JMenuItem("Exit", KeyEvent.VK_X);
        exitMenuItem.addActionListener(menuListener);
        fileMenu.add(exitMenuItem);

        JMenu editMenu = new JMenu("Edit");
        editMenu.setMnemonic(KeyEvent.VK_E);
        menuBar.add(editMenu);
        
        JMenuItem cutMenuItem = new JMenuItem("Cut");
        cutMenuItem.addActionListener(menuListener);
        KeyStroke ctrlXKeyStroke = KeyStroke.getKeyStroke("control X");
        cutMenuItem.setAccelerator(ctrlXKeyStroke);
        editMenu.add(cutMenuItem);
        
        JMenuItem copyMenuItem = new JMenuItem("Copy");
        copyMenuItem.addActionListener(menuListener);
        KeyStroke ctrlCKeyStroke = KeyStroke.getKeyStroke("control C");
        copyMenuItem.setAccelerator(ctrlCKeyStroke);
        editMenu.add(copyMenuItem);
        
        JMenuItem pasteMenuItem = new JMenuItem("Paste");
        pasteMenuItem.addActionListener(menuListener);
        KeyStroke ctrlVKeyStroke = KeyStroke.getKeyStroke("control V");
        pasteMenuItem.setAccelerator(ctrlVKeyStroke);
        editMenu.add(pasteMenuItem);
        
        JMenuItem selectAllMenuItem = new JMenuItem("Select All");
        selectAllMenuItem.addActionListener(menuListener);
        KeyStroke ctrlAKeyStroke = KeyStroke.getKeyStroke("control A");
        selectAllMenuItem.setAccelerator(ctrlAKeyStroke);
        editMenu.add(selectAllMenuItem);

        JMenuItem printMenuItem = new JMenuItem("Print...", KeyEvent.VK_P);
        printMenuItem.addActionListener(menuListener);
        KeyStroke ctrlPKeyStroke = KeyStroke.getKeyStroke("control P");
        printMenuItem.setAccelerator(ctrlPKeyStroke);
        editMenu.add(printMenuItem);
   			
        JMenu helpMenu = new JMenu("Help");
        helpMenu.setMnemonic(KeyEvent.VK_E);
        menuBar.add(helpMenu);
        
        // opens application website for further documentation
    
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
				
        for (String line : contents) {
        	textArea.append(line+newline);
        }				
	}
	
	 static class MenuActionListener implements ActionListener {
	        public void actionPerformed(ActionEvent actionEvent) {	        	
        		if(actionEvent.getActionCommand()=="Close") {  
        			frame.dispose();
        		}
        		
        		if(actionEvent.getActionCommand()=="Exit") {  
        			System.exit(0);
        		}
        		
        		if(actionEvent.getActionCommand()=="Cut") {
        			textArea.cut();
        		}
        		
        		if(actionEvent.getActionCommand()=="Copy") {  
        			textArea.copy();
        		}
        		
        		if(actionEvent.getActionCommand()=="Paste") {
        			textArea.paste();
        		}
        		
        		if(actionEvent.getActionCommand()=="Select All") {  
        			textArea.selectAll();
        		}
        		
        		if(actionEvent.getActionCommand()=="Print...") {  
        			Print.printToPrinter(textArea);
        		}
	        }
	    } 
	 	
		/**
		 * This method provides access to the value the variable holds
		 * @return the text content
		 */
	    public static JTextArea getTextArea() {
	        return textArea;
	    }
}
