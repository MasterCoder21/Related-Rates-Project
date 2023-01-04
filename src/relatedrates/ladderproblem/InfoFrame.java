package relatedrates.ladderproblem;

import java.awt.Dimension;
import java.io.IOException;
import java.net.URL;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

public class InfoFrame extends JFrame {

	private static final long serialVersionUID = 4848473811062671713L;
	
	private JEditorPane infoArea;
	private JScrollPane infoScrollPane;
	private URL helpDocument;
	
	public InfoFrame(final AnimationFrame parent) {
		super("Related Rates Project - Ladder Problem");

		this.setSize(600, 725);
		// this.setLocationRelativeTo(parent);
		this.setLocation(parent.getX() + parent.getWidth() - 13, parent.getY());
		this.setResizable(false);
		
		this.setupAndAddInfoArea();

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	private void setupAndAddInfoArea() {
		this.infoArea = new JEditorPane();
		this.infoArea.setSize(new Dimension(500, 725));
		this.infoArea.setPreferredSize(new Dimension(500, 725));
		this.infoArea.setEditable(false);
		
		this.helpDocument = this.getClass().getClassLoader().getResource("assets/info.html");
		
		if (this.helpDocument != null) {
		    try {
		        this.infoArea.setPage(this.helpDocument);
		    } catch (IOException e) {
		        System.err.println("Attempted to read a bad URL: " + this.helpDocument);
		    }
		} else {
		    System.err.println("Couldn't find file: info.html");
		}
		
		this.infoScrollPane = new JScrollPane(this.infoArea);
		this.infoScrollPane.setVerticalScrollBarPolicy(
		                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		this.add(this.infoScrollPane);
	}

}
