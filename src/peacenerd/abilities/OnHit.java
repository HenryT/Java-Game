package peacenerd.abilities;

import peacenerd.character.GameCharacter;

public interface OnHit {
	void onhit(GameCharacter user, GameCharacter target);
}
