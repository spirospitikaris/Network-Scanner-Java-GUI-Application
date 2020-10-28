import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.commons.io.FileUtils;

/**
 * This class facilitates the process of opening a .txt file from the application
 */
public class Open extends JFrame {

	public Open() {
		showOpenFileDialog();
	}

	public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());            
        } catch (Exception e) { 
        	
        }

        SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				new Open();
			}
		});
	}
	
	/**
	 This method shows a modal dialog, in which the user determines what file will be opened
	 */
	private void showOpenFileDialog() {
		
		setIconImage(new ImageIcon(this.getClass().getResource("/icon.png")).getImage());
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Open");
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Documents (*.txt)", "txt");
		fileChooser.setFileFilter(filter);
		fileChooser.addChoosableFileFilter(filter);
		int userSelection = fileChooser.showOpenDialog(this);
		if (userSelection == JFileChooser.APPROVE_OPTION) {
			File fileToSave = fileChooser.getSelectedFile();
			
	        try {
	        	// reads the lines of a selected .txt file
	            List<String> contents = FileUtils.readLines(fileToSave, "UTF-8");
	            
	            // transfers the file content to Read window  
	            new Read(contents, fileChooser.getSelectedFile()+"");

	        } catch (IOException e) {
	            e.printStackTrace();
	        }
			
		}
	}
	
}

