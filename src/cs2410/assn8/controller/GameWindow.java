package cs2410.assn8.controller;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import cs2410.assn8.components.MineField;
import cs2410.assn8.components.ScoreBoard;

/**
 * This class is the window that the game will run on.
 * @author Nathaniel Sneddon
 *
 */
@SuppressWarnings("serial")
public class GameWindow extends JFrame{
	
	/**
	 * This is the mine field where the dangerous stuff happens.
	 */
	private MineField dangerZone;
	
	/**
	 * This is the default container of the JFrame
	 */
	private Container pane;
	
	/**
	 * This is the top bar for the game. Although it is not exactly a scoreboard it has elemets of the previous scoreboard in the other
	 * assignment. 
	 */
	private ScoreBoard theTopBar;
	
	
	/**
	 * This is the constructor for the Game window.
	 */
	public GameWindow(){
		super("Minesweeper");
		this.setLayout(new BorderLayout());
		pane = this.getContentPane();
		//pane.setLayout(new BorderLayout());
		
		theTopBar = new ScoreBoard();
		dangerZone = new MineField(theTopBar);

		this.setSize(750, 750);
		this.setResizable(false);

		
		theTopBar.getStartButton().addActionListener(e -> {
			dangerZone.start();
		});
		
		pane.add(dangerZone);
		pane.add(theTopBar, BorderLayout.NORTH);
		
		setLocationRelativeTo(null);
		setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.update(getGraphics());
	}
	
	
	
	/**
	 * This is the main method
	 * @param args
	 * 		Command line arguments
	 */
	public static void main(String[] args){
		//GameWindow test = new GameWindow();
		
		SwingUtilities.invokeLater(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				new GameWindow();
			}
			
		});
	}
	
}
