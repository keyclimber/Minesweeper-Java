package cs2410.assn8.components;


import java.awt.Font;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * This is the "Scoreboard". It displays the seconds that went by and has a start/restart button.
 * @author Nathaniel Sneddon
 *
 */
@SuppressWarnings("serial")
public class ScoreBoard extends JPanel {

	/**
	 * This button can be pressed to start or to restart the game
	 */
	private JButton start;

	/**
	 * This is the label that represents the number of mines left
	 */
	private JLabel flagsLeftLabel;
	
	/**
	 * This shows how many mines you have to still find, based on the number of flags set.
	 */
	private int flagsLeft;

	/**
	 * This shows the time counting up to 999. If you hit 999 nothing happens,
	 * but you suck.
	 */
	private JLabel timerValueLabel;

	/**
	 * The timer for the game;
	 */
	private Timer timer;

	/**
	 * This checks if the timer is running
	 */
	private boolean timerRunning;
	

	/**
	 * The value of the timer being displayed
	 */
	private int timerValue;

	/**
	 * This just stores the default font value
	 */
	private Font defaultFont;

	/**
	 * The constructor that initializes everything
	 */
	public ScoreBoard() {
		super();
		// this.setLayout(new BorderLayout());

		defaultFont = new Font("Elephant", Font.PLAIN, 15);

		timerValueLabel = new JLabel("000");
		timerValueLabel.setFont(defaultFont);
		timerValue = 0;
		timer = new Timer();
		this.add(timerValueLabel);
		start = new JButton("Start");

		timerRunning = false;
		flagsLeft = 100;
		
		start.setFont(defaultFont);
		this.add(start);
		flagsLeftLabel = new JLabel(String.valueOf(flagsLeft));
		flagsLeftLabel.setFont(defaultFont);
		this.add(flagsLeftLabel);
	}
	
	/**
	 * 
	 * @return
	 * 		The amount of time running
	 */
	public boolean timerRunning() {
		return timerRunning;
	}

	/**
	 * This starts the timer
	 */
	public void startTimer() {
		if (timerRunning) {
			timer.cancel();
			resetTimer();
		}
		
		timerRunning = true;
		timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				timerValueLabel.setText(String.format("%03d", ++timerValue));
			}

		}, 1000, 1000);
	}

	/**
	 * This returns the start button
	 * @return
	 * 		the start button
	 */
	public JButton getStartButton() {
		return start;
	}
	
	/**
	 * This stops the timer
	 */
	public void stopTimer() {
		timer.cancel();
		timerRunning = false;
	}
	
	/**
	 * This resets the timer and everything else in this class
	 */
	public void reset(){
		resetTimer();
		flagsLeft = 100;
		flagsLeftLabel.setText(String.format("%03d", flagsLeft));
		
	}
	
	/**
	 * This resets the timer
	 */
	public void resetTimer() {
		if(timerRunning){
			timer.cancel();
		}
		timerValue = 0;
		timerValueLabel.setText("000");
		timer = new Timer();
		timerRunning = false;
	}
	
	/**
	 * This increments the number of flags that has not been placed
	 */
	public void incFlags(){
		flagsLeft++;
		flagsLeftLabel.setText(String.format("%03d", flagsLeft));
	}
	
	/**
	 * This decrements the number of flags left
	 */
	public void decFlags(){
		flagsLeft--;
		flagsLeftLabel.setText(String.format("%03d", flagsLeft));
	}

	/**
	 * This shows a cool message saying you won the game
	 * @param panel
	 * 		The JPanel to center the window on
	 */
	public void gameWon(JPanel panel) {
		JOptionPane.showMessageDialog(panel, String.format("Congrats! You won with a time of %03d!", timerValue), "You survived", JOptionPane.INFORMATION_MESSAGE);
	}

}
