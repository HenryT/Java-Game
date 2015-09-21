package peacenerd.combat;

import peacenerd.abilities.OnHit;
import peacenerd.character.GameCharacter;
import peacenerd.character.GameCharacter.DamageType;
import peacenerd.utils.MathHelper;
import peacenerd.utils.MathHelper.*;

public class Weapon {
	
	public enum WeaponType {
		Unarmed(1,Dice.D4, DamageType.Bludgeoning, true, false, false),
		Longsword(1,Dice.D8, DamageType.Slashing, false, false, false),
		Shortsword(1,Dice.D6, DamageType.Slashing, true, false, false),
		Mace(1,Dice.D6, DamageType.Bludgeoning, true, false, false),
		Shortbow(1,Dice.D6, DamageType.Piercing, false, false, true, 4),
		Longbow(1,Dice.D6, DamageType.Piercing, false, false, true, 10),
		Greatsword(2, Dice.D6, DamageType.Slashing, false, false, true);
		
		private int numDice;
		private Dice damageDie;
		
		private boolean isRanged;
		private boolean isLight;
		private boolean isFinesse;
		private boolean isTwoHanded;
		
		private int attackRange;
		
		private DamageType damageType;
		
		WeaponType(int num, Dice d, DamageType dt, boolean light, boolean finesse, boolean twohand) {
			this.numDice = num;
			this.damageDie = d;
			
			this.isRanged = false;
			this.isLight = light;
			this.isFinesse = finesse;
			this.isTwoHanded = twohand;
			
			this.attackRange = 1;
			this.damageType = dt;
		}
		
		WeaponType(int num, Dice d, DamageType dt, boolean light, boolean finesse, boolean twohand,
				int attackRange) {
			this(num,d,dt,light,finesse,twohand);
			this.attackRange = attackRange;
		}
		
		int numDice() {
			return numDice;
		}
		
		boolean isRanged() {
			return isRanged;
		}
		
		boolean isLight() {
			return isLight;
		}
		
		boolean isTwoHanded() {
			return isTwoHanded;
		}
		
		boolean isFinesse() {
			return isFinesse;
		}
		
		int attackRange() {
			return attackRange;
		}
		
		Dice damageDie() {
			return damageDie;
		}
	}
	
	private WeaponType weaponType;
	private int hitModifier;
	private int damageModifier;
	private int critRange;
	private DamageType damageType;
	private OnHit onHit;
	
	public Weapon(WeaponType w) {
		this.weaponType = w;
		this.hitModifier = 0;
		this.damageModifier = 0;
		this.critRange = 20;
		this.damageType = w.damageType;
		this.onHit = (u, t) -> {};
	}
	
	public Weapon(WeaponType w, int hitmod, int damagemod) {
		this(w);
		this.hitModifier = hitmod;
		this.damageModifier = damagemod;
	}
	
	/* Hit Mod */
	public int getHitMod() {
		return this.hitModifier;
	}
	
	public void addHitMod(int num) {
		this.hitModifier += num;
	}
	
	/* Damage Mod */
	public void addDamageMod(int num) {
		this.damageModifier += num;
	}
	
	/* Attack Range */
	public boolean isRanged() {
		return this.weaponType.isRanged();
	}
	
	public int getAttackRange() {
		return this.weaponType.attackRange();
	}
	
	public int getCritRange() {
		return this.critRange;
	}
	
	/* Rolls damage for the weapon */
	public int attack() {
		return MathHelper.roll(this.weaponType.numDice(), this.weaponType.damageDie(), 0) + this.damageModifier;
	}
	
	public DamageType getDamageType() {
		return this.damageType;
	}
	
	public WeaponType getWeaponType() {
		return this.weaponType;
	}
	
	public void onHit(GameCharacter user, GameCharacter target) {
		onHit.onhit(user, target);
	}
}
