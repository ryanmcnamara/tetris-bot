package tetris.bot;

public class OrientedTetramino {
	public final Tetramino tetramino;
	public final Orientation orientation;

	private OrientedTetramino(Tetramino tetramino, Orientation orientation) {
		super();
		this.tetramino = tetramino;
		this.orientation = orientation;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((orientation == null) ? 0 : orientation.hashCode());
		result = prime * result
				+ ((tetramino == null) ? 0 : tetramino.hashCode());
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
		OrientedTetramino other = (OrientedTetramino) obj;
		if (orientation != other.orientation)
			return false;
		if (tetramino != other.tetramino)
			return false;
		return true;
	}

	public static OrientedTetramino newOrientedTetramino(Tetramino tetramino,
			Orientation orientation) {
		return new OrientedTetramino(tetramino, orientation);
	}

}
