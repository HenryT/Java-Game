package peacenerd.character;

import java.util.ArrayList;
import java.util.HashMap;

import peacenerd.abilities.Ability.*;
import peacenerd.abilities.*;
import peacenerd.combat.*;
import peacenerd.combat.Weapon.*;
import peacenerd.utils.*;
import peacenerd.utils.MathHelper.Dice;

public class GameCharacter extends Moveable {
	
	public enum Stat {
		Strength,
		Dexterity,
		Constitution,
		Wisdom,
		Intelligence,
		Charisma;
	}
	
	public enum ActionType {
		Swift,
		Regular,
		WeaponAttack;
		
		private static ActionType[] arr = values();
		
		static ActionType get(int i) {
			return arr[i < arr.length ? i : 0];
		}
		
		static HashMap<ActionType, Integer> actionsLeft(int ... init) {
			HashMap<ActionType, Integer> ret = new HashMap<ActionType, Integer>();
			for (int i = 0; i < arr.length; i++) {
				int j = i >= init.length ? 1 : init[i];
				ret.put(arr[i], j);
			}
			return ret;
		}
	}
	
	public enum DamageType {
		Fire(false),
		Cold(false),
		Necrotic(false),
		Radiant(false),
		Poison(false),
		Acid(false),
		Force(false),
		Psychic(false),
		Pure(false),
		Slashing(true),
		Bludgeoning(true),
		Piercing(true);
		
		private boolean isPhysical;
		DamageType(boolean b) {
			this.isPhysical = b;
		}
		
		boolean isPhysical() {
			return this.isPhysical;
		}
	}
	
	public enum StatusEffect {
		Stunned(0),
		Sleep(0),
		Immobile(0),
		Charmed(0),
		Frightened(0);
		
		private int duration;
		
		StatusEffect(int duration) {
			this.duration = duration;
		}
		
		//returns duration in turns
		int duration() {
			return this.duration;
		}
		
		void reduce() {
			this.duration--;
		}
		
		static StatusEffect get(StatusEffect s, int duration) {
			StatusEffect ret = s;
			ret.duration = duration;
			return ret;
		}
	}
	
	public static GameCharacter nullChar = new GameCharacter("", "Resources/Images/img1.png");
	
	/* Default Value Information */
	private static int minModifier = -5;
	private static int defaultStat = 10;
	private static int defaultSpeed = 6;
	private static int defaultHealth = 6;
	private static int defaultArmor = 10;
	
	private static int defaultNumSpellLevels = 9;
	private static int defaultSpellSlots = 0;
	
	//Character Information
	private String name;
	private boolean isDead;
	private String id;
	
	//Basic Stats
	private int speed;
	private int movementLeft;
	private int currentHealth;
	private int baseHealth;
	
	private int strength;
	private int dexterity;
	private int constitution;
	private int wisdom;
	private int intelligence;
	private int charisma;
	
	//Combat Information
	private Weapon equippedWeapon;
	protected Stat meleeAttackStat;
	protected Stat rangedAttackStat;
	private int baseAC;
	private int damageReduction;
	private int weaponCritRange;
	private int spellCritRange;
	
	private int numAttacks;
	private int[] actionsLeftMax;
	private HashMap<ActionType, Integer> actionsLeft;
	
	private HashMap<String, Ability> abilities;
	private ArrayList<Ability> activeAbilities;
	
	private ArrayList<Integer> spellSlots;
	private ArrayList<Integer> maxSpellSlots;
	
	private ArrayList<StatusEffect> activeStatusEffects;
	private HashMap<DamageType, Boolean> damageResistance;
	private HashMap<DamageType, Boolean> damageVuln;
	
	private HashMap<WeaponType, Boolean> weaponProfs;

