package peacenerd.abilities;

import java.util.Iterator;

import peacenerd.character.GameCharacter;
import peacenerd.character.GameCharacter.ActionType;
import peacenerd.character.GameCharacter.DamageType;
import peacenerd.combat.Weapon;
import peacenerd.utils.MathHelper.Dice;

public class AbilityWeaponAttack extends Ability {
			
	public AbilityWeaponAttack() {
		super("Default Weapon Attack", Targets.Normal, 1, 1, 0, null);
		this.actionType = ActionType.WeaponAttack;
	}
	
	@Override
	public void use(GameCharacter user) {
		Weapon w = user.getEquippedWeapon();
		
		//a weapon attack uses the damage type of the weapon, not the ability superclass
		DamageType dtype = w.getDamageType();
		
		/* Hit Roll */
		int attackRoll = w.isRanged() ? Dice.D20.roll() + user.getRangedHitBonus() + w.getHitMod() 
					: Dice.D20.roll() + user.getMeleeHitBonus() + w.getHitMod();
		
		/* Damage Roll*/
		int damage = w.isRanged() ? w.attack() + user.getRangedDamageBonus() 
					: w.attack() + user.getMeleeDamageBonus();
		//crit
		if (attackRoll >= user.getWeaponCritRange()) {
			damage += w.isRanged() ? w.attack() + user.getRangedDamageBonus() 
					: w.attack() + user.getMeleeDamageBonus();
			System.out.println("Crit!");
		}
		
		Iterator<GameCharacter> it = this.getTargets();
		
		/* For each target, check if hit roll meets target AC. If so, apply the damage */
		while (it.hasNext()) {
			GameCharacter t = it.next();
			
			if (attackRoll >= t.getAC()) {
				
				//apply onhit effect of the weapon
				w.onHit(user, t);
				t.takeDamage(damage, dtype);
			}			
		}
		user.useActionType(this.actionType);
	}
	
	/* Determines if the target is a valid target for this ability */
	protected boolean canTarget(GameCharacter target) {
		return target.isEnemy();
	}
}
