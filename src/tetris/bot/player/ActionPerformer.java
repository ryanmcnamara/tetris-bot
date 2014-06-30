package tetris.bot.player;

import tetris.bot.ActiveTetramino;
import tetris.bot.BoardState;

public class ActionPerformer {
	public static void updateToState(GamePlayer player, BoardState from, ActiveTetramino to){
		int xDiff = to.location - 3;// from.falling.location;
		int rotDiff = to.rotation.rot - from.falling.rotation.rot;
		int initXDiff = xDiff;
		rotDiff += 4;
		rotDiff %= 4;
		int initRotDiff = rotDiff;
		
		while ( rotDiff != 0 ){
			if ( rotDiff < 2 ){
				player.rotateC();
				rotDiff --;
			}
			else
			{
				player.rotateCC();
				rotDiff++;
			}
			rotDiff %= 4;
		}
	
		
		while ( xDiff != 0 ){
			if ( xDiff > 0 ){
				player.moveRight();
				xDiff--;
			}
			else{
				player.moveLeft();
				xDiff++;
			}
		}
		
		if ( Main.BREAK ){
			int x = 0; 
			x++;
		}
		player.pressHardDrop();
		player.waitForFallingPiece();
		System.out.println("                                                                                         hard drop complete");
		return;
	}
}
