import java.awt.*;
import javax.swing.*;

/**
 * This class implements a splash screen as object, with specified properties
 */
public class Splashscreen extends JWindow {

    private int duration;

    public Splashscreen(int d) {
        setDuration(d);
    }

    // method to show a title screen in the center
    
    public void showSplashScreen() {

        JPanel content = (JPanel) getContentPane();
        content.setBackground(Color.white);

        // setting the window's properties
        
        int width = 700;
        int height = 438;
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screen.width - width) / 2;
        int y = (screen.height - height) / 2;
        setBounds(x, y, width, height);

        // build the splash screen using an image 
        
        JLabel gifImage = new JLabel(new ImageIcon(this.getClass().getResource("/splash.gif")));
        content.add(gifImage, BorderLayout.CENTER);

        // display it
        setVisible(true);

        new resourceLoader().execute();
    }
    
    public class resourceLoader extends SwingWorker < Object, Object > {

        @Override
        protected Object doInBackground() throws Exception {
        	
            // wait before loading the main application frame
            try {
                Thread.sleep(5500);

            } catch (Exception e) {}
            return null;
        }
        @Override
        protected void done() {
            setVisible(false);
            Interface window =new Interface();
            System.out.println(window);
        }
    }
    public void showSplashAndExit() {
        showSplashScreen();
    }

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}
}