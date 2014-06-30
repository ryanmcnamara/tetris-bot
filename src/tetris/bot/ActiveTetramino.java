package tetris.bot;

import java.util.HashMap;
import java.util.Map;

public final class ActiveTetramino {

	private static Map<OrientedTetramino, boolean[][]> cachedTetraminos = new HashMap<>();

	public final boolean[][] minos;
	// save x location only
	public final int location;
	public final Orientation rotation;
	public final Tetramino tetramino;

	private ActiveTetramino(Tetramino tetramino, int location,
			Orientation rotation) {
		OrientedTetramino oriented = OrientedTetramino.newOrientedTetramino(
				tetramino, rotation);
		addToCache(oriented);
		this.tetramino = tetramino;
		this.minos = cachedTetraminos.get(oriented);
		this.location = location;
		this.rotation = rotation;
	}

	private static boolean addToCache(OrientedTetramino oriented) {
		if (cachedTetraminos.containsKey(oriented)) {
			return false;
		}
		cachedTetraminos.put(oriented,
				oriented.tetramino.rotatedMinos[oriented.orientation.rot]);
		return true;
	}

	public static ActiveTetramino newActiveTetramino(Tetramino tetramino,
			int location, Orientation rotation) {
		return new ActiveTetramino(tetramino, location, rotation);
	}

	public static ActiveTetramino newFromBasic(Tetramino tetramino) {
		return new ActiveTetramino(tetramino, Constants.SPAWN_COLUMN, Orientation.DEFAULT);
	}
	
	public String toString(){
		return tetramino + " location: " + location + ", rot: " + rotation.rot;
	}

	// TODO add builder

}
