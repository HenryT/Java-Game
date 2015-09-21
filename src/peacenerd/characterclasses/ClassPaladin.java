package peacenerd.characterclasses;

import peacenerd.character.PlayerCharacter;
import peacenerd.combat.Weapon.WeaponType;
import peacenerd.character.GameCharacter.Stat;
import peacenerd.utils.MathHelper.Dice;

public class ClassPaladin extends CharacterClass {

	private int[][] spellSlots = 
		//An extra row and column is added so that the first element is [1][1] rather than [0][0]
		{
		{},
		{0,0,0,0,0,0,0,0,0,0},
		{0,2,0,0,0,0,0,0,0,0},
		{0,3,0,0,0,0,0,0,0,0},
		{0,3,0,0,0,0,0,0,0,0},
		{0,4,2,0,0,0,0,0,0,0},
		{0,4,2,0,0,0,0,0,0,0},
		{0,4,3,0,0,0,0,0,0,0},
		{0,4,3,0,0,0,0,0,0,0},
		{0,4,3,2,0,0,0,0,0,0},
		{0,4,3,2,0,0,0,0,0,0},
		{0,4,3,3,0,0,0,0,0,0},
		{0,4,3,3,0,0,0,0,0,0},
		{0,4,3,3,1,0,0,0,0,0},
		{0,4,3,3,1,0,0,0,0,0},
		{0,4,3,3,2,0,0,0,0,0},
		{0,4,3,3,2,0,0,0,0,0},
		{0,4,3,3,3,1,0,0,0,0},
		{0,4,3,3,3,1,0,0,0,0},
		{0,4,3,3,3,2,0,0,0,0},
		{0,4,3,3,3,2,0,0,0,0}};
	
	//Feats
	
	//Abilities
	
	public ClassPaladin() {
		super(ClassName.Paladin, Dice.D10, true, Stat.Charisma);
	}

	@Override
	public void applyStartingBenefits(PlayerCharacter character) {
		//spell slots
		character.setMaxSpellSlots(this.spellSlots);
		
		//add save bonuses
		character.addSaveBonus(Stat.Charisma, CharacterClass.saveBonus);
		character.addSaveBonus(Stat.Constitution, CharacterClass.saveBonus);
		
		//Weapon Proficiencies
		character.addWeaponProficiency(WeaponType.Greatsword);
		character.addWeaponProficiency(WeaponType.Longsword);
		character.addWeaponProficiency(WeaponType.Shortsword);
		character.addWeaponProficiency(WeaponType.Mace);
	}

	@Override
	public void applyMultiClassBenefits(PlayerCharacter character) {
		//spell slots
		character.setMaxSpellSlots(CharacterClass.multiClassSpellSlots);
		
		//Weapon Proficiencies
		character.addWeaponProficiency(WeaponType.Greatsword);
		character.addWeaponProficiency(WeaponType.Longsword);
		character.addWeaponProficiency(WeaponType.Shortsword);
		character.addWeaponProficiency(WeaponType.Mace);
	}

	@Override
	public void levelUp(PlayerCharacter character) {
		
	}

}