	public GameCharacter(String name, String imageFile)
	{
		super(imageFile);
		this.name = name;
		this.isDead = false;
		
		this.speed = defaultSpeed;
		this.movementLeft = defaultSpeed;
		this.currentHealth = defaultHealth;
		this.baseHealth = defaultHealth;
		
		this.strength = defaultStat;
		this.dexterity = defaultStat;
		this.constitution = defaultStat;
		this.wisdom = defaultStat;
		this.intelligence = defaultStat;
		this.charisma = defaultStat;
		
		this.meleeAttackStat = Stat.Strength;
		this.rangedAttackStat = Stat.Dexterity;
		this.baseAC = defaultArmor;
		
		this.activeStatusEffects = new ArrayList<StatusEffect>();
		this.abilities = new HashMap<String, Ability>();
		this.activeAbilities = new ArrayList<Ability>();
		this.damageVuln = new HashMap<DamageType, Boolean>();
		this.damageResistance = new HashMap<DamageType, Boolean>();
		
		//Add Weapon Proficiencies - defaults to no proficiencies except unarmed
		for (int i = 0; i < WeaponType.values().length; i++) {
			this.weaponProfs.put(WeaponType.values()[i], false);
		}
		this.weaponProfs.put(WeaponType.Unarmed, true);
		
		//Equip an unarmed weapon
		this.equipWeapon(new Weapon(WeaponType.Unarmed));
		
		this.weaponCritRange = this.equippedWeapon.getCritRange();
		this.numAttacks = 1;
		
		int[] t = {1,1,numAttacks};
		this.actionsLeftMax = t;
		this.actionsLeft = ActionType.actionsLeft(actionsLeftMax);
		
		//There are 9 spell levels, so we need 10 slots to ignore the first array slot */
		this.spellSlots = new ArrayList<Integer>();
		this.maxSpellSlots = new ArrayList<Integer>();
		
		//Initialize spell slots
		for (int i = 1; i <= defaultNumSpellLevels; i++)
		{
			//Start with 0 spell slots by default
			maxSpellSlots.add(defaultSpellSlots);
			spellSlots.add(defaultSpellSlots);
		}
		
		//Generate ID
		this.id = "char" + Double.toString(Math.random() * Math.random()) 
			+ "-" + Double.toString(Math.random()) 
			+ "-" + Double.toString(Math.random())
			+ "-" + Double.toString(Math.PI * Math.random());
		
		//Default Abilities
		this.addAbility(Ability.defaultWeaponAttack);
		this.activeAbilities.add(Ability.defaultWeaponAttack);
		
		// TEST 
		this.addAbility(Ability.magicMissile);
		this.activeAbilities.add(Ability.magicMissile);
		
		
	}
	
	/* Accessing Information */
	public String getName() {
		return this.name;
	}
	
	public int getSpeed() {
		return this.speed;
	}
	
	public int getMovementLeft() {
		return this.movementLeft;
	}
	
	public Weapon getEquippedWeapon() {
		return this.equippedWeapon;
	}
	
	public Ability getAbility(String name) {
		return this.abilities.get(name);
	}
	
	public void addAbility(Ability ability) {
		this.abilities.put(ability.getName(), ability);
	}
	
	public Ability getActiveAbility(int n) {
		return activeAbilities.get(n);
	}
	
	public int getWeaponCritRange() {
		return this.weaponCritRange;
	}
	
	public void setWeaponCritRange(int newrange) {
		this.weaponCritRange = newrange;
	}
	
	public int getSpellCritRange() {
		return this.weaponCritRange;
	}
	
	public void setSpellCritRange(int newrange) {
		this.spellCritRange = newrange;
	}
	
	public void setMeleeAttackStat(Stat stat) {
		this.meleeAttackStat = stat;
	}
	
	public void setRangedAttackStat(Stat stat) {
		this.rangedAttackStat = stat;
	}
	
	public boolean hasWeaponProficiency(WeaponType wt) {
		return this.weaponProfs.get(wt);
	}
	
	public void addWeaponProficiency(WeaponType wt) {
		this.weaponProfs.put(wt, true);
	}
	
	//equips a weapon, returning true if this character has the correct proficiency, false otherwise
	public boolean equipWeapon(Weapon w) {
		if (this.hasWeaponProficiency(w.getWeaponType())) {
			this.equippedWeapon = w;
			return true;
		}
		
		return false;
	}
	
	//todo: update for unequipping specific inventory slots
	public void unequipWeapon() {
		this.equipWeapon(new Weapon(WeaponType.Unarmed));
	}
	
