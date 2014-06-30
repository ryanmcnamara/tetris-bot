package tetris.bot.ai;

import tetris.bot.ActiveTetramino;
import tetris.bot.BoardState;
import tetris.bot.BoardTransformer;
import tetris.bot.Orientation;

public class Minimax {

	public class SearchResult {
		public final ActiveTetramino move;
		public final BoardState resultState;

		public SearchResult(ActiveTetramino move, BoardState resultState) {
			super();
			this.move = move;
			this.resultState = resultState;
		}

	}

	private FitnessFunction f;
	private int depth;
	private BoardTransformer transformer;

	public Minimax(FitnessFunction f, BoardTransformer transformer, int depth) {
		this.f = f;
		this.depth = depth;
		this.transformer = transformer;
	}

	// returns where/how to drop next
	public SearchResult solve(BoardState b) {
		return solve(b, 0);
	}

	private SearchResult solve(BoardState b, int depth) {
		if (depth >= this.depth || b.falling == null) {
			return new SearchResult(null, b);
		}

		SearchResult ret = null;
		for (int rot = 0; rot < 4; rot++) {
			BoardState rotated = transformer.rotateFalling(b,
					Orientation.getRotation(rot));

			for (int col = -2;; col++) {
				ActiveTetramino thisMove = ActiveTetramino.newActiveTetramino(rotated.falling.tetramino, col, Orientation.getRotation(rot));
				BoardState dropped = transformer.dropFalling(rotated, col);
				if (dropped == null) {
					if (col > 0) {
						break;
					} else {
						continue;
					}
				}
				SearchResult bestChild = solve(dropped, depth + 1);
				if ( bestChild != null ){
					if (ret == null
							|| (this.f.valueOf(bestChild.resultState) > this.f
									.valueOf(ret.resultState))) {
						ret = new SearchResult(thisMove, dropped);
					}
				}
			}
		}
		return ret;
	}
}
