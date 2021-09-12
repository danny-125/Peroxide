package me.danny125.peroxide.utilities;

public class RandomUtil {
	public static int randomNumber(double max, double min) {
        int ii = (int) (-min + (int) (Math.random() * ((max - (-min)) + 1)));
        return ii;
    }
}
