package peacenerd.utils;

import java.util.ArrayList;

public class Utils {
	
	//returns if a given object of type T is within an array list of T objects
	public static <T> boolean isIn(T t, ArrayList<T> arr) {
		for (int i = 0; i < arr.size(); i++) {
			if (arr.get(i).equals(t))
				return true;
		}
		return false;
	}
}
