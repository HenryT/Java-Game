package peacenerd.abilities;

import java.util.Iterator;

import peacenerd.character.GameCharacter;
import peacenerd.character.GameCharacter.ActionType;
import peacenerd.character.GameCharacter.DamageType;
import peacenerd.utils.MathHelper.Dice;

public class AbilitySpellAttack extends Ability {
	
	private int numDie;
	private Dice damageDie;
	
	public AbilitySpellAttack(String name, int numTargets, int range, int spellLevel, DamageType damageType,
			int numDie, Dice die) {
		super(name, Targets.Normal, numTargets, range, spellLevel, damageType);
		this.numDie = numDie;
		this.damageDie = die;
	}
	
	public AbilitySpellAttack(String name, int numTargets, int range, int spellLevel, DamageType damageType,
			int numDie, Dice die, OnHit onhit) {
		super(name, Targets.Normal, numTargets, range, spellLevel, damageType);
		this.onhit = onhit;
		this.numDie = numDie;
		this.damageDie = die;
	}

	@Override
	protected boolean canTarget(GameCharacter target) {
		return target.isEnemy();
	}

	@Override
	public void use(GameCharacter user) {
		Iterator<GameCharacter> it = this.getTargets();
		/* For each target, check if hit roll meets target AC. If so, apply the damage */
		while (it.hasNext()) {
			GameCharacter t = it.next();

			int attackRoll = Dice.D20.roll() + user.getSpellHitBonus();			
			if (attackRoll > t.getAC()) {
				int damage = damageDie.roll(numDie);
				
				//crit
				if (attackRoll >= user.getSpellCritRange()) {
					damage += damageDie.roll(numDie);
					System.out.println("Crit!");
				}
					
				t.takeDamage(damage, damageType);
				onHit(user, t);
			}
		}
		user.useSpellSlot(this.getSpellLevel(), 1);
		user.useActionType(this.actionType);
	}
}
