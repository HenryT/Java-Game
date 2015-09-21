package peacenerd.level;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import peacenerd.character.GameCharacter;
import peacenerd.game.MainApp;

public class GridCell {
	
	public enum GridCellType {
		Wall,
		Floor;
	}
	
	private GameCharacter character;
	private boolean isOccupied;
	private boolean isBlocking;
	private boolean isEmpty;
	private Image sprite;
	private int row;
	private int col;
	
	public GridCell() {
		this.character = GameCharacter.nullChar;
		this.isOccupied = false;
		this.isBlocking = false;
		this.sprite = null;
		this.isEmpty = true;
		row = 0;
		col = 0;
	}
	
	public GridCell(String imageFile, GridCellType t) {
		this.character = GameCharacter.nullChar;
		
		if (t.equals(GridCellType.Wall)) {
			this.isOccupied = false;
			this.isBlocking = true;
			this.isEmpty = false;
		}
		
		else if (t.equals(GridCellType.Floor)) {
			this.isOccupied = false;
			this.isBlocking = false;
			this.isEmpty = false;
		}
		
		row = 0;
		col = 0;
		
		try {
			this.sprite = new Image(imageFile);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	/* Occupied - is there a game character here? */
	public boolean isOccupied() {
		return isOccupied;
	}
	
	public void setOccupied(boolean occupied) {
		this.isOccupied = occupied;
	}
	
	/* Empty - has this grid cell been given an image and location yet? */
	public boolean isEmpty() {
		return isEmpty;
	}
	
	public void setEmpty(boolean empty) {
		this.isEmpty = empty;
	}
	
	/* GameCharacters */
	public GameCharacter getChar() {
		return this.character;
	}
	
	public void placeChar(GameCharacter character) {
		this.character = character;
	}
	
	/* Blocking */
	public boolean isBlocking() {
		return isBlocking;
	}
	
	public void setBlocking(boolean blocking) {
		this.isBlocking = blocking; 
	}
	
	/* Images and Drawing */
	public void draw() {
		this.sprite.draw(this.col * MainApp.getGridSize(), this.row * MainApp.getGridSize());
	}
	
	public void setCoords(int row, int col) {
		this.row = row;
		this.col = col;
	}
}
