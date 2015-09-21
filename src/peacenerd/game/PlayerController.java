package peacenerd.game;

import java.util.ArrayList;
import java.util.HashMap;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import peacenerd.abilities.Ability;
import peacenerd.character.*;
import peacenerd.character.GameCharacter.ActionType;
import peacenerd.game.TurnController.Turn;
import peacenerd.level.Level;
import peacenerd.ui.CharacterHUD;
import peacenerd.ui.Cursor;
import peacenerd.utils.InputControls;

public class PlayerController {

	private HashMap<String, PlayerCharacter> characters;
	private PlayerCharacter selectedChar;
	private CharacterHUD hud;

	private boolean isAbilityActivated;
	private Ability activeAbility;
	private ArrayList<GameCharacter> targets;
	private int activeAbilityNum;

	public PlayerController() throws SlickException {
		characters = new HashMap<String, PlayerCharacter>();
		selectedChar = new PlayerCharacter(GameCharacter.nullChar);
		hud = new CharacterHUD();
		targets = new ArrayList<GameCharacter>();
		isAbilityActivated = false;
		activeAbility = null;
		activeAbilityNum = -1;
	}

	public void addCharacter(PlayerCharacter character) {
		this.characters.put(character.getName(), character);
	}

	public PlayerCharacter getCharacter(String name) {
		return characters.get(name);
	}

	/* Selected Char Methods */
	public PlayerCharacter getSelectedChar() {
		return this.selectedChar;
	}

	private boolean hasSelectedChar() {
		return !selectedChar.getName().equals("");
	}

	private void setSelectedChar(String name) {
		selectedChar = characters.get(name);
		if (selectedChar == null)
			clearSelectedChar();
	}

	private void clearSelectedChar() {
		selectedChar = new PlayerCharacter(GameCharacter.nullChar);
	}

	private boolean selectChar(Level currentLevel, int row, int col) {
		GameCharacter character = currentLevel.getCharAt(row, col);

		if (character.isPlayerCharacter()) {
			setSelectedChar(character.getName());
			return true;
		}

		return false;
	}

	/* Update */

	/* Handles the logic of all player character actions and UI elements */
	public void update(Input input, Level currentLevel, Cursor cursor) {
		/* Key Presses */

		/* Key Presses that can only be used when it is the player's turn */
		if (currentLevel.turn().getCurrentTurn() == Turn.Player) {

			/* Select Key pressed */
			if (input.isKeyPressed(InputControls.Select)) {
				int r = cursor.getRow();
				int c = cursor.getCol();

				/* Select a character */
				if (!hasSelectedChar() && currentLevel.getCharAt(r, c).isPlayerCharacter()) {
					selectChar(currentLevel, r, c);
				}

				/* Player character already selected */
				else if (hasSelectedChar()) {

					/* Select targets for an ability */
					if (isAbilityActivated) {
						if (targets.size() < this.activeAbility.getMaxTargets()
								&& this.activeAbility.isValidTarget(this.selectedChar, currentLevel.getCharAt(r, c))) {
							System.out.println(currentLevel.getCharAt(r, c).getName() + " added as target");
							targets.add(currentLevel.getCharAt(r, c));
						}
					}

					/* Move a character */
					if (selectedChar.isInSpeedRange(cursor)
							// && !currentLevel.isBlocking(r, c)
							&& !currentLevel.isOccupied(r, c)) {
						moveCharTo(currentLevel, selectedChar, r, c);
					}
				}
			}

			/* Automatically use ability when max targets have been selected */
			if (this.isAbilityActivated && this.activeAbility.getMaxTargets() == this.targets.size()) {
				useAbility(this.activeAbility);
			}

			/* Use Ability Key Pressed */
			if (input.isKeyPressed(InputControls.useAbility)) {
				// Ability will still be used if less than max targets selected
				if (this.isAbilityActivated) {
					useAbility(this.activeAbility);
				}

				System.out.println(this.selectedChar.getID());
			}

			/* Ability Key Pressed */
			if (input.isKeyPressed(InputControls.AbilityOne)) {
				int abilityNum = 0;
				if (this.hasSelectedChar() && abilityNum != this.activeAbilityNum && this.getSelectedChar()
						.hasActionLeft(this.getSelectedChar().getActiveAbility(abilityNum).getActionType())) {
					setActiveAbility(this.getSelectedChar().getActiveAbility(abilityNum));
					this.activeAbilityNum = abilityNum;
				}
			}

			if (input.isKeyPressed(InputControls.AbilityTwo)) {
				int abilityNum = 1;
				if (this.hasSelectedChar() && abilityNum != this.activeAbilityNum && this.getSelectedChar()
						.hasActionLeft(this.getSelectedChar().getActiveAbility(abilityNum).getActionType())) {
					setActiveAbility(this.getSelectedChar().getActiveAbility(abilityNum));
					this.activeAbilityNum = abilityNum;
				}
			}

			/* End Turn key pressed */
			if (input.isKeyPressed(InputControls.endTurn)) {
				if (currentLevel.turn().getCurrentTurn() == Turn.Player) {
					currentLevel.turn().nextTurn();
					for (PlayerCharacter p : this.characters.values()) {
						p.newTurn();
					}
				}
			}

			/* Exit key pressed */
			if (input.isKeyPressed(InputControls.Exit)) {
				/* Stop activation of an ability */
				if (isAbilityActivated) {
					this.activeAbility.clearTargets();
					isAbilityActivated = false;
					this.activeAbility = null;

					System.out.println("Ability de-activated");
				}

				/* Un-select a character */
				else {
					clearSelectedChar();
				}
			}
		}
	}

