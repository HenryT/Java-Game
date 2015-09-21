package peacenerd.characterclasses;

import java.util.ArrayList;
import java.util.Iterator;

import peacenerd.abilities.Ability;
import peacenerd.character.GameCharacter.Stat;
import peacenerd.character.PlayerCharacter;
import peacenerd.utils.MathHelper.Dice;

public abstract class CharacterClass {
	
	public enum ClassName {
		Barbarian,
		Bard,
		Cleric,
		Druid,
		Fighter,
		Monk,
		Paladin,
		Ranger,
		Rogue,
		Sorcerer,
		Warlock,
		Wizard;
	}
	
	//first index is the level of caster, second is number of spell slots for given spell level
	public static int[][] multiClassSpellSlots = 
		{
		{},
		{0,0,0,0,0,0,0,0,0,0},
		{0,3,0,0,0,0,0,0,0,0},
		{0,4,2,0,0,0,0,0,0,0},
		{0,4,3,0,0,0,0,0,0,0},
		{0,4,3,2,0,0,0,0,0,0},
		{0,4,3,3,0,0,0,0,0,0},
		{0,4,3,3,1,0,0,0,0,0},
		{0,4,3,3,2,0,0,0,0,0},
		{0,4,3,3,3,1,0,0,0,0},
		{0,4,3,3,3,2,0,0,0,0},
		{0,4,3,3,3,2,1,0,0,0},
		{0,4,3,3,3,2,1,0,0,0},
		{0,4,3,3,3,2,1,1,0,0},
		{0,4,3,3,3,2,1,1,0,0},
		{0,4,3,3,3,2,1,1,1,0},
		{0,4,3,3,3,2,1,1,1,0},
		{0,4,3,3,3,2,1,1,1,1},
		{0,4,3,3,3,2,1,1,1,1},
		{0,4,3,3,3,2,2,1,1,1},
		{0,4,3,3,3,2,2,2,1,1}};
	
	public static int saveBonus = 2;
	
	private ClassName className;
	private Dice hitDie;
	private int level;
	private boolean isSpellCaster;
	private Stat spellCastingStat;
	private ArrayList<Ability> classAbilities;
	private ArrayList<Ability> classSpells;
		
	public CharacterClass(ClassName name, Dice hitdie, boolean spellcaster, Stat spellstat) {
		this.className = name;
		this.hitDie = hitdie;
		this.isSpellCaster = spellcaster;
		this.spellCastingStat = spellstat;
		
		this.level = 1;
		this.classAbilities = new ArrayList<Ability>();
		this.classSpells = new ArrayList<Ability>();
	}
	
	public abstract void applyStartingBenefits(PlayerCharacter character);
	
	public abstract void applyMultiClassBenefits(PlayerCharacter character);
	
	public abstract void levelUp(PlayerCharacter character);
	
	public Iterator<Ability> getClassAbilities() {
		return this.classAbilities.iterator();
	}
	
	public Iterator<Ability> getClassSpells() {
		return this.classSpells.iterator();
	}
	
	public boolean isSpellCaster() {
		return this.isSpellCaster;
	}
	
	public int getLevel() {
		return this.level;
	}
	
	public Dice getHitDie() {
		return this.hitDie;
	}
	
	public ClassName getClassName() {
		return this.className;
	}
	
	public Stat getSpellCastingStat() {
		return this.spellCastingStat;
	}
}
