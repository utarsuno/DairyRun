package com.uladzislau.dairy_run.math_utility;

import com.uladzislau.dairy_run.math.Vector2f;

public class MathUtil {

	public static boolean isNumber(char c) {
		if (c >= '0' && c <= '9') {
			return true;
		}
		return false;
	}

	public static boolean hasNumbers(String text) {
		if (text.matches(".*\\d.*")) { //$NON-NLS-1$
			return true;
		}
		return false;
	}

	public static final double round(double n, int number_of_decimals) {
		switch (number_of_decimals) {
		case 1:
			return (long) (n * 10 + 0.5) / 10.0;
		case 2:
			return (long) (n * 100 + 0.5) / 100.0;
		case 3:
		case 4:
		default:
			break;
		}
		return (long) (n * 1000 + 0.5) / 1000.0;
	}

	public static final double distance(int x1, int y1, int x2, int y2) {
		return Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
	}

	public static double getAngleFrom(Vector2f point_one, Vector2f point_two) {
		return Math.atan((point_one.y - point_two.y) / (point_one.x - point_two.x));
	}

}