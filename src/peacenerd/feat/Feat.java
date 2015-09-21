package peacenerd.feat;

import peacenerd.character.GameCharacter;

public class Feat {
	
	/* Feats */
	public static Feat dualWeaponFighting = new Feat(
			(user)->{
				
			});
	
	private FeatApply apply;
	private boolean hasBeenApplied;
	
	public Feat(FeatApply applicationMethod) {
		this.apply = applicationMethod;
		this.hasBeenApplied = false;
	}
	
	public void apply(GameCharacter user) {
		if (this.hasBeenApplied)
			return;
		
		this.apply.apply(user);
		this.hasBeenApplied = true;
	}
}
