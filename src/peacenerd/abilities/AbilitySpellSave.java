package peacenerd.abilities;

import java.util.Iterator;

import peacenerd.character.GameCharacter;
import peacenerd.character.GameCharacter.DamageType;
import peacenerd.utils.MathHelper.Dice;

public class AbilitySpellSave extends Ability {
	
	private int numDie;
	private Dice damageDie;
	
	public AbilitySpellSave(String name, int numTargets, int range, int spellLevel, DamageType damageType,
			int numDie, Dice die) {
		super(name, Targets.Normal, numTargets, range, spellLevel, damageType);
		this.numDie = numDie;
		this.damageDie = die;
	}
	
	public AbilitySpellSave(String name, int numTargets, int range, int spellLevel, DamageType damageType,
			int numDie, Dice die, OnHit onhit) {
		this(name, numTargets, range, spellLevel, damageType, numDie, die);
		this.onhit = onhit;
	}

	@Override
	protected boolean canTarget(GameCharacter target) {
		return target.isEnemy();
	}

	@Override
	public void use(GameCharacter user) {
		Iterator<GameCharacter> it = this.getTargets();
		/* For each target, check if they save the DC of the spell. If so, apply the damage 
		 * 	- note: no crits for spells requiring saves 
		 */
		int damage = damageDie.roll(numDie);
		
		while (it.hasNext()) {
			GameCharacter t = it.next();
			
			//deal the damage if the target fails a save
			if (t.rollSave(this.getSaveStat()) <= user.getSpellDC()) {
				t.takeDamage(damage, damageType);
				onHit(user, t);
			}
		}
		user.useSpellSlot(this.getSpellLevel(), 1);
		user.useActionType(this.actionType);
	}
}
