package cs2410.assn8.components;

import java.awt.Color;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * This class is the class for the mine cell. It is a JButton also
 * @author Nathaniel Sneddon
 *
 */
@SuppressWarnings("serial")
public class MineCell extends JButton {

	/**
	 * This variable will say if it's a mine or not
	 */
	private boolean isMine;

	/**
	 * The state of the mine. 1 means it's flagged, 2 means it's marked for
	 * question. 0 means it's not marked at all;
	 * 
	 */
	private int state;
	
	/**
	 * This shows whether or not a mine was selected
	 */
	private boolean selected;

	/**
	 * The number of mines adjacent. -1 means that the mine is a mine
	 */
	private int adjacentMines;

	/**
	 * The x position of the mine
	 */
	public final int X_POSITION;

	/**
	 * The y position of the mine
	 */
	public final int Y_POSITION;

	/**
	 * The constructor for the mine cell
	 * 
	 * @param xPos
	 *            The x position of the mine
	 * @param yPos
	 *            The y position of the mine
	 */
	public MineCell(int xPos, int yPos) {
		super();
		this.X_POSITION = xPos;
		this.Y_POSITION = yPos;
		state = 0;
		isMine = false;
		selected = false;
	}

	/**
	 * This randomly lays down a mine.
	 * 
	 * @return
	 * 		If the mine has been laid or not
	 */
	public boolean addMine() {
		if(!isMine){
			Random rand = new Random();
			if (rand.nextInt(30) == 0) {
				isMine = true;
				return true;
			}
		}
		return false;
	}

	/**
	 * This method selects the mine.
	 */
	public void selectMine() {
		ImageIcon icon;
		selected = true;
		if (isMine) {
			icon = new ImageIcon("data/pictures/explodedbomb.png");
			this.setIcon(icon);
			this.setDisabledIcon(icon);
			

		} else {
			if (adjacentMines != 0) {
				icon = new ImageIcon(String.format("data/pictures/%03d.png", adjacentMines));
				this.setIcon(icon);
				this.setDisabledIcon(icon);
			}
		}
		this.setEnabled(false);
	}

	/**
	 * This method cycles through the state and is called when right clicked
	 */
	public void cycleState() {
		if (this.isEnabled()) {
			ImageIcon statePicture;
			if (state == 2) {
				state = 0;
			} else {
				state++;
			}
			switch (state) {
			case 0:
				this.setIcon(null);
				break;
			case 1:
				statePicture = new ImageIcon("data/pictures/mineflag.png");
				this.setIcon(statePicture);
				
				break;
			case 2:
				statePicture = new ImageIcon("data/pictures/questionmark.png");
				this.setIcon(statePicture);
				break;
			default:
				this.setIcon(null);
			}
		}
	}

	/**
	 * Chckes wheter or not the minecell is dangerous
	 * 
	 * @return Whether or not you would die by stepping on it.
	 */
	public boolean isMine() {
		return isMine;
	}

	/**
	 * This method just sets the adjacent mines to a certain value.
	 * 
	 * @param numMines
	 *            The number of adjacent mines
	 */
	public void setAdjacentMines(int numMines) {
		if (numMines >= -1) {
			adjacentMines = numMines;
		}
	}
	
	/**
	 * @return
	 * 		if the mine has been selected
	 */
	public boolean isSelected(){
		return selected;
	}
	
	/**
	 * This is for the end to reveal the true nature of the mine cell
	 * @param isWinner
	 * 		If the player has won
	 */
	public void showMines(boolean isWinner){
		if(isMine && !selected){
			ImageIcon icon = new ImageIcon("data/pictures/explodedbomb.png");
			this.setIcon(icon);
			if(state == 1){
				setBackground(Color.GREEN);
				setOpaque(true);
			} else if(!isWinner){
				setBackground(Color.RED);
				setOpaque(true);
			}
		} else if(state == 1){
			setBackground(Color.YELLOW);
			setOpaque(true);
		}
		
	}

	/**
	 * This returns the number of adjacent mines
	 * 
	 * @return Adjacent mines
	 */
	public int getAdjacentMines() {
		return adjacentMines;
	}
	
	/**
	 * This resets the game to the way things were
	 */
	public void reset(){
		isMine = false;
		selected = false;
		adjacentMines = 0;
		state = 0;
		setEnabled(true);
		this.removeAll();
		this.setIcon(null);
		this.setOpaque(false);
		this.setBackground(null);
		this.update(getGraphics());
	}

	/**
	 * This lets you know if it's flagged or marked for question
	 * 
	 * @return The state of the mine
	 */
	public int getState() {
		return state;
	}
}
