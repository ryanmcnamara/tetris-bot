package tetris.bot;

import java.awt.Point;

public class Constants {
	public static final int BOARD_WIDTH = 10;
	public static final int BOARD_HEIGHT = 21;
	public static final int SPAWN_COLUMN = 3;
	
	public static final Point[] directions = new Point[]{new Point(1,0), new Point(0,1), new Point(-1, 0), new Point(0,-1)};
	
}
