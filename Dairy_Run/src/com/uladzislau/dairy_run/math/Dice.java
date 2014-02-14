package com.uladzislau.dairy_run.math;

import java.util.Random;

public class Dice {

	private static Random dice = new Random();

	public static int get_Random_Integer_From_Min_To_Max(int min, int max) {
		return dice.nextInt((max - min + 1)) + min;
	}

	public static float get_Random_Float_From_Min_To_Max(float min, float max) {
		return dice.nextFloat() * (max - min) + min;
	}

	public static float nextFloat() {
		return dice.nextFloat();
	}

	public static boolean nextBoolean() {
		return dice.nextBoolean();
	}

	public static double get_Random_Double_From_Min_To_Max(double min, double max) {
		return (max - min + 1) * dice.nextDouble() + min;
	}

}