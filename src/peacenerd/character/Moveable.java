package peacenerd.character;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import peacenerd.game.MainApp;

public abstract class Moveable {
	private int row;
	private int col;
	private Image sprite;
	
	public Moveable(String imageFile) {
		this.row = 0;
		this.col = 0;
		try {
			this.sprite = new Image(imageFile);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	//Getters
	public int getRow() {
		return row;
	}
	
	public int getCol() {
		return col;
	}
	
	public String getImageFile() {
		return sprite.getResourceReference();
	}
	
	//Move Functions
	public boolean moveTo(int row, int col) {
		this.row = row;
		this.col = col;
		return true;
	}
	
	public boolean move(int row, int col) {
		this.row += row;
		this.col += col;
		return true;
	}
	
	//Basic Draw
	public void draw() {
		int x = MainApp.getGridSize() * this.col;
		int y = MainApp.getGridSize() * this.row;
		sprite.draw(x, y);
	}
}
