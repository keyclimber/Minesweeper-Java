package cs2410.assn8.media;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 * This class is the class that handles the picture making. It returns a JLabel
 * with a picture of choice.
 * 
 * @author Nathaniel Sneddon
 *
 */
@SuppressWarnings("serial")
public class Picture extends JLabel {

	

	/**
	 * The constructor for this class. According to what number you put in it
	 * will generate an image that will be used.
	 * 
	 * @param picNumber
	 *            The picture that is desired out of the 12
	 */
	public Picture(int picNumber) {
		super(makePicture(picNumber));
	}

	/**
	 * This method generates an image icon from one of the 5 files in the
	 * pictures folder.
	 * 
	 * @param picNumber
	 *            The picture that is desired out of the 12
	 * @return The image that has been wanted
	 */
	private static ImageIcon makePicture(int picNumber) {
		BufferedImage myPicture = null;
		try {
			if (picNumber >= 0 && picNumber <= 8) {
				File pictureFile = new File(String.format("data/pictures/%03d.png", picNumber));
				myPicture = ImageIO.read(pictureFile);
			} else if (picNumber == 9) {
				File pictureFile = new File("data/pictures/explodedbomb.png");
				myPicture = ImageIO.read(pictureFile);
			} else if (picNumber == 10) {
				File pictureFile = new File("data/pictures/mineflag.png");
				myPicture = ImageIO.read(pictureFile);
			} else if (picNumber == 11) {
				File pictureFile = new File("data/pictures/questionmark.png");
				myPicture = ImageIO.read(pictureFile);
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Error 404: File not found!", "Error!", JOptionPane.ERROR_MESSAGE);
		}
		return new ImageIcon(myPicture);
	}
}
