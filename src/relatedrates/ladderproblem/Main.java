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
            FlatLaf lookAndFeel;
            /*
            if (Configuration.getInstance().is("ui.theme.dark.mode.enabled", false)) {
                lookAndFeel = new FlatDarkLaf();
            } else {
                lookAndFeel = new FlatLightLaf();
            }
            */
            lookAndFeel = new FlatLightLaf();
            JFrame.setDefaultLookAndFeelDecorated(true);
            JDialog.setDefaultLookAndFeelDecorated(true);
            UIManager.setLookAndFeel(lookAndFeel);
        } catch (Exception e) {
        	System.err.println("Could not set look and feel for the application");
        }
		
		final AnimationFrame animationFrame = new AnimationFrame();
		final InfoFrame infoFrame = new InfoFrame(animationFrame);
	}

}
