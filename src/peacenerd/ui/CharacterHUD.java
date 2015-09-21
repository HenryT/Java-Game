package peacenerd.ui;

import java.awt.Font;
import java.io.InputStream;
import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.util.ResourceLoader;

import peacenerd.character.*;
import peacenerd.game.MainApp;

public class CharacterHUD {
	
	private static final float STAT_TEXT_FONT_SIZE = 24f;
	private static final String STAT_TEXT_FONT_FILE = "Resources/Fonts/stat_text.ttf";
	
	private static final int MARGIN_X = 1;
	private static final int MARGIN_Y = 1;
	
	private static final int STAT_RECT_WIDTH = 6;
	private static final int STAT_RECT_HEIGHT = 6;
	
	private static final float STAT_TEXT_MARGIN_X = .2f;
	private static final float ABILITY_TEXT_MARGIN_X = .2f;
	
	private static final float STAT_TEXT_LINE_SPACING = STAT_TEXT_FONT_SIZE;
	private static final float ABILITY_TEXT_LINE_SPACING = 1f;
	
	private static final int ABILITY_WIDTH = 6;
	private static final int ABILITY_HEIGHT = 2;
	
	private static final Color STAT_COLOR = new Color(255, 255, 255, .7f);
	private static final Color ABILITY_COLOR = new Color(100, 150, 255, .5f);
	
	private static final Color STAT_TEXT_COLOR = Color.black;
	private static final Color ABILITY_TEXT_COLOR = Color.black;
	
	private TrueTypeFont textFont;
	
	public CharacterHUD () {
		//Load Font
		try {
			InputStream inputStream	= ResourceLoader.getResourceAsStream(STAT_TEXT_FONT_FILE);
 
			Font awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
			awtFont = awtFont.deriveFont(STAT_TEXT_FONT_SIZE); // set font size
			textFont = new TrueTypeFont(awtFont, true);
 
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/* Used when cursor hovers over a player character */
	public void drawFullPlayerCharacterHUD(Graphics g, PlayerCharacter character) {
		drawStatHUD(g,character);
		drawAbilityHUD(g,character);
	}
	
	/* Used for when a player character is selected, but not hovered over */
	public void drawSelectedCharacterHUD(Graphics g, PlayerCharacter character) {
		drawAbilityHUD(g,character);
	}
	
	/* Used for when a non-player character is hovered over */
	public void drawCharacterHUD(Graphics g, GameCharacter character) {
		drawEnemyHUD(g,character);
	}
	
	/* Drawing Helper Methods */
	private void drawStatHUD(Graphics g, PlayerCharacter character) {
		int gsize = MainApp.getGridSize();
		/* Information to display */
		String name = character.getName();
		
		ArrayList<String> toDisplay = new ArrayList<String>();
		toDisplay.add("Health: " + Integer.toString(character.getCurrentHealth()));
		toDisplay.add("AC: " + Integer.toString(character.getAC()));
		toDisplay.add("Movement: " + Integer.toString(character.getMovementLeft()));
		
		
		g.setColor(STAT_COLOR);
		g.fillRect(gsize * MARGIN_X, 
				gsize * MARGIN_Y, 
				gsize * STAT_RECT_WIDTH, 
				gsize * STAT_RECT_HEIGHT);
		
		/* Draw Character Name */ 
		textFont.drawString(
				(gsize * MARGIN_X) + (gsize * STAT_TEXT_MARGIN_X), 
				(gsize * MARGIN_Y), 
				name, STAT_TEXT_COLOR);
		
		/* Draw other character stats */
		for (int i = 1; i <= toDisplay.size(); i++) {
			textFont.drawString((gsize * STAT_TEXT_MARGIN_X) + (gsize * MARGIN_X), 
					((i) * STAT_TEXT_LINE_SPACING) + (gsize * MARGIN_Y), 
					toDisplay.get(i-1), STAT_TEXT_COLOR);
		}
	}
	
	private void drawAbilityHUD(Graphics g, PlayerCharacter character) {
	
	}
	
	private void drawEnemyHUD(Graphics g, GameCharacter character) {
		
	}

}
