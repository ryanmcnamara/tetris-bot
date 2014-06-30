package tetris.bot.player;

import java.util.List;

import tetris.bot.Tetramino;

/**
 * Game player, specific to a version of tetris
 * @author ryanmcnamara
 *
 */
public interface GamePlayer {
	void startGame();
	
	void waitForStart();
		
	Tetramino getFallingTetramino();

	List<Tetramino> getNextTetraminos();

	Tetramino getNext1();
	
	boolean validateScreen();

	void moveRight();

	void moveLeft();

	void pressHardDrop();

	void rotateCC();
	
	void rotateC();

	Tetramino waitForFallingPiece();	
	
}
