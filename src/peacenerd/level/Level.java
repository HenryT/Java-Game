package peacenerd.level;

import java.util.HashMap;

import org.newdawn.slick.SlickException;

import peacenerd.character.GameCharacter;
import peacenerd.game.MainApp;
import peacenerd.game.TurnController;
import peacenerd.game.TurnController.Turn;
import peacenerd.generate.*;

public class Level {
	private HashMap<String, Grid> rooms;
	private Grid currentRoom;
	private String name;
	private GridGenerator rgen;
	private TurnController turns;

	public Level(String name) throws SlickException {
		this.name = name;
		rooms = new HashMap<String, Grid>();
		String roomname = "Room";
		//rooms.put(roomname, new Grid(roomname, "Resources/Tilesets/cragmaw-castle-use-later.tmx"));
		rgen = new GridGenerator();
		currentRoom = rgen.generateGrid("Room 1", MainApp.getGameRows(), MainApp.getGameCols());
		turns = new TurnController(Turn.Player);
	}
	
	/* Turn */
	public TurnController turn() {
		return this.turns;
	}
	
	/* Occupied */
	public boolean isOccupied(int row, int col) {
		return currentRoom.isOccupied(row, col);
	}

	public void setOccupied(boolean occupied, int row, int col) {
		currentRoom.setOccupied(occupied, row, col);
	}
	
	/* Blocking */
	public void setBlocking(boolean blocking, int row, int col) {
		currentRoom.setBlocking(blocking, row, col);
	}
	
	public boolean isBlocking(int row, int col) {
		return true;
	}
	
	/* GameCharacter */
	public GameCharacter getCharAt(int row, int col) {
		return currentRoom.getCharAt(row, col);
	}

	public void placeChar(GameCharacter character, int row, int col) {
		currentRoom.placeChar(character, row, col);
	}
	
	public void removeChar(int row, int col) {
		currentRoom.removeChar(row, col);
	}
	
	/* Drawing */
	public void draw() {
		currentRoom.draw();
	}
}
