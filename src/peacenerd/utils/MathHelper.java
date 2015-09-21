package peacenerd.utils;

import peacenerd.character.Moveable;

public class MathHelper {
	
	public class Pair<A, B> {
		private A a;
		private B b;
		
		public Pair(A a, B b) {
			this.a = a;
			this.b = b;
		}
		
		public A a() {
			return this.a;
		}
		
		public B b() {
			return this.b;
		}
	}
	
	public enum Dice {
		D4 (4), 
		D6 (6), 
		D8 (8),
		D10 (10),
		D12 (12), 
		D20 (20);
		
		//static array avoids array copying
		private static Dice[] vals = values();
		private int sides;
		
		Dice(int sides) {
			this.sides = sides;
		}
		
		public int sides() {
			return this.sides;
		}
		
		public int roll() {
			return MathHelper.getRand(1, this.sides-1);
		}
		
		public int roll(int num) {
			int damage = 0;
			for (int i = 0; i < num; i++)
				damage += roll();
			return damage;
		}
		
		/* Returns the next dice in the enum
		 *  next(Dice.D6) returns Dice.D8 
		 *  */
		public Dice next(Dice dice) {
			if (this.ordinal() == vals.length - 1)
				return this;
			return vals[(this.ordinal()+1) % vals.length];
		}
		
		/* Returns the next dice in the enum 
		 *  next(Dice.D12) returns Dice.D10 
		 * */
		public Dice prev(Dice dice) {
			if (this.ordinal() == 0)
				return this;
			return vals[(this.ordinal()-1) % vals.length];
		}
	}
	
	/* Returns number between min (inclusive) and max (inclusive)*/
	public static int getRand(int min, int max) {
		return min + (int)((max+1) * Math.random());
	}
	
	/* Input a decimal number between 0-1
	 * 	'probability' is the chance that this method returns true
	 */
	public static boolean odds(double probability) {
		return Math.random() >= ((1-probability));
	}
	
	/* non functional right now */
	public static int getRandWithBias(double ... nums) {
		int length = nums.length;
		if (length % 2 != 0)
			return Integer.MAX_VALUE;
		
		return 0;
	}
	
	/* Rolls numdice number of Dice type d */
	public static int roll(int numdice, Dice d, int bonus) {
		int toReturn = 0;
		for(int i = 0; i < numdice; i++)
			toReturn += d.roll();
		return toReturn+bonus;
	}
	
	/* Rolls dice with either advantage (adv = true) or disadvantage (adv = false) */
	public static int roll(int numdice, Dice d, int bonus, boolean adv) {
		int toReturn1 = 0;
		int toReturn2 = 0;
		for(int i = 0; i < numdice; i++)
			toReturn1 += d.roll();
		for(int i = 0; i < numdice; i++)
			toReturn2 += d.roll();
		
		return (adv) ? Math.max(toReturn1, toReturn2) + bonus : Math.min(toReturn1, toReturn2) + bonus;
	}
	
	/* Returns a random integer, with bias given to one of the integers in the range */
	public static int getRandWithBias(int num, double probability, int min, int max) {
		if ((odds(probability))) {
			return num;
		}
		else {
			int x = getRand(min, max);
			while (x == num) {
				x = getRand(min, max);
			}
			return x;
		}
	}
	
	/* Returns Manhattan distance between two points on a grid
	 * note: Diagonal movement is not allowed */
	public static int lineDist(int r1, int c1, int r2, int c2) {
		return (Math.abs(r1 - r2) + Math.abs(c1 - c2));
	}
	
	/* Returns Manhattan distance between two Moveable objects 
	 * note: Diagonal movement is not allowed */
	public static int lineDist(Moveable m1, Moveable m2) {
		return (Math.abs(m1.getRow() - m2.getRow()) + Math.abs(m1.getCol() - m2.getCol()));
	}
}
