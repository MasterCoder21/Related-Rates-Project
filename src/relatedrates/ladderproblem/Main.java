package relatedrates.ladderproblem;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;

public class Main {
	
	public static void main(String[] args) {
		try {
            UIManager.put("Button.arc", 4);
            JFrame.setDefaultLookAndFeelDecorated(true);
            JDialog.setDefaultLookAndFeelDecorated(true);
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
        	System.err.println("Could not set look and feel for the application");
		}

		new InfoFrame(new AnimationFrame());
	}

}
