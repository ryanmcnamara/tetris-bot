package tetris.bot;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import tetris.bot.ai.Minimax;
import tetris.bot.ai.Minimax.SearchResult;
import tetris.bot.ai.SimpleFitnessFunction;

import com.google.common.collect.Lists;

public class Main {
	public static void main(String[] args) throws InterruptedException {
		ActiveTetramino falling = ActiveTetramino.newActiveTetramino(
				Tetramino.T, Constants.SPAWN_COLUMN, Orientation.DEFAULT);
		List<Tetramino> nexts = Lists.newArrayList();
		for ( int i = 0; i < 100; i++ ){
			List<Tetramino> asdf = Lists.newArrayList();
			asdf.add(Tetramino.S);
			asdf.add(Tetramino.I);
			asdf.add(Tetramino.O);
			asdf.add(Tetramino.J);
			asdf.add(Tetramino.L);
			asdf.add(Tetramino.Z);
			asdf.add(Tetramino.T);
			Collections.shuffle(asdf);
			nexts.addAll(asdf);
			
		}
		BoardState boardState = new BoardState.Builder().setFalling(falling)
				.setNexts(nexts).build();

		BoardTransformer transformer = new SimpleBoardTransformer();
		Minimax minimax = new Minimax(new SimpleFitnessFunction(), transformer, 2);
		Scanner in = new Scanner(System.in);
		for ( int i = 0;; i++ ){
			SearchResult result = minimax.solve(boardState);
			boardState = result.resultState;
			System.out.println();
			System.out.println();				
			System.out.println(boardState);
			System.out.println(result.move);
			Thread.sleep(50);
		}
		

	}
}