	private void setActiveAbility(Ability ability) {
		System.out.println(ability.getName() + " activated");
		this.isAbilityActivated = true;
		this.activeAbility = ability;
	}

	private void useAbility(Ability ability) {
		ability.setTargets(targets);
		ability.use(selectedChar);
		ability = null;
		this.isAbilityActivated = false;
		this.targets.clear();
		this.activeAbilityNum = -1;
	}

	/* Movement of Character objects */
	// returns true if the move is successful, false otherwise.
	private boolean moveCharTo(Level currentLevel, GameCharacter character, int row, int col) {
		if (currentLevel.isOccupied(row, col) || row < 0 || col < 0 || row >= MainApp.getGameRows()
				|| col >= MainApp.getGameCols())
			return false;

		currentLevel.setOccupied(false, character.getRow(), character.getCol());
		currentLevel.removeChar(character.getRow(), character.getCol());
		character.moveTo(row, col);
		currentLevel.setOccupied(true, row, col);
		currentLevel.placeChar(character, row, col);
		return true;
	}

	// returns true if the move is successful, false otherwise.
	private boolean moveChar(Level currentLevel, GameCharacter character, int row, int col) {
		int r = character.getRow();
		int c = character.getCol();

		// Fail conditions: don't allow them to move to an occupied space or off
		// the grid boundaries.
		if (currentLevel.isOccupied(r + row, c + col) || r + row < 0 || c + col < 0 || r + row >= MainApp.getGameRows()
				|| c + col >= MainApp.getGameCols())
			return false;

		currentLevel.setOccupied(false, character.getRow(), character.getCol());
		currentLevel.removeChar(row, col);
		character.move(row, col);
		currentLevel.setOccupied(true, r + row, c + col);
		currentLevel.placeChar(character, r + row, c + col);
		return true;
	}

	/* Drawing */

	/* Main Drawing Method */
	public void draw(Cursor cursor, Level currentLevel, Graphics g) {
		drawMoveGrid(g);
		drawCharacters();
		drawHUD(cursor, currentLevel, g);
	}

	/* Draws all of the player's characters */
	private void drawCharacters() {
		for (PlayerCharacter c : characters.values())
			c.draw();
	}

	/* Draw shaded region of where the character can move to */
	private void drawMoveGrid(Graphics g) {
		if (selectedChar.getName().equals(""))
			return;

		int speed = selectedChar.getMovementLeft();

		// draws bottom to top
		Color gridColor = new Color(1f, 1f, 1f, 0.1f);
		g.setColor(gridColor);

		int j = speed - 1;
		for (int i = 0; i < (speed + 1); i++) {
			g.fillRect((MainApp.getGridSize() * (selectedChar.getCol() - i)),
					(MainApp.getGridSize() * (selectedChar.getRow() + speed - i)),
					(MainApp.getGridSize() * (1 + (i * 2))), MainApp.getGridSize());
		}
		for (int i = speed + 1; i < (2 * (speed + 1)) - 1; i++) {
			g.fillRect((MainApp.getGridSize() * (selectedChar.getCol() - j)),
					(MainApp.getGridSize() * (selectedChar.getRow() + speed - i)),
					(MainApp.getGridSize() * (1 + (j * 2))), MainApp.getGridSize());
			j--;
		}
	}

	/* Draw HUD */
	private void drawHUD(Cursor cursor, Level currentLevel, Graphics g) {
		int r = cursor.getRow();
		int c = cursor.getCol();

		/* TEST */
		Color gridColor = new Color(1f, 1f, 1f, 1f);
		g.setColor(gridColor);
		g.drawString("Selected Ability: " + activeAbility == null ? activeAbility.getName() : "", 50, 600);

		/* Cursor is hovering over a player character */
		if (currentLevel.getCharAt(r, c).isPlayerCharacter()) {
			hud.drawFullPlayerCharacterHUD(g, (PlayerCharacter) currentLevel.getCharAt(r, c));
		}

		else {
			/*
			 * Player has selected a character, but cursor not hovering over a
			 * player character
			 */
			if (hasSelectedChar()) {
				hud.drawSelectedCharacterHUD(g, getSelectedChar());
			}

			/* Cursor is hovering over a non-player character */
			if (!currentLevel.isOccupied(r, c)) {
				hud.drawCharacterHUD(g, currentLevel.getCharAt(r, c));
			}
		}
	}

}
