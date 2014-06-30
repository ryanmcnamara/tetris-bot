package tetris.bot;

import java.util.ArrayList;
import java.util.List;

public class SimpleBoardTransformer implements BoardTransformer {

	@Override
	public BoardState rotateFalling(BoardState parent, Orientation rotation) {
		ActiveTetramino newFalling = ActiveTetramino.newActiveTetramino(
				parent.falling.tetramino, parent.falling.location, rotation);
		return new BoardState.Builder(parent).setFalling(newFalling).build();
	}

	@Override
	public BoardState dropFalling(BoardState parent, int location) {
		if (parent.falling == null) {
			return null;
		}

		ActiveTetramino activeFalling = parent.falling;

		boolean[][] boardArray = new boolean[parent.board.length][];
		for (int i = 0; i < boardArray.length; i++) {
			boardArray[i] = parent.board[i].clone();
		}

		boolean[][] fallingMinos = activeFalling.minos;
		int minx = Integer.MAX_VALUE;
		int maxx = Integer.MIN_VALUE;

		for (int y = 0; y < fallingMinos.length; y++) {
			for (int x = 0; x < fallingMinos[0].length; x++) {
				if (fallingMinos[y][x]) {
					minx = Math.min(minx, x);
					maxx = Math.max(maxx, x);
				}
			}
		}

		if ((location + minx < 0) || (location + maxx >= boardArray[0].length)) {
			return null;
		}

		int[] heights = new int[fallingMinos[0].length];
		for (int col = 0; col < heights.length; col++) {
			if (col < minx || col > maxx)
				continue;
			for (int row = 0; row < boardArray.length; row++) {
				if (boardArray[row][col + location]) {
					heights[col] = row+1;
				}
			}
		}

		int placeRow = Integer.MIN_VALUE;
		int highMino = Integer.MIN_VALUE;

		for (int col = 0; col < heights.length; col++) {
			int lowMino = Integer.MAX_VALUE;
			for (int row = 0; row < fallingMinos.length; row++) {
				if (fallingMinos[row][col]) {
					lowMino = Math.min(row, lowMino);
					highMino = Math.max(row, highMino);
				}
			}
			placeRow = Math.max(placeRow, heights[col] - lowMino);
		}

		if ( placeRow + highMino >= boardArray.length){
			return BoardState.GAME_OVER;
		}
		// alter the board to place it there
		for (int row = 0; row < fallingMinos.length; row++) {
			for (int col = 0; col < fallingMinos[0].length; col++) {
				if (fallingMinos[row][col]) {
					boardArray[row + placeRow][col + location] = fallingMinos[row][col];
				}
			}
		}
		List<Tetramino> newNexts = new ArrayList<Tetramino>(parent.nexts);
		ActiveTetramino newFalling = null;
		if (parent.nexts.size() > 0) {
			newFalling = ActiveTetramino.newActiveTetramino(
					parent.nexts.get(0), location, Orientation.DEFAULT);
			newNexts.remove(0);
		}
		BoardState result = new BoardState(parent.hold, newNexts, boardArray, newFalling);
		result = result.removeClearedLines();
		return result;
	}
}
