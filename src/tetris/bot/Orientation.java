package tetris.bot;

public enum Orientation {
	ROT0(0), ROT1(1), ROT2(2), ROT3(3), DEFAULT(0);

	public final int rot;

	Orientation(int rot) {
		this.rot = rot;
	}

	public static Orientation getRotation(int rotate) {
		if ( rotate == 0 ){
			return ROT0;
		}if ( rotate == 1 ){
			return ROT1;
		}if ( rotate == 2 ){
			return ROT2;
		}if ( rotate == 3 ){
			return ROT3;
		}
		return null;
	}
}
