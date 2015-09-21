package peacenerd.game;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class StateMenu extends BasicGameState {
	
	public void init(GameContainer container, StateBasedGame game) throws SlickException {	
	}

	//Delta = time since last update in milliseconds
	//	ex: if you update 10/second, delta = 100ms
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
	}
	
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {	
	}

	public int getID() {
		return 2;
	}
}