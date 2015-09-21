package peacenerd.game;

public class TurnController {
	
	public enum Turn {
		Player,
		Enemy;
	}
		
	private Turn turn;
	public TurnController(Turn init) {
		turn = init;
	}
	
	public Turn getCurrentTurn() {
		return this.turn;
	}
	
	public void nextTurn() {
		this.turn = this.turn == Turn.Enemy ? Turn.Player : Turn.Enemy;
	}
}
