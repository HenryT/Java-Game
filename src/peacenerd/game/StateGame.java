package peacenerd.game;

import java.util.HashMap;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import peacenerd.character.*;
import peacenerd.character.GameCharacter.DamageType;
import peacenerd.enemy.Enemy;
import peacenerd.game.TurnController.Turn;
import peacenerd.level.Level;
import peacenerd.ui.CharacterHUD;
import peacenerd.ui.Cursor;
import peacenerd.utils.InputControls;

public class StateGame extends BasicGameState {
	
	//Basic Game Entities
	private PlayerController player;
	private HashMap<String, Level> levels;
	private Level currentLevel;
	private Cursor cursor;
	
	//Test Objects
	GameCharacter c1;
	GameCharacter c2;
	GameCharacter c3;
	
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		//alien = new Image("Resources/Images/img1.png");
		//tiles = new SpriteSheet(tileset, 24, 24); //24, 24 is the size of each tile in the sheet
		//circle = new Circle(100, 100, 10);
		
		String levelname = "Level 1";
		
		//Basic Game Entities
		cursor = new Cursor();
		player = new PlayerController();
		levels = new HashMap<String, Level>();
		levels.put(levelname, new Level(levelname));
		currentLevel = levels.get(levelname);
		
		
		//Test Objects
		player.addCharacter(new PlayerCharacter("Guy1", "Resources/Images/img1.png"));
		player.addCharacter(new PlayerCharacter("Guy2", "Resources/Images/img1.png"));
		player.addCharacter(new PlayerCharacter("Guy3", "Resources/Images/img1.png"));
		
		c1 = new Enemy("Guy1", "Resources/Images/img2.png");
		c2 = new Enemy("Guy1", "Resources/Images/img2.png");
		c3 = new Enemy("Guy1", "Resources/Images/img2.png");
		c1.addDamageVuln(DamageType.Force);
		
		forceMoveTo(player.getCharacter("Guy1"), 10, 10);
		forceMoveTo(player.getCharacter("Guy2"), 15, 5);
		forceMoveTo(player.getCharacter("Guy3"), 2, 10);
		
		player.getCharacter("Guy1").refreshMovement();
		player.getCharacter("Guy2").refreshMovement();
		player.getCharacter("Guy3").refreshMovement();
		
		forceMoveTo(c1, 5, 5);
		forceMoveTo(c2, 20, 3);
		forceMoveTo(c3, 15, 10);
	}

	//Delta = time since last update in milliseconds
	//	ex: if you update 10/second, delta = 100ms
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		//Input Controls
		Input input = container.getInput();
		cursor.update(input);
		player.update(input, currentLevel, cursor);
		if (currentLevel.turn().getCurrentTurn() == Turn.Enemy) {
			currentLevel.turn().nextTurn();
		}
			
	}
	
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		/* Draw Level */
		currentLevel.draw();
		
		/* Draw player characters and UI*/
		player.draw(cursor, currentLevel, g);		
		
		/* Test Enemies */
		c1.draw();
		c2.draw();
		c3.draw();
		
		/* Draw Cursor */
		cursor.draw();
		
		
		Color gridColor = new Color(1f, 1f, 1f, 1f);
		g.setColor(gridColor);
		g.drawString("Selected Character: " + player.getSelectedChar().getName(), 50, 650);
		g.drawString("Current Grid Cell: " + currentLevel.isOccupied(cursor.getRow(), cursor.getCol()) + ", " 
				+ currentLevel.getCharAt(cursor.getRow(), cursor.getCol()).getName(), 50, 670);
		
	}
	
	/* Handler Methods */
	
	//Used for setting spawn points
	private boolean forceMoveTo(GameCharacter character, int row, int col) {
		if (currentLevel.isOccupied(row, col)
				|| row < 0 || col < 0
				|| row >= MainApp.getGameRows() || col >= MainApp.getGameCols())
			return false;
		
		currentLevel.setOccupied(false, character.getRow(), character.getCol());
		currentLevel.removeChar(character.getRow(), character.getCol());
		character.forceMove(row, col);
		currentLevel.setOccupied(true, row, col);
		currentLevel.placeChar(character, row, col);
		return true;
	}

	public int getID() {
		return 0;
	}
}