package cs2410.assn8.components;

import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import cs2410.assn8.components.MineCell;

@SuppressWarnings("serial")
/**
 * This class has the mine field and its way of handling stuff.
 * 
 * @author Nathaniel Sneddon
 *
 */
public class MineField extends JPanel {

	/**
	 * The mine cells in the game field
	 */
	private MineCell mines[][];

	/**
	 * This is the layout for the mine field
	 */
	private GridLayout minefieldLayout;



	/**
	 * This is a copy of the scoreboard in the game window
	 */
	private ScoreBoard theScoreBoard;

	/**
	 * The mine field constructor. It lays the mines in a 24 by 24 grid.
	 * @param aScoreBoard
	 * 		the scoreboard of the game, so that the minefield can interact with it
	 */
	public MineField(ScoreBoard aScoreBoard) {
		super();

		theScoreBoard = aScoreBoard;

		minefieldLayout = new GridLayout(24, 24);
		this.minefieldLayout.setHgap(2);
		this.minefieldLayout.setVgap(2);

		this.setLayout(minefieldLayout);

		mines = new MineCell[24][24];

		for (int i = 0; i < mines.length; i++) {
			for (int j = 0; j < mines[i].length; j++) {
				mines[i][j] = new MineCell(i, j);
				mines[i][j].addMouseListener(new MouseListener() {

					@Override
					public void mouseClicked(MouseEvent e) {
						// TODO Auto-generated method stub
						if (e.getButton() == MouseEvent.BUTTON1) {
							if (!theScoreBoard.timerRunning()) {
								theScoreBoard.startTimer();
							}
							select((((MineCell) e.getSource()).X_POSITION), ((MineCell) e.getSource()).Y_POSITION);
							if (isWinner()) {
								gameOver(true);
							}
						} else if (e.getButton() == MouseEvent.BUTTON3) {
							if (((MineCell) e.getSource()).getState() == 1) {
								theScoreBoard.incFlags();
							}
							((MineCell) e.getSource()).cycleState();
							if (((MineCell) e.getSource()).getState() == 1) {
								theScoreBoard.decFlags();
							}
						}
					}


					@Override
					public void mouseEntered(MouseEvent e) {
						// TODO Auto-generated method stub

					}

					@Override
					public void mouseExited(MouseEvent e) {
						// TODO Auto-generated method stub

					}

					@Override
					public void mousePressed(MouseEvent e) {
						// TODO Auto-generated method stub

					}

					@Override
					public void mouseReleased(MouseEvent e) {
						// TODO Auto-generated method stub

					}

				});
				this.add(mines[i][j]);
			}
		}
		start();
	}

	/**
	 * This method will randomly lay out mines in the field
	 */
	private void setMines() {
		int mineNumber = 100; // number of mines left to place
		while (mineNumber > 0) {
			for (int i = 0; i < mines.length; i++) {
				for (int j = 0; j < mines[i].length; j++) {
					if (mines[i][j].addMine()) { // addMine will randomly make that field a mine or leave it alone
						mineNumber--;
					}
				}
			}
		}
	}

	/**
	 * This method is to check individual mines for adjacent mines
	 * 
	 * @param x
	 *            The x coordinate of the mine cell to be checked
	 * @param y
	 *            The y coordinate of the mine cell to be checked
	 * @return The number of mines surrounding the mine cell
	 */
	private int adjacentMines(int x, int y) {
		int answer = 0;
		int xStart = x - 1;
		int yStart = y - 1;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				try {
					if (mines[xStart + i][yStart + j].isMine()) {
						answer++;
					}
				} catch (ArrayIndexOutOfBoundsException e) {
				}
			}
		}
		return answer;
	}

	/**
	 * This method goes through every mine and sets the number of adjacent mines
	 * around it
	 */
	private void findAdjacentMines() {
		for (int i = 0; i < mines.length; i++) {
			for (int j = 0; j < mines[i].length; j++) {
				if (mines[i][j].isMine()) {
					mines[i][j].setAdjacentMines(-1);
				} else {
					mines[i][j].setAdjacentMines(adjacentMines(i, j));
				}
			}
		}
	}

	/**
	 * This method selects a mine
	 * @param x
	 * 		The x coordinate value of the mine being selected
	 * @param y
	 * 		The y coordinate value of the mine being selected
	 */
	public void select(int x, int y) {
		try {
			if (mines[x][y].getState() != 1) {
				mines[x][y].selectMine();
				if (mines[x][y].getAdjacentMines() == 0) {
					selectMinesAround(x, y);
				}
				if (mines[x][y].isMine()) {
					gameOver(false);
				}
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			// do nothing
		}
	}

	/**
	 * This method selects every mine around x and y
	 * 
	 * @param x
	 *            The x coordinate of the mine being selected
	 * @param y
	 *            The y coordinate of the mine being selected
	 */
	public void selectMinesAround(int x, int y) {
		int xStart = x - 1;
		int yStart = y - 1;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				try {
					if (!(i == 1 && j == 1) && mines[i + xStart][j + yStart].isEnabled()) {
						select(i + xStart, j + yStart);
					}
				} catch (ArrayIndexOutOfBoundsException e) {
					// do nothing
				}
			}
		}

	}
	
	/*
	private boolean isWinner() {
		for (MineCell[] mc : mines) {
			for (MineCell m : mc) {
				if (m.isSelected() && !m.isMine()) {
					System.out.println(m.X_POSITION + " " + m.Y_POSITION);
					return false;
				}
			}
		}
		return true;
	}
	*/
	
	public void actionListener(){
		mines[1][1].addActionListener(e->{
			
		});
	}
	
	/**
	 * This prints the details of the field. This method should not be used in
	 * the game, but is used for debugging purposes.
	 */
	private void printField() { 
		for (MineCell[] mc : mines) {
			for (MineCell m : mc) {
				if(m.getAdjacentMines() == 0)
					System.out.print("   ");
				else
					System.out.printf("%3d", m.getAdjacentMines());
			}
			System.out.println();
		}
		System.out.println("=========================================================================");
	}

	/**
	 * This method starts or restarts the game. Uncommenting print field will print the bombs and their location on the console.
	 	This is for debugging purposes.
	 */
	public void start() {
		resetMines();
		setMines();
		findAdjacentMines();
		
		theScoreBoard.reset();
		//this.printField(); // TODO Delete this code after finished debugging

	}

	/**
	 * This resets all of the mines.
	 */
	public void resetMines() {
		for (MineCell[] mc : mines) {
			for (MineCell m : mc) {
				m.reset();
			}
		}
	}

	/**
	 * This method stops the game from running
	 * 
	 * @param win
	 *            Whether or not the the game is victorious
	 */
	public void gameOver(boolean win) {
		theScoreBoard.stopTimer();
		for (MineCell[] mc : mines) {
			for (MineCell m : mc) {
				m.showMines(win);
			}
		}
		if (win) {
			theScoreBoard.gameWon(this);
		}
	}
	
	/**
	 * This method shows is fomeone is the winner of the game
	 * @return
	 * 		if the player has won
	 */
	private boolean isWinner() {
		// TODO Auto-generated method stub
		for (int i = 0; i < mines.length; i++) {
			for (int j = 0; j < mines[i].length; j++) {
				if(!mines[i][j].isMine() && !mines[i][j].isSelected()){
					return false;
				}
			}
		}
		return true;
	}
}
