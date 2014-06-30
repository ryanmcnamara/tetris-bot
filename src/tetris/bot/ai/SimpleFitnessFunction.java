package tetris.bot.ai;

import tetris.bot.BoardState;

/**
 * Just compute the holes
 */
public class SimpleFitnessFunction implements FitnessFunction {

	@Override
	public long valueOf(BoardState state) {
		if ( state.gameOver ){
			return Long.MIN_VALUE;
		}
		long holeCost = 100;
		long ret = 0;
		int[] heights = new int[state.board[0].length];
		int maxHeight = Integer.MIN_VALUE;

		boolean[][] array = state.board;
		for (int y = 0; y < array.length; y++) {
			for (int x = 0; x < array[0].length; x++) {
				if (array[y][x]) {
					heights[x] = y;
					maxHeight = Math.max(maxHeight, y);
				}
			}
		}
		long heightDiffPenalty = 0;
		for ( int i = 0; i < heights.length - 1; i++ ){
			heightDiffPenalty += Math.abs(heights[i] - heights[i+1]);
		}
		
		ret -= heightDiffPenalty;
		
		if ( maxHeight > 15 ){
			ret -= maxHeight - 13;
		}

		for (int y = 0; y < array.length; y++) {
			for (int x = 0; x < array[0].length; x++) {
				if (!array[y][x]) {
					if (y < heights[x]) {
						ret-=holeCost;
					}
				}
			}
		}

		return ret;
	}

}
