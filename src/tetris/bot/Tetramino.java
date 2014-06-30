package tetris.bot;


public enum Tetramino {
	NONE, I(new String[][]
			{
			{ 
				"0000",
				"1111",
				"0000",
				"0000"
			}, 
			{
				"0100",
				"0100",
				"0100",
				"0100"	
			}, 
			{
				"0000",
				"0000",
				"1111",
				"0000"
			}, 
			{
				"0010",
				"0010",
				"0010",
				"0010"
			}
			}),
	L(new String[][]{{ 
			"001", 
			"111", 
			"000"}, {
				"110", 
				"010", 
				"010"}, {
					"000", 
					"111", 
					"100"}, {
						"010", 
						"010", 
						"011"}}),
	J(new String[][]{{ 
			"100", 
			"111", 
			"000"}, {
				"010", 
				"010", 
				"110"}, {
					"000", 
					"111", 
					"001"}, {
						"011", 
						"010", 
						"010"}}),
	S(new String[][]{{ 
	        "011", 
	        "110", 
	        "000"},{
	        	"100", 
	        	"110", 
	        	"010"},{
	        		"000", 
	        		"011", 
	        		"110"},{
	        			"010", 
	        			"011", 
	        			"001"}}),
    Z(new String[][]{{
	        "110", 
	        "011", 
	        "000"}, {
	        	"010", 
	        	"110", 
	        	"100"}, {
	        		"000", 
	        		"110", 
	        		"011"}, {
	        			"001", 
	        			"011", 
	        			"010"}}),
    T(new String[][]{{ 
	        "010","111","000"}, {"010", "110", "010"}, {"000", "111", "010"}, {"010", "011", "010"}}),
    O(new String[][]{{ 
	        "011", 
	        "011"},{ 
		        "011", 
		        "011"},{ 
			        "011", 
		        "011"},{ 
			        "011", 
		        "011"}}); 
	
	// describes each row
	public final boolean[][][] rotatedMinos = new boolean[4][][];
	private boolean[][] stringArrayToBoolean(String[] a) {
		boolean[][] result = new boolean[a.length][a[0].length()];
		for (int i = a.length-1; i >= 0; i--) {
			for (int j = 0; j < a[0].length(); j++) {
				result[a.length - 1 - i][j] = a[i].charAt(j) == '1';
			}
		}
		return result;
	}

	Tetramino(){
		
	}
	Tetramino(String[][] rotatedMinos) {		
		for ( int i = 0; i < 4; i++ ){
			this.rotatedMinos[i] = stringArrayToBoolean(rotatedMinos[i]);
		}
	}
}
