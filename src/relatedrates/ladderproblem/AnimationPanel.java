package relatedrates.ladderproblem;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class AnimationPanel extends JPanel {
	
	private int END_FRAMES = 500;
	private int FRAMES_MIN = 0;
	private int FRAMES_MAX = 1999 + END_FRAMES;
	private final double HEIGHT_MIN = 0;
	private final double HEIGHT_MAX = 280; // 285
	private final double HEIGHT_DIFF = HEIGHT_MAX - HEIGHT_MIN;
	private double HEIGHT_PER_FRAME = HEIGHT_DIFF / (FRAMES_MAX - END_FRAMES + 1);
	private final double BEAM_LENGTH = HEIGHT_DIFF;

	private int animationSpeed = 50;
	private int frames;
	private double height = HEIGHT_MIN;
	private double craneHeight = HEIGHT_MIN;
	
	private final int offset = 200;
	
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

	public void update(int direction) {
		this.frames += direction;
		if (this.frames > FRAMES_MAX)
			this.frames = FRAMES_MIN;
		if (this.frames < FRAMES_MIN)
			this.frames = FRAMES_MAX;
		this.height += direction * HEIGHT_PER_FRAME;
		if (this.height > HEIGHT_MAX + (500 * HEIGHT_PER_FRAME))
			this.height = HEIGHT_MIN;
		if (this.height < HEIGHT_MIN)
			this.height = HEIGHT_MAX + (500 * HEIGHT_PER_FRAME);
		this.repaint();
	}
	
	public void resetFrames() {
		this.frames = FRAMES_MIN;
		this.height = HEIGHT_MIN;
	}
	
	public void updateFramesCount(int maxframes) {
		FRAMES_MAX = maxframes;
		HEIGHT_PER_FRAME = HEIGHT_DIFF / (FRAMES_MAX - END_FRAMES + 1);
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
		g.drawString("Frames: " + this.frames + "   Delay: " + this.animationSpeed + " ms",
				this.getWidth() / 2 - g.getFontMetrics()
						.stringWidth("Frames: " + this.frames + "   Delay: " + this.animationSpeed + " ms") / 2,
				this.getHeight() - 4 - g.getFontMetrics().getHeight());

		// Draw Building
		g.drawRect(offset - 80, this.getHeight() - 405, 80, 355)	;
		for (int c = 0; c < 3; c++) {
			for (int r = 0; r < 14; r++) {
				g.drawRect(offset - 80 + 5 + (25 * c), this.getHeight() - 405 + 5 + (25 * r), 20, 20);
			}
		}

		// Draw crane
		if (crane != null) {
			g.drawImage(this.crane, -42, (int) (13 + this.getHeight() - 50 - height - 212), 550, 500, this); // x was originally -75
		}
		
		// The following lines are for testing purposes
		// g.drawLine(offset + 42, 0, offset + 42, this.getHeight() - 50); // 167
		// g.drawLine(0, (int) this.getHeightAtCraneRope(), this.getWidth(), (int) this.getHeightAtCraneRope());
		
		// Draw triangle
		double otherLeg = this.calculateOtherLeg();
		g.setColor(Color.red);
		final Stroke prevStroke = ((Graphics2D) (g)).getStroke();
		((Graphics2D) (g)).setStroke(new BasicStroke(3));
		if(height < HEIGHT_MAX)
			g.drawLine(offset, (int) (this.getHeight() - 50 - height), (int) (offset + otherLeg), this.getHeight() - 50);
		else
			g.drawLine(offset, (int) (this.getHeight() - 50 - height), (int) (offset), (int) (this.getHeight() - 50 - (height - HEIGHT_MAX)));
		((Graphics2D) (g)).setStroke(prevStroke);
		
		// These are the other lines of the triangle (only need the hypotenuse)
		// g.drawLine(offset, this.getHeight() - 50, offset, (int) (this.getHeight() - 50 - height));
		// g.drawLine(offset, this.getHeight() - 50, (int) (offset + otherLeg), this.getHeight() - 50);
	}
	
	private double calculateOtherLeg() {
		double otherLeg = Math.pow(BEAM_LENGTH, 2) - Math.pow(height, 2);
		otherLeg = Math.sqrt(otherLeg);
		return otherLeg;
	}
	
	private double lineEquation(double x) {
		// Point: (offset, (int) (this.getHeight() - 50 - height))
		// Change in Y: this.getHeight() - 50 - height - this.getHeight() - 50 = -offset - height
		// Change in X: offset + otherLeg - offset = otherLeg
		// Slope: (-offset - height) / otherLeg
		double px = offset;
		double py = this.getHeight() - 50 - height;
		double slope = (-offset - height) / this.calculateOtherLeg();
		return (slope * (x - px)) + py;
	}
	
	private double getHeightAtCraneRope() {
		return lineEquation(offset);
	}
	

}
