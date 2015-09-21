package peacenerd.abilities;

import java.util.ArrayList;
import java.util.Iterator;

import peacenerd.character.*;
import peacenerd.character.GameCharacter.ActionType;
import peacenerd.character.GameCharacter.DamageType;
import peacenerd.character.GameCharacter.Stat;
import peacenerd.combat.*;
import peacenerd.game.*;
import peacenerd.utils.MathHelper;
import peacenerd.utils.MathHelper.Dice;

public abstract class Ability {
	
	public enum Targets {
		Normal,
		Allies,
		AoE,
		Self;
	}
	
	public static Ability defaultWeaponAttack = new AbilityWeaponAttack();
	public static Ability magicMissile = new AbilitySpellAttack("Magic Missile", 3, 60, 1, 
			DamageType.Force, 1, Dice.D4,
			(user, target) -> 
				target.takeDamage(1, DamageType.Force));
	
	private String name;
	private Targets targetingType;
	private ArrayList<GameCharacter> targets;
	private int spellLevel;
	private int range;
	private int maxTargets;
	protected OnHit onhit;
	protected ActionType actionType;
	protected DamageType damageType;
	
	protected boolean needsSave;
	protected Stat saveStat;
	
	public Ability(String name, Targets numTargets, int maxTargets, int range, int spellLevel, DamageType damageType) {
		this.targets = new ArrayList<GameCharacter>();
		this.name = name;
		this.targetingType = numTargets;
		this.range = range;
		this.maxTargets = maxTargets;
		this.spellLevel = spellLevel;
		this.damageType = damageType;

		this.onhit = (user,target)->{};
		this.actionType = ActionType.Regular;
		
		this.needsSave = false;
		this.saveStat = null;
	}
	
	public Ability(String name, Targets numTargets, int maxTargets, int range, int spellLevel, DamageType damageType,
			Stat saveStat) {
		this(name, numTargets, maxTargets, range, spellLevel, damageType);
		this.needsSave = true;
		this.saveStat = saveStat;
	}
	
	public void setTargets(ArrayList<GameCharacter> targets) {
		this.targets = targets;
	}
	
	public Targets getTargetingType() {
		return this.targetingType;
	}
	
	public Iterator<GameCharacter> getTargets() {
		return this.targets.iterator();
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getRange() {
		return this.range;
	}
	
	public void setRange(int range) {
		this.range = range;
	}
	
	public int getSpellLevel() {
		return this.spellLevel;
	}
	
	public void setSpellLevel(int level) {
		this.spellLevel = level;
	}
	
	public Stat getSaveStat() {
		return this.saveStat;
	}
	
	public boolean needsSave() {
		return this.needsSave;
	}
	
	public int getMaxTargets() {
		return this.maxTargets;
	}
	
	public void setMaxTargets(int targets) {
		this.maxTargets = targets;
	}
	
	public void clearTargets() {
		this.targets.clear();
	}
	
	public boolean equals(Ability other) {
		return this.getName().equals(other.getName());
	}
	
	public boolean isValidTarget(GameCharacter user, GameCharacter target) {
		return canTarget(target) && MathHelper.lineDist(user, target) <= this.range;
	}
	
	public ActionType getActionType() {
		return this.actionType;
	}
	
	protected void onHit(GameCharacter user, GameCharacter target) {
		onhit.onhit(user, target);
	}
	
	protected abstract boolean canTarget(GameCharacter target);

	public abstract void use(GameCharacter user);

}
