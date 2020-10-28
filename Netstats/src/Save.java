import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * This class facilitates the process of saving scan report results to a .txt file, after user request
 */
public class Save extends JFrame {

	public Save() {		
		showSaveFileDialog();
	}

	public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) { 
        	
        }
        	SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				new Save();
			}
		});
	 }
	
	/**
	 * This method shows a modal dialog, in which the user determines where the new file will be saved and its name
	 */
	private void showSaveFileDialog() {
		
		setIconImage(new ImageIcon(this.getClass().getResource("/icon.png")).getImage());
		boolean acceptable = false;
			
			// continuous loop to ensure that no file is overwritten automatically
		
			do {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Save As");
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Documents (*.txt)", "txt");
				fileChooser.setFileFilter(filter);
				fileChooser.addChoosableFileFilter(filter);
				int userSelection = fileChooser.showSaveDialog(this);
				if (userSelection == JFileChooser.APPROVE_OPTION) {
					File fileToSave = new File(fileChooser.getSelectedFile()+".txt");
					
		            if (fileToSave.exists()) {
		                int result = JOptionPane.showConfirmDialog(this, "The file already exists, overwrite?", "Existing file", JOptionPane.YES_NO_CANCEL_OPTION);
		                if (result == JOptionPane.YES_OPTION) {
		        			try {
		        				// responsible to create the file in the specified path
		        				CreateNewFile.createFile(fileToSave.getAbsolutePath());
		        				JOptionPane.showMessageDialog(null,"File has been created successfully");
		        				acceptable = true;
		        			} catch (IOException e) {
		        				// TODO Auto-generated catch block
		        				e.printStackTrace();
		        				acceptable = true;
		        			}
		                }
				    }
		            else {
						try {       
							CreateNewFile.createFile(fileToSave.getAbsolutePath());
							JOptionPane.showMessageDialog(null,"File has been created successfully");
							acceptable = true;
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							acceptable = true;
						}
		            }
			   }
				else {
					acceptable = true;
				}
		  } while (!acceptable);
	}

}