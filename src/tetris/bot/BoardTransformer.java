package tetris.bot;

public interface BoardTransformer {

	BoardState dropFalling(BoardState parent, int location);

	BoardState rotateFalling(BoardState parent, Orientation rotation);
}
