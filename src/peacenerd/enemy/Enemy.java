package peacenerd.enemy;

import peacenerd.character.GameCharacter;
import peacenerd.combat.Weapon;
import peacenerd.combat.Weapon.WeaponType;

public class Enemy extends GameCharacter {

	public enum EnemyTemplate {
		Orc("Orc", 35, 16, 6, new Weapon(WeaponType.Longsword), 1, "Resources/Images/img2.png");
		
		private String name;
		private int health;
		private int armor;
		private int speed;
		private Weapon weapon;
		private int challengeRating;
		
		private String imageFile;
		
		EnemyTemplate(String name, int health, int armor, int speed, Weapon w, int cr, String imageFile) {
			this.name = name;
			this.health = health;
			this.armor = armor;
			this.speed = speed;
			this.weapon = w;
			this.challengeRating = cr;
		}
	}
	
	public Enemy(String name, String imageFile) {
		super(name, imageFile);
	}
	
	public Enemy(EnemyTemplate template) {
		super(template.name, template.imageFile);
	}
	
	public boolean isEnemy() {
		return true;
	}
	
	
}
