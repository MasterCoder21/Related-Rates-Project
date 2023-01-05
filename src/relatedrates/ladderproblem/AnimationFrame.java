package relatedrates.ladderproblem;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class AnimationFrame extends JFrame implements ActionListener, ChangeListener, KeyListener, Runnable {

	private static final long serialVersionUID = 8550340662439054811L;

	private JButton startAnimation, restartAnimation, resetAnimation, stopAnimation;
	private JButton previousFrame, nextFrame;
	private JSlider animationSpeedSlider;
	private JSpinner framesCountBox;

	private AnimationPanel animationPanel;

	private Container northContainer, centerContainer, southMainContainer, southTopContainer, southBottomContainer;

	private int animationSpeed = 50;
	private boolean animating = false;

	private int framesCount = 2000;

	public AnimationFrame() {
		super("Related Rates Project - Ladder Problem");

		this.setSize(600, 725);
		this.setLocationRelativeTo(null);
		this.setLocation(this.getX() - this.getWidth() / 2, this.getY());
		this.setResizable(false);

		this.northContainer = new Container();
		this.northContainer.setLayout(new GridLayout(1, 4));
		this.northContainer.add(this.startAnimation = new JButton("Start Animation"));
		this.startAnimation.addActionListener(this);
		this.northContainer.add(this.restartAnimation = new JButton("Restart Animation"));
		this.restartAnimation.addActionListener(this);
		this.northContainer.add(this.resetAnimation = new JButton("Reset Animation"));
		this.resetAnimation.addActionListener(this);
		this.northContainer.add(this.stopAnimation = new JButton("Stop Animation"));
		this.stopAnimation.addActionListener(this);
		this.stopAnimation.setEnabled(false);
		this.add(this.northContainer, BorderLayout.NORTH);

		this.centerContainer = new Container();
		this.centerContainer.setLayout(new GridLayout(1, 1));
		this.centerContainer.add(this.animationPanel = new AnimationPanel());
		this.add(this.centerContainer, BorderLayout.CENTER);

		this.southMainContainer = new Container();
		this.southMainContainer.setLayout(new GridLayout(3, 2));
		this.southMainContainer.add(this.previousFrame = new JButton("Prev. Frame"));
		this.previousFrame.addActionListener(this);
		this.southMainContainer.add(this.nextFrame = new JButton("Next Frame"));
		this.nextFrame.addActionListener(this);
		final JLabel label1 = new JLabel("Animation Delay");
		label1.setHorizontalAlignment(JLabel.CENTER);
		this.southMainContainer.add(BorderLayout.WEST, label1);
		this.southMainContainer.add(BorderLayout.EAST,
				this.animationSpeedSlider = new JSlider(JSlider.HORIZONTAL, 0, 50, this.animationSpeed));
		this.animationSpeedSlider.setMajorTickSpacing(5);
		this.animationSpeedSlider.setMinorTickSpacing(1);
		this.animationSpeedSlider.setPaintLabels(true);
		this.animationSpeedSlider.addChangeListener(this);
		final JLabel label2 = new JLabel("Frames Count");
		label2.setHorizontalAlignment(JLabel.CENTER);
		this.southMainContainer.add(BorderLayout.WEST, label2);
		this.southMainContainer.add(BorderLayout.EAST,
				this.framesCountBox = new JSpinner(new SpinnerNumberModel(2000, 1000, 5000, 100)));
		this.framesCountBox.addChangeListener(this);
		this.framesCountBox.addKeyListener(this);
		this.add(this.southMainContainer, BorderLayout.SOUTH);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		if (e.getSource().equals(this.animationSpeedSlider)) {
			this.animationSpeed = this.animationSpeedSlider.getValue();
			this.animationPanel.setSpeed(this.animationSpeed);
		}
		if (e.getSource().equals(this.framesCountBox)) {
			this.framesCount = (int) this.framesCountBox.getValue();
			this.animationPanel.updateFramesCount(this.framesCount);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(this.startAnimation)) {
			this.startAnimation.setEnabled(false);
			this.stopAnimation.setEnabled(true);
			this.previousFrame.setEnabled(false);
			this.nextFrame.setEnabled(false);
			this.framesCountBox.setEnabled(false);
			this.animating = true;
			Thread thread = new Thread(this);
			thread.start();
		}
		if (e.getSource().equals(this.restartAnimation)) {
			this.animating = false;
			try {
				Thread.sleep(1);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			this.startAnimation.setEnabled(false);
			this.stopAnimation.setEnabled(true);
			this.previousFrame.setEnabled(false);
			this.nextFrame.setEnabled(false);
			this.framesCountBox.setEnabled(false);
			this.animating = true;
			this.animationPanel.resetFrames();
			Thread thread = new Thread(this);
			thread.start();
		}
		if (e.getSource().equals(this.resetAnimation)) {
			if (this.animating) {
				this.animating = false;
			}

			this.startAnimation.setEnabled(true);
			this.stopAnimation.setEnabled(false);
			this.previousFrame.setEnabled(true);
			this.nextFrame.setEnabled(true);
			this.framesCountBox.setEnabled(true);
			this.animationPanel.resetFrames();
		}
		if (e.getSource().equals(this.stopAnimation)) {
			this.startAnimation.setEnabled(true);
			this.stopAnimation.setEnabled(false);
			this.previousFrame.setEnabled(true);
			this.nextFrame.setEnabled(true);
			this.framesCountBox.setEnabled(true);
			this.animating = false;
		}
		if (e.getSource().equals(this.previousFrame)) {
			this.animationPanel.update(-1);
		}
		if (e.getSource().equals(this.nextFrame)) {
			this.animationPanel.update(1);
		}
	}

	@Override
	public void run() {
		while (animating) {
			this.animationPanel.update(1);
			try {
				Thread.sleep(this.animationSpeed + 1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getSource().equals(this.framesCountBox)) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				this.framesCountBox.requestFocus();
			}
		}
	}

	@Override
	@Unused
	public void keyReleased(KeyEvent e) {
	}

	@Override
	@Unused
	public void keyTyped(KeyEvent e) {
	}

}
