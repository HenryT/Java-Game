package peacenerd.character;

import java.util.ArrayList;
import java.util.HashMap;

import peacenerd.abilities.Ability;

public class PlayerCharacter extends GameCharacter {
	
	//Misc damage and hit bonuses for weapons
	private int hitBonus;
	private int damageBonus;
	private ArrayList<Ability> activeAbilities;

	
	public PlayerCharacter(String name, String imageFile) {
		super(name, imageFile);
		this.level = 1;
		this.hitBonus = 0;
		this.damageBonus = 0;
		this.activeAbilities = new ArrayList<Ability>();
		
		this.activeAbilities.add(Ability.defaultWeaponAttack);
		this.activeAbilities.add(Ability.magicMissile);

	}
	
	public PlayerCharacter(GameCharacter character) {
		this(character.getName(), character.getImageFile());
	}
	
	@Override
	public boolean isPlayerCharacter() {
		return true;
	}
	
	//Proficiency bonus
	public int getProfBonus() {
		if (this.level < 5)
			return 2;
		else if (this.level < 9)
			return 3;
		else if (this.level < 13)
			return 4;
		else if (this.level < 17)
			return 5;
		else return 6;
	}
	
	public Ability getActiveAbility(int n) {
		return activeAbilities.get(n);
	}
	
	/* Weapon Attack Bonuses */
	@Override
	public int getMeleeHitBonus() {
		//Mod for melee attack stat + proficieny bonus + bonus on weapon + misc hit bonus + hit bonus for specific weapon type
		return this.getMod(this.meleeAttackStat) + this.getProfBonus() + 
				getEquippedWeapon().getHitMod() + this.hitBonus
				+ this.getWeaponHitBonus(this.getEquippedWeapon().getWeaponType());
	}
	@Override
	public int getRangedHitBonus() {
		//Mod for ranged attack stat + proficieny bonus + bonus on weapon + misc hit bonus + hit bonus for specific weapon type
		return this.getMod(this.rangedAttackStat) + this.getProfBonus() +
				getEquippedWeapon().getHitMod() + this.hitBonus
				+ this.getWeaponHitBonus(this.getEquippedWeapon().getWeaponType());
	}
	@Override
	public int getMeleeDamageBonus() {
		//Mod for melee attack stat + misc damage bonus + damage bonus for specific weapon
		//note: weapon damage bonus is included in the weapon itself
		return this.getMod(this.meleeAttackStat) + this.damageBonus
				+ this.getWeaponDamageBonus(this.getEquippedWeapon().getWeaponType());
	}
	@Override
	public int getRangedDamageBonus() {
		//Mod for ranged attack stat + misc damage bonus + damage bonus for specific weapon
		//note: weapon damage bonus is included in the weapon itself
		return this.getMod(this.rangedAttackStat) + this.damageBonus
				+ this.getWeaponDamageBonus(this.getEquippedWeapon().getWeaponType());
	}
	
	/* Spell Hit and DC */
	@Override
	public int getSpellHitBonus() {
		return this.getProfBonus() + this.getMod(this.spellCastingStat);
	}
	
	@Override
	public int getSpellDC() {
		return 8 + this.getProfBonus() + this.getMod(this.spellCastingStat);
	}
	
}
