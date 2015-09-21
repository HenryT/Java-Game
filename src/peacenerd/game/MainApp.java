package peacenerd.game;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import peacenerd.utils.MathHelper;
import peacenerd.utils.MathHelper.Dice;

public class MainApp extends StateBasedGame {
	
	/* Globals */
	private static int gameWidth = 960;
	private static int gameHeight = 720;
	private static int gridSize = 24;
	private static int targetFPS = 60;
	
	public static int getFPS() {
		return targetFPS;
	}
	
	public static int getGridSize() {
		return gridSize;
	}
	
	public static int getGameRows() {
		return gameHeight / gridSize;
	}
	
	public static int getGameCols() {
		return gameWidth / gridSize;
	}
	
	public static int getGameWidth() {
		return gameWidth;
	}
	
	public static int getGameHeight() {
		return gameHeight;
	}
	
	public MainApp(String title) {
		super(title);
	}

	public static void main(String[] args) throws SlickException {
		//Testing Random Numbers
		/*
		int[] nums = new int[6];
		for (int i = 0; i < 1000000; i++) {
			int n = MathHelper.getRandWithBias(0, .5, 0, 5);
			nums[n]++;
		}
		
		System.out.println("Bias Rolls");
		System.out.printf("0s: %d\n1s: %d\n2s: %d\n3s: %d\n4s: %d\n5s: %d\n", nums[0], nums[1], nums[2], nums[3], nums[4], nums[5]);
		
		int[] rolls = new int[6];
		for (int i = 0; i < 1000000; i++) {
			int n = Dice.D6.roll();
			rolls[n-1]++;
		}
		
		System.out.println("Dice Rolls");
		System.out.printf("1s: %d\n2s: %d\n3s: %d\n4s: %d\n5s: %d\n6s: %d\n", rolls[0], rolls[1], rolls[2], rolls[3], rolls[4], rolls[5]);
		
		int[] rand = new int[6];
		for (int i = 0; i < 1000000; i++) {
			int n = MathHelper.getRand(0, 5);
			rand[n]++;
		}
		
		System.out.println("Random Numbers");
		for (int i = 0; i < rand.length; i++) {
			System.out.println(i + ": " + rand[i]);
		}
		*/
		AppGameContainer app = new AppGameContainer(new MainApp("Game"));
		app.setDisplayMode(getGameWidth(), getGameHeight(), false);
		app.setTargetFrameRate(getFPS());
		app.start();
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		this.addState(new StateGame());
		this.addState(new StateMenu());
		this.addState(new StateStartScreen());
	}
	
}
