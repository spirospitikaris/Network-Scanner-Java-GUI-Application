import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.awt.event.ActionEvent;

/**
 * This class creates a window that shows the tools that were used in the application development process
 */
public class About extends JFrame {

	private JPanel contentPane;
		
  	public About() {
	  	AboutGUI();
        Options.CenteredFrame(this); 
    }

	public void AboutGUI() {
		
		// declaring all GUI elements with their properties for the window ABOUT
		
		setTitle("About Netstats");
		setIconImage(new ImageIcon(this.getClass().getResource("/icon.png")).getImage());
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 432, 486);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
				
		JLabel lblNetstats = new JLabel("Netstats");
		lblNetstats.setFont(new Font("Gadugi", Font.BOLD, 33));
		lblNetstats.setBounds(233, 55, 186, 39);
		contentPane.add(lblNetstats);
		
		JLabel lblNetwork = new JLabel("Network Scanner");
		lblNetwork.setFont(new Font("Gadugi", Font.PLAIN, 16));
		lblNetwork.setBounds(234, 95, 144, 14);
		contentPane.add(lblNetwork);
		
		JLabel lblVersion = new JLabel("Version 1.0");
		lblVersion.setFont(new Font("Gadugi", Font.PLAIN, 16));
		lblVersion.setBounds(234, 126, 89, 14);
		contentPane.add(lblVersion);
				
		JLabel lblJava = new JLabel("JAVA JDK:");
		lblJava.setBounds(214, 229, 59, 14);
		contentPane.add(lblJava);
		
		JLabel lblJdk = new JLabel("1.8");
		lblJdk.setBounds(214, 241, 46, 14);
		contentPane.add(lblJdk);
		
		JLabel lblEclipse = new JLabel("Eclipse IDE: 2019.19");
		lblEclipse.setBounds(214, 275, 129, 14);
		contentPane.add(lblEclipse);
		
		JLabel lblGui = new JLabel("GUI AWT and SWIFT packages");
		lblGui.setBounds(214, 288, 153, 14);
		contentPane.add(lblGui);
		
		JLabel lblApache = new JLabel("Apache Commons Libraries:");
		lblApache.setBounds(214, 319, 144, 14);
		contentPane.add(lblApache);		
		
		JLabel lblCommonsCodec = new JLabel("commons codec 1.9");
		lblCommonsCodec.setBounds(214, 331, 129, 14);
		contentPane.add(lblCommonsCodec);
		
		JLabel lblCommonsIo = new JLabel("commons io 2.6");
		lblCommonsIo.setBounds(214, 344, 129, 14);
		contentPane.add(lblCommonsIo);
		
		JLabel lblJavaIcon = new JLabel("");
		lblJavaIcon.setBounds(105, 225, 135, 30);
		contentPane.add(lblJavaIcon);
		lblJavaIcon.setIcon(new ImageIcon(this.getClass().getResource("/java.png")));
		
		JLabel lblEclipseIcon = new JLabel("");
		lblEclipseIcon.setBounds(105, 270, 153, 39);
		contentPane.add(lblEclipseIcon);
		lblEclipseIcon.setIcon(new ImageIcon(this.getClass().getResource("/eclipse.png")));
		
		JLabel lblApacheIcon = new JLabel("");
		lblApacheIcon.setBounds(106, 315, 153, 39);
		contentPane.add(lblApacheIcon);
		lblApacheIcon.setIcon(new ImageIcon(this.getClass().getResource("/apache.png")));
		
		JLabel lblNetstatsIcon = new JLabel("");
		lblNetstatsIcon.setBounds(32, 25, 191, 175);
		contentPane.add(lblNetstatsIcon);
		lblNetstatsIcon.setIcon(new ImageIcon(this.getClass().getResource("/logo.png")));
		
		JButton btnMore = new JButton("More");
		btnMore.addActionListener(new ActionListener() {
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
		btnMore.setBounds(164, 381, 89, 23);
		contentPane.add(btnMore);
		
		JLabel lblCopyright = new JLabel("Copyright (c) 2020. All Rights Reserved. Trademark Legal Notice.");
		lblCopyright.setBounds(48, 411, 326, 14);
		contentPane.add(lblCopyright);
		
		JLabel lblDisclaimer = new JLabel("All product names, logos, and brands are property of their respective owners.");
		lblDisclaimer.setBounds(21, 424, 387, 14);
		contentPane.add(lblDisclaimer);
		
	}
}
