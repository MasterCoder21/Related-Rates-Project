package relatedrates.ladderproblem;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class AnimationPanel extends JPanel {

	private int FRAMES_MIN = 0;
	private int FRAMES_MAX = 1999;
	private final double HEIGHT_MIN = 0;
	private final double HEIGHT_MAX = 280; // 285
	private final double HEIGHT_DIFF = HEIGHT_MAX - HEIGHT_MIN;
	private double HEIGHT_PER_FRAME = HEIGHT_DIFF / (FRAMES_MAX + 1);
	private final double BEAM_LENGTH = HEIGHT_DIFF;

	private int animationSpeed = 50;
	private int frames;
	private double height = HEIGHT_MIN;
	
	/*
	private long timer = 0;
	private boolean timerOn = false;
	private boolean firstTime = true;
	*/

	private BufferedImage crane;

	public AnimationPanel() {
		this.setSize(500, 500);

		try {
			crane = ImageIO.read(this.getClass().getClassLoader().getResource("assets/crane.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println(this.HEIGHT_PER_FRAME);
	}
	
	/*
	public void setTimer(boolean enabled, boolean restart) {
		this.timerOn = enabled;
		if(restart || this.firstTime) {
			if(this.timerOn) {
				this.timer = System.currentTimeMillis();
			}
			else {
				this.timer = 0;
			}
			this.firstTime = false;
		}
		this.repaint();
	}
	*/

	public void update(int direction) {
		this.frames += direction;
		if (this.frames > FRAMES_MAX)
			this.frames = FRAMES_MIN;
		if (this.frames < FRAMES_MIN)
			this.frames = FRAMES_MAX;
		this.height += direction * HEIGHT_PER_FRAME;
		if (this.height > HEIGHT_MAX)
			this.height = HEIGHT_MIN;
		if (this.height < HEIGHT_MIN)
			this.height = HEIGHT_MAX;
		this.repaint();
	}
	
	public void resetFrames() {
		this.frames = FRAMES_MIN;
		this.height = HEIGHT_MIN;
	}
	
	public void updateFramesCount(int maxframes) {
		FRAMES_MAX = maxframes;
		HEIGHT_PER_FRAME = HEIGHT_DIFF / (FRAMES_MAX + 1);
	}

	public void setSpeed(int speed) {
		this.animationSpeed = speed;
		this.repaint();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		// Draw Floor & Text
		g.setColor(Color.black);
		g.fillRect(1, this.getHeight() - 50, this.getWidth() - 1, 2);
		g.setFont(g.getFont().deriveFont(Font.BOLD));
		((Graphics2D) (g)).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		/*
		g.drawString("Frames: " + this.frames + (this.timerOn ? "   Time: " + ((System.currentTimeMillis() - this.timer) / 1000) % 60 : "") + "   Delay: " + this.animationSpeed + " ms",
				this.getWidth() / 2 - g.getFontMetrics()
						.stringWidth("Frames: " + this.frames + (this.timerOn ? "   Time: " + ((System.currentTimeMillis() - this.timer) / 1000) % 60 : "") + "   Delay: " + this.animationSpeed + " ms") / 2,
				this.getHeight() - 4 - g.getFontMetrics().getHeight());
				*/
		g.drawString("Frames: " + this.frames + "   Delay: " + this.animationSpeed + " ms",
				this.getWidth() / 2 - g.getFontMetrics()
						.stringWidth("Frames: " + this.frames + "   Delay: " + this.animationSpeed + " ms") / 2,
				this.getHeight() - 4 - g.getFontMetrics().getHeight());

		// Draw Building
		g.drawRect(20, this.getHeight() - 405, 80, 355);
		for (int c = 0; c < 3; c++) {
			for (int r = 0; r < 14; r++) {
				g.drawRect(20 + 5 + (25 * c), this.getHeight() - 405 + 5 + (25 * r), 20, 20);
			}
		}

		// Draw crane
		if (crane != null) {
			g.drawImage(this.crane, -75, 13, 550, 500, this);
		}
		
		/*
		// Draw box
		g.drawLine(100, (int) (this.getHeight() - 50 - height), this.getWidth(),
				(int) (this.getHeight() - 50 - height));
				*/
		// Draw triangle
		double otherLeg = this.calculateOtherLeg();
		g.setColor(Color.red);
		// g.drawLine(100, this.getHeight() - 50, 100, (int) (this.getHeight() - 50 - height));
		g.drawLine(100, (int) (this.getHeight() - 50 - height), (int) (100 + otherLeg), this.getHeight() - 50);
		// g.drawLine(100, this.getHeight() - 50, (int) (100 + otherLeg), this.getHeight() - 50);

	}
	
	private double calculateOtherLeg() {
		double otherLeg = Math.pow(BEAM_LENGTH, 2) - Math.pow(height, 2);
		otherLeg = Math.sqrt(otherLeg);
		return otherLeg;
		
	}

}
