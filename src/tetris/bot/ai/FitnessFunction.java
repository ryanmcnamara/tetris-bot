package tetris.bot.ai;

import tetris.bot.BoardState;

public interface FitnessFunction {
	
	
	public long valueOf(BoardState state);
}