	/* returns number of slots left for the given level */
	public int getSpellSlots(int slot) {
		return spellSlots.get(slot);
	}
	
	/* Restores a specific amount of used spell slots
	 * usage: 
	 * 	to refresh 4 'level 1' spell slots and 3 'level 2' spell slots, call like:
	 * 	refreshSpellSlots(1, 4, 2, 3);
	 * 		- even numbered arguments refer to amount of slots to refresh
	 * 		- odd numbered arguments refer to the level of spell slot to refresh
	 * 		- amount of spells in a slot will not exceed the max spell slot amount (given by the maxSpellSlots array)
	 */
	public void refreshSpellSlots(int ... slots) {
		//odd numbered arguments means this method was called wrong
		if (slots.length % 2 != 0)
			return;
		
		for (int i = 0; i < slots.length-1; i+=2) {
			this.spellSlots.set(slots[i],
					Math.min(this.maxSpellSlots.get(i), this.spellSlots.get(i) + slots[i+1]));
		}
	}
	
	/* Restores all used spell slots */
	public void refreshSpellSlots() {
		for (int i = 1; i < this.spellSlots.size(); i++) {
			this.spellSlots.set(i, this.maxSpellSlots.get(i));
		}
	}
	
	/* Sets the max amount of spell slots
	 * usage: 
	 * 	to refresh 4 'level 1' spell slots and 3 'level 2' spell slots, call like:
	 * 	refreshSpellSlots(1, 4, 2, 3);
	 * 		- even numbered arguments refer to amount of slots to refresh
	 * 		- odd numbered arguments refer to the level of spell slot to refresh
	 * 		- amount of spells in a slot will not exceed the max spell slot amount (given by the maxSpellSlots array)
	 */
	public void setMaxSpellSlots(int ... slots) {
		//odd numbered arguments means this method was called wrong
		if (slots.length % 2 != 0)
			return;
		
		for (int i = 0; i < slots.length-1; i+=2) {
			this.maxSpellSlots.set(slots[i], slots[i+1]);
		}
	}
	
	/* Exhausts 'amount' number of level 'slot' spell slots */
	public void useSpellSlot(int slot, int amount) {
		this.spellSlots.set(slot, Math.max(0, this.spellSlots.get(slot) - amount));
	}
	
	/* Information */

	//Returns the modifier of the passed in stat
	public int getMod(Stat stat) {
		int toReturn = 0;
		switch(stat) {
			case Strength: toReturn = this.strength;
			case Dexterity: toReturn = this.dexterity;
			case Constitution: toReturn = this.constitution;
			case Wisdom: toReturn = this.wisdom;
			case Intelligence: toReturn = this.intelligence;
			case Charisma: toReturn = this.charisma;
		}
		return Math.max(minModifier, (toReturn-10)/2);
	}
	
	public int rollSave(Stat stat) {
		return Dice.D20.roll() + this.getMod(stat);
	}
	
	//Sets the base stat value to the given number
	public void setStat(Stat stat, int num) {
		switch (stat) {
			case Strength: this.strength = num;
			case Dexterity: this.dexterity = num;
			case Constitution: this.constitution = num;
			case Wisdom: this.wisdom = num;
			case Intelligence: this.intelligence = num;
			case Charisma: this.charisma = num;
		}
	}
	
	public int getCurrentHealth() {
		return this.currentHealth;
	}
	
