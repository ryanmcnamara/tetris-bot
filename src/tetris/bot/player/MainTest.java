package tetris.bot.player;

public class MainTest {

	public static void main2(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		Thread.sleep(2000L);
		GamePlayer player = new TetrisFriendsGamePlayer();
		player.startGame();
		System.out.println(player.getNextTetraminos());
	}

}
