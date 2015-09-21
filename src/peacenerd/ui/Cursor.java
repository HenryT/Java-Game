package peacenerd.ui;

import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import peacenerd.character.Moveable;
import peacenerd.game.MainApp;
import peacenerd.utils.InputControls;

public class Cursor extends Moveable {
	
	private int counter = 0;
	private int timer = 0;
	private int scrollSpeed = 0;
	private int scrollSpeedFactor = 2;
	private int maxScrollSpeed = 30;
	private int baseScrollSpeed = 1;
	
	public Cursor() throws SlickException {
		super("Resources/Images/cursor.png");
		super.moveTo(MainApp.getGameRows()/2, MainApp.getGameCols()/2);
	}
	
	@Override
	public boolean move(int row, int col) {
		if (getRow() + row < 0 || getCol() + col < 0 
				|| getRow() + row >= MainApp.getGameRows()
				|| getCol() + col >= MainApp.getGameCols())
			return false;
		return super.move(row, col);
	}
	
	@Override
	public boolean moveTo(int row, int col) {
		if (row < 0 || col < 0 
				|| row >= MainApp.getGameRows()
				|| col >= MainApp.getGameCols())
			return false;
		return super.move(row, col);
	}
	
	//Performs input controls for the cursor
	public void update(Input input) {
		//Cursor Up
		if (input.isKeyPressed(InputControls.CursorUp)) {
			counter = 0;
			timer = 0;
			move(-1, 0);
		}
		else if (input.isKeyDown(InputControls.CursorUp)) {
			counter++;
			timer++;
			
			scrollSpeed = (int)Math.min(maxScrollSpeed, baseScrollSpeed + Math.pow((timer/scrollSpeedFactor), 2));
			if (counter >= MainApp.getFPS()/scrollSpeed) {
				counter = 0;
				move(-1, 0);
			}
		}
		
		//Cursor Down
		if (input.isKeyPressed(InputControls.CursorDown)) {
			counter = 0;
			timer = 0;
			move(1, 0);
		}
		else if (input.isKeyDown(InputControls.CursorDown)) {
			counter++;
			timer++;
			
			scrollSpeed = (int)Math.min(maxScrollSpeed, baseScrollSpeed + Math.pow((timer/scrollSpeedFactor), 2));
			if (counter >= MainApp.getFPS()/scrollSpeed) {
				counter = 0;
				move(1, 0);
			}
		}
		
		//Cursor Right
		if (input.isKeyPressed(InputControls.CursorRight)) {
			counter = 0;
			timer = 0;
			move(0, 1);
		}
		else if (input.isKeyDown(InputControls.CursorRight)) {
			counter++;
			timer++;
			
			scrollSpeed = (int)Math.min(maxScrollSpeed, baseScrollSpeed + Math.pow((timer/scrollSpeedFactor), 2));
			if (counter >= MainApp.getFPS()/scrollSpeed) {
				counter = 0;
				move(0, 1);
			}
		}
		
		//Cursor Left
		if (input.isKeyPressed(InputControls.CursorLeft)) {
			counter = 0;
			timer = 0;
			move(0, -1);
		}
		else if (input.isKeyDown(InputControls.CursorLeft)) {
			counter++;
			timer++;
			
			scrollSpeed = (int)Math.min(maxScrollSpeed, baseScrollSpeed + Math.pow((timer/scrollSpeedFactor), 2));
			if (counter >= MainApp.getFPS()/scrollSpeed) {
				counter = 0;
				move(0, -1);
			}
		}
	}
}
