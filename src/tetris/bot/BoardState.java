package tetris.bot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public final class BoardState {	
	public static final int PIECE_LOOKAHEAD = 5;
	public static final BoardState GAME_OVER = new BoardState(false);
	public final Tetramino hold;
	public final List<Tetramino> nexts;
	public final boolean[][] board;
	public final ActiveTetramino falling;
	public final boolean gameOver;
	public BoardState(Tetramino hold, List<Tetramino> nexts, boolean[][] board,
			ActiveTetramino falling) {
		super();
		this.gameOver = false;
		this.hold = hold;
		this.nexts = nexts;
		this.board = board;
		this.falling = falling;
	}

	public BoardState(boolean b) {
		this.gameOver = true;
		this.hold = null;
		this.nexts = null;
		this.board = new boolean[Constants.BOARD_HEIGHT][Constants.BOARD_WIDTH];
		this.falling = null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(board);
		result = prime * result + ((falling == null) ? 0 : falling.hashCode());
		result = prime * result + ((hold == null) ? 0 : hold.hashCode());
		result = prime * result + ((nexts == null) ? 0 : nexts.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BoardState other = (BoardState) obj;
		if (!Arrays.deepEquals(board, other.board))
			return false;
		if (falling == null) {
			if (other.falling != null)
				return false;
		} else if (!falling.equals(other.falling))
			return false;
		if (hold != other.hold)
			return false;
		if (nexts == null) {
			if (other.nexts != null)
				return false;
		} else if (!nexts.equals(other.nexts))
			return false;
		return true;
	}

	public static class Builder {
		private Tetramino hold;
		private List<Tetramino> nexts;
		private boolean[][] board;
		private ActiveTetramino falling;

		public Builder(){
			this.hold = Tetramino.NONE;
			this.nexts = Lists.newArrayList();
			this.board = new boolean[Constants.BOARD_HEIGHT][Constants.BOARD_WIDTH];
			this.falling = null;
		}
		public Builder(BoardState parent) {
			this.hold = parent.hold;
			this.nexts = new ArrayList<Tetramino>(parent.nexts);
			this.board = parent.board;
			for (int i = 0; i < board.length; i++) {
				this.board[i] = this.board[i].clone();
			}
			this.falling = parent.falling;
		}

		public Builder setFalling(ActiveTetramino activeTetramino) {
			this.falling = activeTetramino;
			return this;
		}

		public BoardState.Builder setNexts(List<Tetramino> nexts) {
			this.nexts = nexts;
			return this;
		}
		
		public BoardState build() {
			return new BoardState(hold, nexts, board, falling);
		}
		public Builder setBoard(boolean[][] array) {
			this.board = array;
			return this;
		}

	}
	
	public BoardState removeClearedLines(){
		Set<Integer> clears = Sets.newHashSet();
		for ( int row = 0; row < this.board.length; row++){
			boolean cleared = true;
			for ( int col = 0; cleared && col < this.board[0].length; col++){
				cleared = this.board[row][col];
			}
			if ( cleared){
				clears.add(row);
			}
		}
		if ( clears.size() == 0 ){
			return this;
		}
		Set<Integer> seenClears = Sets.newHashSet();
		boolean[][] array = new boolean[this.board.length][this.board[0].length];
		for ( int y = 0; y < array.length; y++ ){
			if ( clears.contains(y)){
				seenClears.add(y);
			}
			if ( y + seenClears.size() >= array.length){
				break;
			}
			int pullY = y + seenClears.size();
			for (int x = 0; x < array[0].length; x++){
				array[y][x] = this.board[pullY][x];
			}
		}
		return new Builder(this).setBoard(array).build();
	}

	@Override
	public String toString() {
		if ( this.gameOver ){
			System.out.println("GAMEOVER");
		}
		String ret = "";
		for (int i = this.board.length - 1; i >= 0; i--) {
			ret += "|";
			for (int x = 0; x < this.board[0].length; x++) {
				ret += (this.board[i][x]) ? "X" : " ";
				ret += "|";
			}
			ret += "\n";
			for (int x = 0; x < this.board[0].length * 2 + 1; x++) {
				ret += "_";
			}
			ret += "\n";
		}
		return ret;
	}
	// TODO add builder

	public int getMinoCount() {
		int ret = 0;
		for ( int i = 0; i < this.board.length; i++ ){
			for ( int j = 0; j < this.board[0].length; j++ ){
				if ( this.board[i][j])ret++;
			}
		}
		return ret;
	}
}
