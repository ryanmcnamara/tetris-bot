package tetris.bot.player;

import java.awt.AWTException;
import java.util.List;

import tetris.bot.ActiveTetramino;
import tetris.bot.BoardState;
import tetris.bot.SimpleBoardTransformer;
import tetris.bot.Tetramino;
import tetris.bot.ai.Minimax;
import tetris.bot.ai.Minimax.SearchResult;
import tetris.bot.ai.SimpleFitnessFunction;

public class Main {
	// TODO
	public static boolean BREAK = false;
	public static void main(String[] args) throws AWTException,
			InterruptedException {
		Thread.sleep(3000L);
		GamePlayer player = new TetrisFriendsGamePlayer();
		player.startGame();

		// create AI
		Minimax minimax = new Minimax(new SimpleFitnessFunction(),
				new SimpleBoardTransformer(), 2);

		List<Tetramino> nexts = player.getNextTetraminos();
		Tetramino falling = player.getFallingTetramino();
		BoardState.Builder builder = new BoardState.Builder();
		builder.setNexts(nexts).setFalling(
				ActiveTetramino.newFromBasic(falling));
		BoardState b = builder.build();
		String space = "                                                                                         ";
		System.out.println(space + falling);
		boolean hit = false;
		while (true) {
			BoardState prev = b;
			SearchResult next = minimax.solve(b);

			System.out.println(space + "solved for next state");
			ActionPerformer.updateToState(player, b, next.move);
			b = next.resultState;
			nexts = player.getNextTetraminos();
			b = new BoardState.Builder(b).setNexts(nexts).build();
			if ( prev.getMinoCount() > b.getMinoCount()){
				hit = true;
				BREAK = true;
			}
			System.out.println(space + b.falling.tetramino);
		}
	}
}
