package tetris.bot.player;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.util.List;
import java.util.Map;

import tetris.bot.Tetramino;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 
 // lookup pixel at 393, 600 // bottom left at 414, 600 // top one is at 414,
 * 240 which is 20 up // 486, 258 is the cell we want // 649, 334 reads the
 * first next // 650, 407 reads the second next // 650, 465 reads the third next
 * // 651, 519 reads the 4th next // 650, 563 reads the 5th next
 * 
 * thats some data i used to get all these magic numbers in the class
 * 
 * Colors are as follows : I - 26, 136, 212 L - 214, 71, 0 J - 11, 42, 194 O -
 * 217, 143, 0 Z - 198, 0, 40 S - 84, 165, 0 T - 152, 21, 124
 * 
 * @author ryanmcnamara
 * 
 */
public class TetrisFriendsGamePlayer implements GamePlayer {

	private Robot robot;
	private Point lookupPoint;
	private int[] lookupColor = new int[] { 230, 0, 0 };
	private Point bottomLeftCell;
	private Point topLeftCell;
	private Point minoEntryCell;
	private Point next1;
	private Point next2;
	private Point next3;
	private Point next4;
	private Point next5;
	private Point start;

	private static Map<Tetramino, int[]> tetraminoToColor;
	static {
		tetraminoToColor = Maps.newHashMap();
		tetraminoToColor.put(Tetramino.I, new int[] { 49, 175, 255 });
		tetraminoToColor.put(Tetramino.L, new int[] { 248, 106, 0 });
		tetraminoToColor.put(Tetramino.J, new int[] { 45, 74, 237 });
		tetraminoToColor.put(Tetramino.O, new int[] { 253, 183, 0 });
		tetraminoToColor.put(Tetramino.Z, new int[] { 239, 29, 71 });
		tetraminoToColor.put(Tetramino.S, new int[] { 118, 206, 0 });
		tetraminoToColor.put(Tetramino.T, new int[] { 191, 52, 162 });
		tetraminoToColor.put(Tetramino.NONE, new int[] { 0, 0, 0 });
	}

	// ewwww
	private static void sleep(long l) {
		try {
			Thread.sleep(l);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static Tetramino getTetraminoFromColor(int[] color) {
		Tetramino tetraminoMatch = null;
		int diff = Integer.MAX_VALUE;
		for (Tetramino t : tetraminoToColor.keySet()) {
			int[] v = tetraminoToColor.get(t);
			int thisDiff = 0;
			for (int i = 0; i < 3; i++) {
				thisDiff += Math.abs(color[i] - v[i]);
			}
			if (thisDiff < diff) {
				diff = thisDiff;
				tetraminoMatch = t;
			}
		}
		return tetraminoMatch;
	}

	@Override
	public Tetramino getFallingTetramino() {
		Color c = robot.getPixelColor(minoEntryCell.x, minoEntryCell.y);
		int[] array = new int[] { c.getRed(), c.getGreen(), c.getBlue() };
		return getTetraminoFromColor(array);
	}

	public void typeKey(int event) {
		try {
			if (validateScreen()) {
				robot.keyPress(event); 
				Thread.sleep(90L);
				robot.keyRelease(event);
				Thread.sleep(200L);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Tetramino> getNextTetraminos() {
		List<Tetramino> result = Lists.newLinkedList();
		result.add(getNext1());
		return result;
	}

	public void pressStart() {
		try {
			if (validateScreen()) {
				robot.mouseMove(start.x, start.y);
				// TODO really push 5 times?
				for (int i = 0; i < 5; i++) {
					Thread.sleep(100L);
					if (validateScreen()) {
						robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
						Thread.sleep(100L);
						robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
					}
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void moveLeft() {
		if (validateScreen()) {
			typeKey(KeyEvent.VK_LEFT);
		}
	}

	@Override
	public void moveRight() {
		if (validateScreen()) {
			typeKey(KeyEvent.VK_RIGHT);
		}
	}

	@Override
	public void pressHardDrop() {
		if (validateScreen()) {
			typeKey(KeyEvent.VK_SPACE);
		}
	}

	@Override
	public Tetramino waitForFallingPiece() {
		// spin wait ... :/
		while ( true ){
			Tetramino falling = this.getFallingTetramino();
			if ( falling != Tetramino.NONE ){
				return falling;
			}
			sleep(20);// TODO is 20 a good number?
		}
	}

	@Override
	public void startGame() {
		// get game board pixel locations
		try {
			robot = new Robot();
			Toolkit t = Toolkit.getDefaultToolkit();
			Dimension d = t.getScreenSize();

			BufferedImage img = robot.createScreenCapture(new Rectangle(d));
			Raster raster = img.getRaster();

			for (int y = 0; y < d.height; y++) {
				for (int x = 0; x < d.width; x++) {
					int[] array = new int[3];
					raster.getPixel(x, y, array);
					boolean match = true;
					for (int i = 0; i < 3; i++) {
						if (array[i] != lookupColor[i]) {
							match = false;
						}
					}

					if (match) {
						lookupPoint = new Point(x, y);

						break;
					}
				}
			}
			if (lookupPoint != null) {
				int x = lookupPoint.x;
				int y = lookupPoint.y;
				bottomLeftCell = new Point(414 - 393 + x, y);
				topLeftCell = new Point(414 - 393 + x, 240 - 600 + y);

				double cellWidth = bottomLeftCell.y - topLeftCell.y;
				cellWidth /= 20;
				int cellRad = (int) Math.round(cellWidth / 2);
				minoEntryCell = new Point(486 - 393 + cellRad + x, 258 - 600
						- cellRad + y);
				next1 = new Point(649 - 393 + x + 3 - 1, 334 - 600 - 42 - 3 + 1+ y);
				next2 = new Point(649 - 393 + x, 407 - 600 + y);
				next3 = new Point(649 - 393 + x, 465 - 600 + y);
				next4 = new Point(649 - 393 + x, 519 - 600 + y);
				next5 = new Point(649 - 393 + x, 563 - 600 + y);

				start = new Point(x + 240, y - 30);
				pressStart();
				waitForFallingPiece();
			} else {
				System.err.println("couldn't find lookup point");
				return;
			}

		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void waitForStart() {
		// TODO Auto-generated method stub

	}

	@Override
	public Tetramino getNext1() {
		Color c = robot.getPixelColor(next1.x, next1.y);
		int[] array = new int[] { c.getRed(), c.getGreen(), c.getBlue() };
		return getTetraminoFromColor(array);
	}

	@Override
	public boolean validateScreen() {
		Color c = robot.getPixelColor(lookupPoint.x, lookupPoint.y);
		int[] array = new int[] { c.getRed(), c.getGreen(), c.getBlue() };
		boolean valid = true;
		int diff = 0;
		 for (int i = 0; i < lookupColor.length; i++) {
			diff += Math.abs(lookupColor[i] - array[i]);
		}
		if ( diff > 40 ){
			valid = false;
		}
		
		return true;//TODO
	}

	@Override
	public void rotateCC() {
		typeKey(KeyEvent.VK_UP);
	}

	@Override
	public void rotateC() {
		typeKey(KeyEvent.VK_Z);
	}


}