	public int getMaxHealth() {
		return this.baseHealth;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public boolean isPlayerCharacter() {
		return false;
	}
	
	public boolean isEnemy() {
		return false;
	}
	
	public boolean isDead() {
		return this.isDead;
	}
	
	public String getID() {
		return this.id;
	}
	
	/* returns whether another object is within this character's speed */
	public boolean isInSpeedRange(Moveable m) {
		return MathHelper.lineDist(this, m) <= this.movementLeft;
	}
	
	//adds a resistance to a particular type of damage
	public void addDamageResist(DamageType type) {
		this.damageResistance.put(type, true);
	}
	
	//adds a vulnerability to a particular type of damage
	public void addDamageVuln(DamageType type) {
		this.damageVuln.put(type, true);
	}
	
	//adds a status effect for "duration" turns to this character
	public void addStatusEffect(StatusEffect effect, int duration) {
		this.activeStatusEffects.add(StatusEffect.get(effect, duration));
	}
	
	//Take Damage from a source of any kind
	public int takeDamage(int damage, DamageType type) {
		if (this.damageResistance.get(type) != null
				&& this.damageResistance.get(type)) {
			System.out.println("Resist!");
			damage *= .5;
		}
		
		if (this.damageVuln.get(type) != null
				&& this.damageVuln.get(type)) {
			System.out.println("Vulnerable!");
			damage *= 2;
		}
		
		//Attacks deal at least 1 damage
		this.currentHealth -= Math.max(1, (damage - this.damageReduction));
		
		//dead
		if (this.currentHealth <= 0) {
			this.isDead = true;
		}
		
		System.out.println(this.name + ": Health: " + this.currentHealth);
		return Math.max(1, (damage - this.damageReduction));
	}
	
	/* Movement */
	public boolean moveTo(int row, int col) {
		int reduceBy = MathHelper.lineDist(this.getRow(), this.getCol(), row, col);
		if (this.movementLeft - reduceBy >= 0 && super.moveTo(row, col)) {
			//Removes the distance moved from movementLeft
			this.movementLeft -= reduceBy;
			System.out.println("Movement left: " + this.movementLeft);
			return true;
		}
		return false;
	}
	
	public boolean move(int row, int col) {
		int reduceBy = Math.abs(row) + Math.abs(col);
		if (this.movementLeft - reduceBy >= 0 && super.move(row, col)) {
			//Removes the distance moved from movementLeft
			this.movementLeft -= reduceBy;
			return true;
		}
		return false;
	}
	
	public void forceMove(int row, int col) {
		super.moveTo(row, col);
	}
	
	public void refreshMovement() {
		this.movementLeft = this.speed;
	}
	
	//Everything that should be done when this character gets a new turn should be put in here
	public void newTurn() {
		this.refreshMovement();
		this.refreshActionTypes();
		
		//reduce all status effects by 1 turn, and remove any that are finished.
		for (int i = 0; i < this.activeStatusEffects.size(); i++) {
			this.activeStatusEffects.get(i).reduce();
			if (this.activeStatusEffects.get(i).duration() <= 0) {
				this.activeStatusEffects.remove(i);
				i--;
			}
		}
	}
	
	//returns if the user has any actions of this type left
	public boolean hasActionLeft(ActionType t) {
		return this.actionsLeft.get(t) > 0;
	}
	
	/*
	public boolean isDoneWithTurn() {
		return this.movementLeft <= 0 &&
				this.attacksLeft <= 0 &&
				this.actionsLeft
	}
	*/
	
	/* Combat Information */
	public int getAC() {
		return this.baseAC + getMod(Stat.Dexterity);
	}
	
	public int getMeleeHitBonus() {
		return 0;
	}
	
	public int getRangedHitBonus() {
		return 0;
	}
	
	public int getMeleeDamageBonus() {
		return 0;
	}
	
	public int getRangedDamageBonus() {
		return 0;
	}
	
	public int getSpellHitBonus() {
		return 0;
	}
	
	public int getSpellDC() {
		return 0;
	}
	
	//uses up a particular action type (regular, swift, etc)
	public void useActionType(ActionType t) {
		this.actionsLeft.put(t, this.actionsLeft.get(t) - 1);
		
		//using a weapon attack is your regular action, though you may be able to do it more than once 
		if (t == ActionType.WeaponAttack) {
				this.actionsLeft.put(ActionType.Regular, 
						this.actionsLeft.get(ActionType.Regular) - 1);
		}
		
		//using a regular actions means you cant attack anymore
		if (t == ActionType.Regular) {
			this.actionsLeft.put(ActionType.WeaponAttack, 0);
		}
	}
	
	//restores all uses of action types
	public void refreshActionTypes() {
		for (int i = 0; i < this.actionsLeftMax.length; i++) {
			this.actionsLeft.put(ActionType.get(i), actionsLeftMax[i]);
		}
	}
}
