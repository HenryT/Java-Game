package peacenerd.utils;

import java.util.HashMap;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;

public class InputControls {
	private HashMap<Integer, Boolean> isKeyDown;
		
	public static int CursorUp = Input.KEY_W;
	public static int CursorDown = Input.KEY_S;
	public static int CursorRight = Input.KEY_D;
	public static int CursorLeft = Input.KEY_A;
	
	public static int Right = Input.KEY_RIGHT;
	public static int Left = Input.KEY_LEFT;
	public static int Up = Input.KEY_UP;
	public static int Down = Input.KEY_DOWN;
	
	public static int Select = Input.KEY_SPACE;
	public static int Exit = Input.KEY_ESCAPE;
	
	public static int AbilityOne = Input.KEY_1;
	public static int AbilityTwo = Input.KEY_2;
	
	public static int useAbility = Input.KEY_ENTER;
	
	public static int endTurn = Input.KEY_T;
	
	public InputControls() {
		isKeyDown = new HashMap<Integer, Boolean>(100);
	}
	
	public void update(GameContainer container) {
	}
}
